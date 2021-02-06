package fr.owle.hometracker.listeners;

import fr.owle.hometracker.event.StorageGetRequestEvent;
import fr.owle.hometracker.event.StorageSetRequestEvent;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.services.StorageService;
import fr.owle.hometracker.storage.Node;
import fr.owle.hometracker.utils.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageListener implements Listener {

    @Autowired
    private StorageService storageService;

    @EventHandler
    public void onStorageGetRequestEvent(StorageGetRequestEvent event) {
        final HTModule target = event.getTarget();
        final String request = event.getRequest();
        final Optional<? extends Node<?>> result = storageService.getOnDataBase(target, request);
        result.ifPresent(event::setResult);
    }

    @EventHandler
    public void onStoragePutRequestEvent(StorageSetRequestEvent event) {
        final HTModule target = event.getTarget();
        final String request = event.getRequest();
        final Node<?> content = event.getContent();
        storageService.setOnDataBase(target, request, content);
    }

}
