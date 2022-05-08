package ru.job4j.pools;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenArrayOfIntegerAndSize7() {
        Integer[] array = {0, 1, 5, 12, 8, 6, 7};
        ParallelSearch<Integer> parallelSearch =
                new ParallelSearch<>(array, 0, array.length - 1, 5);
        assertEquals(2, parallelSearch.search(array, 5));
    }

    @Test
    public void whenArrayOfIntegerAndSize15() {
        Integer[] array = new Integer[15];
        for(int i = 0; i < 15; i++) {
            array[i] = i;
        }
        ParallelSearch<Integer> parallelSearch =
                new ParallelSearch<>(array, 0, array.length - 1, 13);
        assertEquals(13, parallelSearch.search(array, 13));
    }
}