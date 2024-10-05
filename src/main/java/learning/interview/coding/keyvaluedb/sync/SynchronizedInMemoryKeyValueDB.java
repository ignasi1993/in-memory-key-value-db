package learning.interview.coding.keyvaluedb.sync;

import learning.interview.coding.keyvaluedb.KeyValueDB;
import learning.interview.coding.keyvaluedb.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SynchronizedInMemoryKeyValueDB implements KeyValueDB {

    private final Map<UUID, Object> map = new HashMap<>();

    @Override
    public synchronized Object get(UUID key) throws KeyNotFoundException {
        checkIfMapContainsKey(key);
        return map.get(key);
    }

    @Override
    public synchronized UUID create(Object o) {
        UUID key = UUID.randomUUID();
        map.put(key, o);
        return key;
    }

    @Override
    public synchronized void put(UUID key, Object o) throws KeyNotFoundException {
        checkIfMapContainsKey(key);
        map.put(key, o);
    }

    @Override
    public synchronized void delete(UUID key) {
        map.remove(key);
    }

    @Override
    public synchronized boolean containsKey(UUID key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized void clear() {
        map.clear();
    }

    private synchronized void checkIfMapContainsKey(UUID key) throws KeyNotFoundException {
        if (!map.containsKey(key)) {
            throw new KeyNotFoundException(key);
        }
    }
}
