package fr.owle.hometracker.storage;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.HTModule;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocalStorage implements Storage {

    public static final String STORAGE_PATH = "storage";

    public LocalStorage() {
        final File file = new File(STORAGE_PATH);
        try {
            Files.createDirectories(file.toPath());
        } catch (IOException e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            e.printStackTrace();
        }
    }

    private File getStorageFile(HTModule htModule) throws IOException {
        final File file = new File(STORAGE_PATH + "/" + htModule.getName() + ".storage");
        assert file.exists() || file.createNewFile();
        return file;
    }

    @Override
    public Optional<? extends Node<?>> get(HTModule htModule, String request) throws IOException, ClassNotFoundException {
        final File storageFile = getStorageFile(htModule);
        final List<String> lines = Files.readAllLines(storageFile.toPath()).stream().filter(line -> {
            final String[] args = line.split(" = ");
            return args.length > 0 && args[0].trim().startsWith(request);
        }).collect(Collectors.toList());
        if (!lines.isEmpty()) {
            final Node<?> createdNode = createNode(request, lines);
            return Optional.ofNullable(createdNode);
        }
        return Optional.empty();
    }

    @Override
    public void set(HTModule htModule, String request, Node<?> value) throws IOException {
        final String currentPath = request + (request.equals("") ?  "" : ".") + value.getKey();
        if (value.isRootNode()) {
            final List<Node<?>> values = ((Node<List<Node<?>>>) value).getValue();
            for (Node<?> node : values)
                set(htModule, currentPath, node);
        } else {
            final File storageFile = getStorageFile(htModule);
            final String line = currentPath + " = " + value.getValue() + " : " + value.getValue().getClass().getName();
            addLine(storageFile, line);
        }
    }

    @Override
    public boolean delete(HTModule htModule, String request) throws IOException {
        final File storageFile = getStorageFile(htModule);
        final StringBuilder builder = new StringBuilder();
        Files.readAllLines(storageFile.toPath()).stream().filter(line -> !line.startsWith(request))
            .map(line -> line + "\n").forEach(builder::append);
        Files.writeString(storageFile.toPath(), builder.toString());
        return true;
    }

    private void addLine(File file, String line) throws IOException {
        try (final BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.APPEND)) {
            final String formatLine = line.replace("\n", "\\n") + "\n";
            writer.write(formatLine);
        }
    }

    private Node<?> createNode(String request, List<String> lines) throws ClassNotFoundException {
        if (lines.size() > 1) {
            return createRootNode(request, lines);
        }
        final String line = lines.get(0);
        return parseLineToNode(request, line);
    }

    private Node<?> parseLineToNode(String request, String line) throws ClassNotFoundException {
        final String[] args = line.split("=");
        final String key = args[0].trim();
        final String valueArgs = line.replaceFirst(key, "").trim().replaceFirst("=", "").trim();
        final String value = valueArgs.substring(0, valueArgs.lastIndexOf(":")).trim();
        final String type = valueArgs.substring(value.length()).trim().replaceFirst(":", "").trim();
        request = !request.equals(key) ? key.replaceFirst(request + "\\.", "") :
            request.substring(request.lastIndexOf(".") + 1);
        return NodeBuilder.build(request, convert(type, value));
    }

    private Node<?> createRootNode(String request, List<String> lines) throws ClassNotFoundException {
        final List<Node<?>> nodes = new ArrayList<>();
        for (String line : lines) {
            nodes.add(parseLineToNode(request, line));
        }
        final int index = request.lastIndexOf(".") + 1;
        final Node<List<Node<?>>> node = new Node<>(index > 0 ? request.substring(index) : request, nodes);
        return NodeBuilder.simplified(node);
    }

    private Object convert(String typeName, String input) throws ClassNotFoundException {
        final Class<?> type = Class.forName(typeName);
        final PropertyEditor editor = PropertyEditorManager.findEditor(type);
        editor.setAsText(input.replace("\\n", "\n"));
        return editor.getValue();
    }

}
