package com.example.sorting.strategy;


import com.example.sorting.collection.CustomArrayList;
import java.util.Comparator;


public class BubbleSortStrategy<T> implements SortStrategy<T> {
    @Override
    public void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}