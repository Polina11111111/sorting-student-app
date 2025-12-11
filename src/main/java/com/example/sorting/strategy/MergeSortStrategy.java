package com.example.sorting.strategy;


import com.example.sorting.collection.CustomArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MergeSortStrategy<T> implements SortStrategy<T> {
    @Override
    public void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        if (n < 2) return;
        List<T> aux = new ArrayList<>(n);
        for (T t : list) aux.add(t);
        mergeSort(aux, 0, aux.size() - 1, comparator);
        for (int i = 0; i < aux.size(); i++) list.set(i, aux.get(i));
    }


    private void mergeSort(List<T> a, int l, int r, Comparator<T> comp) {
        if (l >= r) return;
        int m = (l + r) / 2;
        mergeSort(a, l, m, comp);
        mergeSort(a, m + 1, r, comp);
        List<T> tmp = new ArrayList<>();
        int i = l, j = m + 1;
        while (i <= m || j <= r) {
            if (i <= m && (j > r || comp.compare(a.get(i), a.get(j)) <= 0)) tmp.add(a.get(i++));
            else tmp.add(a.get(j++));
        }
        for (int k = 0; k < tmp.size(); k++) a.set(l + k, tmp.get(k));
    }
}