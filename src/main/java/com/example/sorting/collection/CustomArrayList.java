package com.example.sorting.collection;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class CustomArrayList<T> implements Iterable<T> {
    private Object[] data;
    private int size;


    public CustomArrayList() {
        this(10);
    }

    public CustomArrayList(int capacity) {
        data = new Object[Math.max(10, capacity)];
        size = 0;
    }


    public void add(T item) {
        if (size == data.length) data = Arrays.copyOf(data, data.length * 2);
        data[size++] = item;
    }


    @SuppressWarnings("unchecked")
    public T get(int idx) {
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException("Index: " + idx);
        return (T) data[idx];
    }


    public void set(int idx, T val) {
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException("Index: " + idx);
        data[idx] = val;
    }


    public int size() {
        return size;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cur = 0;

            public boolean hasNext() {
                return cur < size;
            }

            public T next() {
                return get(cur++);
            }
        };
    }


    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size, Spliterator.ORDERED), false);
    }
}