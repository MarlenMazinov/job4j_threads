package ru.job4j.notify;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenProducerConsumerIsCorrect() throws InterruptedException {
        List<Integer> rsl = new ArrayList<>();
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    simpleBlockingQueue.offer(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    rsl.add(simpleBlockingQueue.poll());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(rsl, List.of(0, 1, 2));
    }
}