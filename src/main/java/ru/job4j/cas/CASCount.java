package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer temp = count.get();
        if (temp == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        count.compareAndSet(temp, temp + 1);
    }

    public int get() {
        Integer rsl = count.get();
        if (rsl == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return rsl;
    }
}