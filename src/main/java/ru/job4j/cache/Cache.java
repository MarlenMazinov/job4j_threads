package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public Map<Integer, Base> getMemory() {
        return memory;
    }

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base updatedBase = memory.computeIfPresent(model.getId(), (key, value) -> {
            Base stored = memory.get(key);
            if (stored.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base updated = new Base(key, value.getVersion() + 1);
            updated.setName(value.getName());
            memory.replace(key, stored, updated);
            return memory.get(key);
        });
        return updatedBase != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}