package learning.interview.coding.keyvaluedb.exception;

import java.util.UUID;

public class KeyNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_ERROR_MESSAGE = "object with key %s not found";

    public KeyNotFoundException(UUID key) {
        super(String.format(NOT_FOUND_ERROR_MESSAGE, key));
    }
}
