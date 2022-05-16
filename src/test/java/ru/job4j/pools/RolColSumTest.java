package ru.job4j.pools;


import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenNoAsyncSumMatrixSize3() {
        int[][] matrix = {{1, 4, 7}, {3, 0, 6}, {9, 11, -5}};
        RolColSum.Sums[] actual = RolColSum.sum(matrix);
        RolColSum.Sums[] expected = {new RolColSum.Sums(12, 13),
                new RolColSum.Sums(9, 15), new RolColSum.Sums(15, 8)};
        assertEquals(expected, actual);
    }

    @Test
    public void whenAsyncSumMatrixSize3() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 4, 7}, {3, 0, 6}, {9, 11, -5}};
        RolColSum.Sums[] actual = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] expected = {new RolColSum.Sums(12, 13),
                new RolColSum.Sums(9, 15), new RolColSum.Sums(15, 8)};
        assertEquals(expected, actual);
    }
}