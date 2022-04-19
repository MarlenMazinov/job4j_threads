package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;

import ru.job4j.notify.SimpleBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}