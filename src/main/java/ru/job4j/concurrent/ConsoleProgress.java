package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private String[] array = {"\\", "|", "/"};

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (String symbol : array) {
                    System.out.print("\rLoading ... " + symbol);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}