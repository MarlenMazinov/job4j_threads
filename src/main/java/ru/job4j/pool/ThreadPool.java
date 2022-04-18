package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;

import ru.job4j.notify.SimpleBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        this.tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(new MyThread(tasks));
            while (tasks.isEmpty()) {
                thread.wait();
            }
            thread.start();
            threads.add(thread);
        }

    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        threads.forEach(thread -> {
            if (Thread.State.WAITING.equals(thread.getState())) {
                thread.notify();
            }
        });
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    static class MyThread implements Runnable {
        private final SimpleBlockingQueue<Runnable> tasks;

        public MyThread(SimpleBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        public void run() {
            try {
                tasks.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}