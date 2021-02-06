package fr.owle.hometracker.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NodeBuilder<T> {

    private final String path;
    private final T value;

    private NodeBuilder(String path, T value) {
        this.path = path;
        this.value = value;
    }

    private Node<?> build() {
        final String[] paths = path.split("\\.");
        final String lastPath = paths[paths.length - 1];
        final Node<T> node = new Node<>(lastPath, value);
        if (paths.length > 1) {
            final String newPath = path.substring(0, path.length() - lastPath.length() - 1);
            final List<Node<T>> list = Collections.singletonList(node);
            final NodeBuilder<List<Node<T>>> builder = new NodeBuilder<>(newPath, list);
            return builder.build();
        }
        return node;
    }

    public static <E> Node<?> build(String path, E value) {
        final NodeBuilder<E> nodeBuilder = new NodeBuilder<>(path, value);
        return nodeBuilder.build();
    }

    public static Node<List<Node<?>>> root(String rootName, Node<?>...nodes) {
        final Node<List<Node<?>>> root = new Node<>(rootName, Arrays.asList(nodes));
        return simplified(root);
    }

    public static Node<List<Node<?>>> simplified(Node<List<Node<?>>> root) {
        final List<Node<?>> list = new ArrayList<>();
        for (Node<?> node : root.getValue()) {
            if (list.stream().noneMatch(node::equalsKey)) {
                if (node.isRootNode()) {
                    final List<Node<?>> nodeList = new ArrayList<>();
                    root.getValue().stream().filter(node::equalsKey)
                            .filter(Node::isRootNode).map(Node::getValue)
                            .map(l -> (List<Node<?>>) l).forEach(nodeList::addAll);
                    list.add(simplified(new Node<>(node.getKey(), nodeList)));
                    continue;
                }
                list.add(node);
            }
        }
        return new Node<>(root.getKey(), list);
    }

    public static Node<List<Node<?>>> root(Node<?>...nodes) {
        return root("storage", nodes);
    }

}
