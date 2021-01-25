package fr.owle.hometracker.storage;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.StorageGetRequestEvent;
import fr.owle.hometracker.event.StoragePutRequestEvent;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.modules.HTModule;


public class StorageManager {

    private EventManager eventManager;

    public StorageManager() {
        this.eventManager = HTAPI.getEvent().getEventManager();
    }

    public void save(HTModule module, String key, Node<?> node) {
        final StoragePutRequestEvent event = new StoragePutRequestEvent(module, key, node);
        eventManager.emitEvent(HTAPI.getHTAPI(), event);
    }

    public Node<?> get(HTModule module, String request) {
        final StorageGetRequestEvent event = new StorageGetRequestEvent(module, request);
        eventManager.emitEvent(HTAPI.getHTAPI(), event);
        return event.getResult();
    }
}
