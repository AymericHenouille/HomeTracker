package fr.owle.hometracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 * Represent an element stored in the storage system.
 * The storage system is inspired of the no sql architecture. Here's an example :
 * {@code
 * - Dark Vador
 *      - Lightsaber : red
 * - Yoda
 *      -Lightsaber : green
 * - Obi Wan
 *      - Lightsaber : blue
 * }
 * In this example, a module named "StarWarsModule" saved those data,
 * so we have in our storage system a list of node with the following keys ("Dark Vador","Yoda","Obi Wan"),
 * and the value of those nodes are another node with the key "Lightsaber" and the value being a String ("red","green","blue").
 *
 * To resume, the objects hierarchy is looking like this :
 * {@code
 * Node<List<Node>> {
 *  key: "StarWarsModule",
 *  value:
 *      - Node<Node> {
 *          key: "Dark Vador"
 *          value: Node<String> {
 *              key: "Lightsaber",
 *              value: "red"
 *          }
 *      }
 *      - Node<Node> {
 *          key: "Yoda"
 *          value: Node<String> {
 *              key: "Lightsaber",
 *              value: "green"
 *          }
 *      }
 *      - Node<Node> {
 *          key: "Obi Wan"
 *          value: Node<String> {
 *              key: "Lightsaber",
 *              value: "blue"
 *          }
 *      }
 * }
 * }
 *
 * @param <E> the type of the value attribute.
 * </pre>
 */
public class Node<E> {
    /**
     * It is the name of the node. It is how the {@link StorageManager} will be able to index the nodes.
     */
    protected String key;

    /**
     * It is the value of this node. It's type is generic but, you probably find or use a Node value or a List of Node value.
     */
    protected E value;

    public Node(String key) {
        this.key = key;
    }

    public Node() { }

    public Node(String key, E value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public boolean equalsKey(Node<?> node) {
        return node.getKey().equals(key);
    }

    public boolean isRootNode() {
        return value instanceof List && !((List<?>) value).isEmpty() && ((List<?>) value).get(0) instanceof Node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(key, node.key) && Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
