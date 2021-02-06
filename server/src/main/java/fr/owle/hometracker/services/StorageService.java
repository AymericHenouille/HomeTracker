package fr.owle.hometracker.services;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.storage.LocalStorage;
import fr.owle.hometracker.storage.Node;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageService {

    //TODO degage moi ca !
    private final LocalStorage localStorage = new LocalStorage();

    public Optional<? extends Node<?>> getOnDataBase(HTModule htModule, String request) {
        try {
            return localStorage.get(htModule, request);
        } catch (Exception e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void setOnDataBase(HTModule htModule, String request, Node<?> value) {
        try {
            localStorage.set(htModule, request, value);
        } catch (Exception e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            e.printStackTrace();
        }
    }

}
