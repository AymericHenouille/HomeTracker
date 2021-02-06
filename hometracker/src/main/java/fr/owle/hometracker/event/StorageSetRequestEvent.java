package fr.owle.hometracker.event;

import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.storage.Node;

public class StorageSetRequestEvent extends Event {

    private HTModule target;
    private String request;
    private Node<?> content;

    public StorageSetRequestEvent(HTModule target, String request, Node<?> content) {
        this.target = target;
        this.request = request;
        this.content = content;
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

    public Node<?> getContent() {
        return content;
    }

    public void setContent(Node<?> content) {
        this.content = content;
    }

}
