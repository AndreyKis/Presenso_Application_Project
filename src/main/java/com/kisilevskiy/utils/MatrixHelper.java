package com.kisilevskiy.utils;

import com.kisilevskiy.core.domain.Matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixHelper {

    private static int log2(int x) {

        int result = 1;
        while ((x >>= 1) != 0) result++;

        return result;

    }

    public static int getNewDimension(Matrix first, Matrix second) {

        List<Integer> sizes = Arrays.asList(first.getRowLength(), first.getColumnLength(0), second.getColumnLength(0));

        return 1 << log2(Collections.max(sizes));

    }

    public static Matrix resizeMatrixToSquareForm(Matrix matrixToResize, int dimension) {

        int[][] result = new int[dimension][dimension];

        for (int i = 0; i < matrixToResize.getRowLength(); i++) {
            for (int j = 0; j < matrixToResize.getColumnLength(i); j++) {
                result[i][j] = matrixToResize.getElement(i, j);
            }
        }

        return new Matrix(result);

    }

    // TODO CHEcK a21
    public static Matrix collectMatrix(Matrix a11, Matrix a12, Matrix a21, Matrix a22) {

        int rowLength = a11.getRowLength();
        int[][] result = new int[rowLength << 1][rowLength << 1];

        for (int i = 0; i < rowLength; i++) {
            System.arraycopy(a11.getColumnByIndex(i), 0, result[i], 0, rowLength);
            System.arraycopy(a12.getColumnByIndex(i), 0, result[i], rowLength, rowLength);
            System.arraycopy(a22.getColumnByIndex(i), 0, result[i + rowLength], rowLength, rowLength);
        }

        return new Matrix(result);

    }

    public static Matrix generateMatrixRandomly(int rowDimension, int columnDimension) {
        int[][] result = new int[rowDimension][columnDimension];

        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result[i][j] = ThreadLocalRandom.current().nextInt(0, 15);
            }
        }

        return new Matrix(result);
    }

    public static Matrix getDefaultMatrix() {
        return new Matrix(new int[][]{
                {1, 5, 10, 15, 20},
                {2, 6, 11, 16, 21},
                {3, 7, 12, 17, 22},
                {4, 8, 13, 18, 23},
                {5, 9, 14, 19, 24}
        });
    }

    public static Matrix getDefaultMatricesMultiplicationResult() {

        return new Matrix(new int[][] {
                {201, 405, 660, 915, 1170},
                {216, 440, 720, 1000, 1280},
                {231, 475, 780, 1085, 1390},
                {246, 510, 840, 1170, 1500},
                {261, 545, 900, 1255, 1610}
        });

    }

}
