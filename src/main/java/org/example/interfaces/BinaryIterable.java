package org.example.interfaces;

import java.util.function.BiConsumer;

public interface BinaryIterable<K, U> {
    void forEach(BiConsumer<K, U> action);
}
