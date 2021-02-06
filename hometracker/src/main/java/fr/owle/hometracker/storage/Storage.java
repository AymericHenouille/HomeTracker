package fr.owle.hometracker.storage;

import fr.owle.hometracker.modules.HTModule;

import java.util.Optional;

public interface Storage {

    Optional<? extends Node<?>> get(HTModule htModule, String request) throws Exception;

    void set(HTModule htModule, String request, Node<?> value) throws Exception;

    default <E> void set(HTModule htModule, String request, E value) throws Exception {
        final int lastPoint = request.lastIndexOf(".");
        if (lastPoint > 0) {
            final String path = request.substring(0, lastPoint);
            final String key = request.replaceFirst(path + "\\.", "");
            final Node<E> node = new Node<>(key, value);
            set(htModule, path, node);
        } else {
            final Node<E> node = new Node<>(request, value);
            set(htModule, "", node);
        }
    }

    boolean delete(HTModule htModule, String request) throws Exception;

    default boolean update(HTModule htModule, String request, Node<?> value) throws Exception {
        final boolean delete = delete(htModule, request);
        if (delete) {
            set(htModule, request, value);
        }
        return delete;
    }

    default <E> boolean update(HTModule htModule, String request, E value) throws Exception {
        final boolean delete = delete(htModule, request);
        if (delete) {
            set(htModule, request, value);
        }
        return delete;
    }

}
