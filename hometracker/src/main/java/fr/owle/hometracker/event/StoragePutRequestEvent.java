package fr.owle.hometracker.event;

import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.storage.Node;

public class StoragePutRequestEvent extends Event {
    private HTModule target;
    private String request;
    private Node<?> body;

    public StoragePutRequestEvent(HTModule target, String request, Node<?> body) {
        this.target = target;
        this.request = request;
        this.body = body;
    }
}
