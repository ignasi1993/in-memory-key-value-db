package learning.interview.coding.keyvaluedb.sync;

import learning.interview.coding.keyvaluedb.exception.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SynchronizedInMemoryKeyValueDBTest {

    private SynchronizedInMemoryKeyValueDB keyValueDB;

    @BeforeEach
    void setUp() {
        keyValueDB = new SynchronizedInMemoryKeyValueDB();
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @NullSource
    void shouldCreateAndGet(Object object) {
        // given
        UUID key = keyValueDB.create(object);

        // when
        Object returnedObject = keyValueDB.get(key);

        // then
        assertThat(keyValueDB.containsKey(key)).isTrue();
        assertThat(returnedObject).isEqualTo(object);
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @NullSource
    void shouldUpdateCorrectly(Object differentObject) {
        // given
        Object object = "object";
        UUID key = keyValueDB.create(object);

        // when
        keyValueDB.put(key, differentObject);

        // then
        assertThat(keyValueDB.containsKey(key)).isTrue();
        assertThat(keyValueDB.get(key)).isEqualTo(differentObject);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentKeys() {
        // given
        Object object = "object";
        keyValueDB.create(object);
        UUID otherKey = UUID.randomUUID();

        // when
        Executable executable = () -> keyValueDB.put(otherKey, "different object");

        // then
        assertThrows(KeyNotFoundException.class, executable);
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @NullSource
    void shouldRemoveCorrectly(Object object) {
        // given
        UUID key = keyValueDB.create(object);

        // when
        keyValueDB.delete(key);

        // then
        assertThat(keyValueDB.containsKey(key)).isFalse();
        assertThrows(KeyNotFoundException.class, () -> keyValueDB.get(key));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @NullSource
    void shouldClearCorrectly(Object object) {
        // given
        UUID key = keyValueDB.create(object);

        // when
        keyValueDB.clear();

        // then
        assertThat(keyValueDB.containsKey(key)).isFalse();
        assertThrows(KeyNotFoundException.class, () -> keyValueDB.get(key));
    }

    static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of(""),
                Arguments.of("null"),
                Arguments.of(" something"),
                Arguments.of(3),
                Arguments.of(43.23),
                Arguments.of(new ArrayList<>()));
    }
}
