package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "Notification " + user.getUserName() + " to email " + user.getEmail();
        String body = "Add a new event to " + user.getUserName();
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String subject = "Notification " + user.getUserName() + " to email "
                        + user.getEmail();
                String body = "Add a new event to " + user.getUserName();
                send(subject, body, user.getEmail());
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
