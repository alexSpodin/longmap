package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LongMapImpl<V> implements LongMap<V> {

    private final List<Entry<V>>[] entries;
    private long size;
    private final Class<V> valueType;

    public LongMapImpl(Class<V> valueType) {
        int defaultCapacity = 10;
        entries = new ArrayList[defaultCapacity];
        for (int i = 0; i < defaultCapacity; i++) {
            entries[i] = new ArrayList<>();
        }
        this.valueType = valueType;
    }

    public LongMapImpl(Class<V> valueType, int capacity) {
        entries = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            entries[i] = new ArrayList<>();
        }
        this.valueType = valueType;
    }

    @Override
    public V put(long key, V value) {
        int index = index(key);

        for (Entry<V> entry : entries[index]) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        entries[index].add(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V get(long key) {
        int index = index(key);

        for (Entry<V> entry : entries[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(long key) {
        int index = index(key);

        for (Entry<V> entry : entries[index]) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entries[index].remove(entry);
                size--;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        int index = index(key);

        return entries[index].stream()
                .anyMatch(entry -> entry.getKey().equals(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return Stream.of(entries)
                .anyMatch(entries -> entries.stream()
                        .anyMatch(entry -> entry.getValue().equals(value)));
    }

    @Override
    public long[] keys() {
        return Stream.of(entries)
                .flatMap(entries -> entries.stream()
                        .map(Entry::getKey))
                .mapToLong(x -> x)
                .toArray();
    }

    @Override
    public V[] values() {
        return Stream.of(entries)
                .flatMap(l -> l.stream()
                        .map(Entry::getValue))
                .toArray(this::getCastedArrayInstance);
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void clear() {
        Stream.of(entries).forEach(List::clear);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private V[] getCastedArrayInstance(int arr) {
        return (V[]) Array.newInstance(valueType, arr);
    }

    private int index(long key) {
        return Math.abs(Long.hashCode(key) % entries.length);
    }

    private static class Entry<V> {
        long key;
        V value;

        public Entry(Long key, V value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
