package com.example.sorting.threads;

import com.example.sorting.collection.CustomArrayList;


import java.util.concurrent.atomic.AtomicInteger;


public class MultithreadCounter<T> {
    private final int threadsCount;


    public MultithreadCounter(int threadsCount) {
        this.threadsCount = threadsCount;
    }


    public int countOccurrences(CustomArrayList<T> list, T target) throws InterruptedException {
        AtomicInteger total = new AtomicInteger(0);
        int n = list.size();
        Thread[] threads = new Thread[threadsCount];
        for (int t = 0; t < threadsCount; t++) {
            final int start = t * n / threadsCount;
            final int end = (t + 1) * n / threadsCount;
            threads[t] = new Thread(() -> {
                int cnt = 0;
                for (int i = start; i < end; i++) if (list.get(i).equals(target)) cnt++;
                total.addAndGet(cnt);
            });
            threads[t].start();
        }
        for (Thread th : threads) th.join();
        return total.get();
    }
}