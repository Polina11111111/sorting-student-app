package com.example.sorting.strategy;


import com.example.sorting.collection.CustomArrayList;
import java.util.Comparator;


public interface SortStrategy<T> {
    void sort(CustomArrayList<T> list, Comparator<T> comparator);
}