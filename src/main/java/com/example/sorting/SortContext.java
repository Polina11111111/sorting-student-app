package com.example.sorting;


import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.strategy.SortStrategy;


import java.util.Comparator;


public class SortContext<T> {
    private SortStrategy<T> strategy;


    public void setStrategy(SortStrategy<T> strategy) { this.strategy = strategy; }


    public void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        if (strategy == null) throw new IllegalStateException("Strategy not set");
        strategy.sort(list, comparator);
    }
}