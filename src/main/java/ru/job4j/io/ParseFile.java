package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() {
        return content(ch -> ch < 0x80);
    }

    public synchronized String content(Predicate<Character> filter) {
        String output = "";
        try (BufferedInputStream in =
                     new BufferedInputStream(new FileInputStream(file))) {
            int bytesRead;
            while ((bytesRead = in.read()) != -1) {
                char ch = (char) bytesRead;
                if (filter.test(ch)) {
                    output += ch;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}