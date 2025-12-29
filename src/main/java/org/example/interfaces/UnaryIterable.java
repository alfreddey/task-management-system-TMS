package org.example.interfaces;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface UnaryIterable<T> {
    void forEach(Consumer<T> action);
}

