package com.example.sorting.strategy;


import com.example.sorting.collection.CustomArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class QuickSortStrategy<T> implements SortStrategy<T> {
    @Override
    public void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        if (n < 2) return;
        List<T> tmp = new ArrayList<>(n);
        for (T t : list) tmp.add(t);
        quickSort(tmp, 0, tmp.size() - 1, comparator);
        for (int i = 0; i < tmp.size(); i++) list.set(i, tmp.get(i));
    }


    private void quickSort(List<T> a, int l, int r, Comparator<T> comp) {
        if (l >= r) return;
        T pivot = a.get((l + r) / 2);
        int i = l, j = r;
        while (i <= j) {
            while (comp.compare(a.get(i), pivot) < 0) i++;
            while (comp.compare(a.get(j), pivot) > 0) j--;
            if (i <= j) {
                T tmp = a.get(i);
                a.set(i, a.get(j));
                a.set(j, tmp);
                i++; j--;
            }
        }
        if (l < j) quickSort(a, l, j, comp);
        if (i < r) quickSort(a, i, r, comp);
    }
}