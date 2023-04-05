package com.cl.code.alarm.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 无法改变列表
 *
 * @author chengliang
 * @since 1.0.0
 */
public class UnmodifiableList<T> implements Iterable<T> {

    private final List<T> list;

    public UnmodifiableList(Collection<? extends T> c) {
        this.list = Collections.unmodifiableList(new ArrayList<>(c));
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    public void forEach(Consumer<? super T> action) {
        this.list.forEach(action);
    }

    public Stream<T> stream() {
        return this.list.stream();
    }

    public Stream<T> parallelStream() {
        return this.list.parallelStream();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    public List<T> get() {
        return this.list;
    }
}
