package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<T> {

    private final T[] array;
    private final T object;
    private T rsl;

    public ParallelSearch(T[] array, T object) {
        this.array = array;
        this.object = object;
    }

    @Override
    protected T compute() {
        if (array.length <= 10) {
            for (T obj : array) {
                if (object.equals(obj)) {
                    rsl = obj;
                }
            }
        } else {
            ParallelSearch<T> parallelSearch = new ParallelSearch<>(array, object);
            parallelSearch.fork();
            rsl = parallelSearch.join();
        }
        return rsl;
    }

    public T search(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, object));
    }
}