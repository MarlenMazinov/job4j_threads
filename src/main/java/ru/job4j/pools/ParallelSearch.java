package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T object;

    public ParallelSearch(T[] array, int from, int to, T object) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.object = object;
    }

    @Override
    protected Integer compute() {
        if (to < 10) {
            for (int i = 0; i <= to; i++) {
                if (object.equals(array[i])) {
                    return i;
                }
            }
        } else {
            int mid = (from + to) / 2;
            ParallelSearch<T> leftParallelSearch = new ParallelSearch<>(array, from, mid, object);
            ParallelSearch<T> rightParallelSearch =
                    new ParallelSearch<>(array, mid + 1, to, object);
            leftParallelSearch.fork();
            rightParallelSearch.fork();
            return leftParallelSearch.join() + rightParallelSearch.join();
        }
        return 0;
    }

    public int search(T[] array, T object) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, 0, array.length - 1, object));
    }
}