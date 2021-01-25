package fr.owle.hometracker.event;

import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.storage.Node;

import java.util.Optional;

public class StorageGetRequestEvent extends Event {
    private HTModule target;
    private String request;
    private Node<?> result;

    public StorageGetRequestEvent(HTModule target, String request) {
        this.target = target;
        this.request = request;
    }

    public HTModule getTarget() {
        return target;
    }

    public void setTarget(HTModule target) {
        this.target = target;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Node<?> getResult() {
        return result;
    }

    public void setResult(Node<?> result) {
        this.result = result;
    }
}
