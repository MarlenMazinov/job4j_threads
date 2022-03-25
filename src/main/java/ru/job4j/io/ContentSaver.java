package ru.job4j.io;

import java.io.*;

public class ContentSaver {
    private final File file;

    public ContentSaver(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = content.getBytes();
            out.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
