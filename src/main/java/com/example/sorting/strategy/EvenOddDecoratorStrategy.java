package com.example.sorting.strategy;


import com.example.sorting.collection.CustomArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;


public class EvenOddDecoratorStrategy<T> implements SortStrategy<T> {
    private final SortStrategy<T> inner;
    private final ToIntFunction<T> keyExtractor;


    public EvenOddDecoratorStrategy(SortStrategy<T> inner, ToIntFunction<T> keyExtractor) {
        this.inner = inner;
        this.keyExtractor = keyExtractor;
    }


    @Override
    public void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        List<Integer> idx = new ArrayList<>();
        CustomArrayList<T> evens = new CustomArrayList<>();
        for (int i = 0; i < n; i++) {
            T item = list.get(i);
            if ((keyExtractor.applyAsInt(item) & 1) == 0) { idx.add(i); evens.add(item); }
        }
        inner.sort(evens, comparator);
        for (int k = 0; k < idx.size(); k++) list.set(idx.get(k), evens.get(k));
    }
}