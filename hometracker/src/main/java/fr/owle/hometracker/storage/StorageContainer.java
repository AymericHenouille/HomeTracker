package fr.owle.hometracker.storage;

public class StorageContainer {

    private StorageManager storageManager;

    public StorageManager getStorageManager() {
        return storageManager = storageManager == null ? new StorageManager() : storageManager;
    }
}
