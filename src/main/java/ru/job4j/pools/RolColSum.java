package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int length = matrix.length;
        Sums[] rsl = new Sums[length];
        for (int i = 0; i < length; i++) {
            Sums sums = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            rsl[i] = sums;
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            sums.setColSum(getSum(matrix, i, "column"));
            sums.setRowSum(getSum(matrix, i, "row"));
            result[i] = sums;
        }
        return result;
    }

    private static int getSum(int[][] matrix, int index, String flag) throws ExecutionException,
            InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            if ("row".equals(flag)) {
                for (int i = 0; i < matrix.length; i++) {
                    sum += matrix[index][i];
                }
            }
            if ("column".equals(flag)) {
                for (int i = 0; i < matrix.length; i++) {
                    sum += matrix[i][index];
                }
            }
            return sum;
        }).get();
    }

}