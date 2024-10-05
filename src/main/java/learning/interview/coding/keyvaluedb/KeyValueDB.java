package learning.interview.coding.keyvaluedb;

import learning.interview.coding.keyvaluedb.exception.KeyNotFoundException;

import java.util.UUID;

public interface KeyValueDB {

    Object get(UUID key) throws KeyNotFoundException;

    UUID create(Object o);

    void put(UUID key, Object o) throws KeyNotFoundException;

    void delete(UUID key);

    boolean containsKey(UUID key);

    void clear();
}
