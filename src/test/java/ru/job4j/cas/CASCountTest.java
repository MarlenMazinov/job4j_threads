package ru.job4j.cas;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrementAndReturn10() {
        CASCount count = new CASCount();
        int tmp = count.get();
        while (tmp < 10) {
            count.increment();
            tmp = count.get();
        }
        assertEquals(10, tmp);
    }

    @Test
    public void whenIncrementAndReturn1000000() {
        CASCount count = new CASCount();
        int tmp = count.get();
        while (tmp < 1000000) {
            count.increment();
            tmp = count.get();
        }
        assertEquals(1000000, tmp);
    }
}