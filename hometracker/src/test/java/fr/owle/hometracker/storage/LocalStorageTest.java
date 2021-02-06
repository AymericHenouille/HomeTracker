package fr.owle.hometracker.storage;

import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.modules.HTModuleConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class LocalStorageTest {

    @Test
    public final void setTest() throws Exception {
        final LocalStorage localStorage = new LocalStorage();
        final HTModule htModule = mock(HTModule.class, CALLS_REAL_METHODS);
        final HTModuleConfig htModuleConfig = new HTModuleConfig();
        htModuleConfig.setName("MockModule");
        htModule.setConfig(htModuleConfig);

        final Node<List<Node<?>>> userNode = new Node<>("0", Arrays.asList(
            new Node<>("name", "Benoit:\nLePiaf"),
            new Node<>("age", 21),
            new Node<>("loveAymeric", true)
        ));

        final Node<List<Node<?>>> userNode2 = new Node<>("1", Arrays.asList(
            new Node<>("name", "Geoffrey"),
            new Node<>("age", 20),
            new Node<>("loveAymeric", false)
        ));

        localStorage.set(htModule, "users", userNode);
        localStorage.set(htModule, "users", userNode2);
    }

    @Test
    public final void getTest() throws Exception {
        final LocalStorage localStorage = new LocalStorage();
        final HTModule htModule = mock(HTModule.class, CALLS_REAL_METHODS);
        final HTModuleConfig htModuleConfig = new HTModuleConfig();
        htModuleConfig.setName("MockModule");
        htModule.setConfig(htModuleConfig);

        localStorage.get(htModule, "users.0.name").ifPresent(node -> {
            System.out.println(node.getValue().getClass().getName());
            System.out.println(node);
            System.out.println(node.getValue());
        });
    }

    @Test
    public final void deleteTest() throws IOException {
        final LocalStorage localStorage = new LocalStorage();
        final HTModule htModule = mock(HTModule.class, CALLS_REAL_METHODS);
        final HTModuleConfig htModuleConfig = new HTModuleConfig();
        htModuleConfig.setName("MockModule");
        htModule.setConfig(htModuleConfig);

        localStorage.delete(htModule, "users");
    }

}
