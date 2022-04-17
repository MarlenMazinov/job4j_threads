package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import ru.job4j.notify.SimpleBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        job.notifyAll();
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}