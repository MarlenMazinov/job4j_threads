package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        String[] arr = url.split("/");
        this.fileName = arr[arr.length - 1];
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            try {
                long start = Calendar.getInstance().getTimeInMillis();
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    if (bytesWrited >= speed) {
                        long deltaTime = Calendar.getInstance().getTimeInMillis() - start;
                        if (deltaTime < 1000) {
                            Thread.sleep(1000 - deltaTime);
                        }
                        bytesWrited = 0;
                        start = Calendar.getInstance().getTimeInMillis();
                    }
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    bytesWrited += bytesRead;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void validate(int argsNum) {
        if (argsNum != 2) {
            throw new IllegalArgumentException("There must be two arguments. "
                    + "First argument must be url, second argument - download speed.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wget.validate(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}