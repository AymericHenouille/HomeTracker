package fr.owle.hometracker.storage;

import org.junit.jupiter.api.Test;

import java.util.List;

public class NodeBuilderTest {

    @Test
    public final void buildTest() {
        final Node<?> node1 = NodeBuilder.build("users.0.name", "Benoit");
        final Node<?> node2 = NodeBuilder.build("users.0.age", 8);
        final Node<?> node3 = NodeBuilder.build("users.0.favorite.food", "Frites");
        final Node<List<Node<?>>> root = NodeBuilder.root(node1, node2, node3);
        System.out.println(node1);
        System.out.println(node2);
        System.out.println(node3);
        System.out.println(root);
    }

}
