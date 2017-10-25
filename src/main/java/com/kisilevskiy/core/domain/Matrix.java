package com.kisilevskiy.core.domain;

import java.util.Arrays;

public class Matrix {
    private int[][] array;

    /**
     * Generates matrix with emty array inside
     *
     * @param rowLength    Length of rows
     * @param columnLength Length of columns
     */
    public Matrix(int rowLength, int columnLength) {
        this.array = new int[rowLength][columnLength];
    }

    /**
     * Generates matrix from multidimensional array
     *
     * @param array Array to generate matrix from
     */
    public Matrix(int[][] array) {
        int maxColumn = Arrays
                .stream(array)
                .map(currColumn -> currColumn.length)
                .max(Integer::compare)
                .orElse(0);

        this.array = new int[array.length][maxColumn];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, this.array[i], 0, array[i].length);
        }
    }

    // Getters

    /**
     * Iterates over multidimensional array and searches for element by indices
     *
     * @param rowNumber    index of row
     * @param columnNumber index of column
     * @return Integer element defined by indices
     */
    public int getElement(int rowNumber, int columnNumber) {
        return this.array[rowNumber][columnNumber];
    }

    /**
     * @param i Incoming index of row
     * @return i row column lenght
     */
    public int getColumnLength(int i) {
        return array[i].length;
    }

    /**
     * Fills matrix with elements retrieved from column, defined by index
     * @param index Parameter which defines column number in multidimensional array
     * @param srcPos Position to start from in column
     * @param destination Array to fill from column
     * @param destPos Position in destination array to fill from
     * @param length Amount of elements to copy
     */
    public void fillMatrixFromColumnByIndex(int index, int srcPos, int[] destination, int destPos, int length) {
        System.arraycopy(this.array[index], srcPos, destination, destPos, length);

    }

    /**
     * Method to return row length
     * @return integer length of row
     */
    public int getRowLength() {
        return array.length;
    }

    /**
     * Iterates over rows lengths and returns max
     *
     * @return column with max length or 0.
     */
    public int getMaxColumn() {
        return Arrays
                .stream(array)
                .map(currColumn -> currColumn.length)
                .max(Integer::compare)
                .orElse(0);
    }

    // Matrices mathematical calculations

    /**
     * Splits matrix into 4 different arrays, according to Strassen algorithm requirements
     *
     * @param a11 First matrix to fill
     * @param a12 Second matrix to fill
     * @param a21 Third matrix to fill
     * @param a22 Fourth matrix to fill
     */
    public void splitIntoFourMatrices(Matrix a11, Matrix a12, Matrix a21, Matrix a22) {

        int position = this.getRowLength() >> 1;

        for (int i = 0; i < position; i++) {
            System.arraycopy(this.array[i], 0, a11.array[i], 0, position);
            System.arraycopy(this.array[i], position, a12.array[i], 0, position);
            System.arraycopy(this.array[i + position], 0, a21.array[i], 0, position);
            System.arraycopy(this.array[i + position], position, a22.array[i], 0, position);
        }

    }

    /**
     * Multiplies two matrices
     * @param factorMatrix Matrix to use in multiplication with current object
     * @return multiplication result
     */
    public Matrix multiplyMatrices(Matrix factorMatrix) {

        int thisRows = this.getRowLength();
        int factorMatrixColumn = factorMatrix.getColumnLength(0);
        int thisColumns = this.getColumnLength(0);

        int[][] result = new int[thisRows][factorMatrixColumn];

        for (int i = 0; i < thisRows; i++) {
            for (int j = 0; j < factorMatrixColumn; j++) {
                int sum = 0;
                for (int k = 0; k < thisColumns; k++) {
                    sum += this.getElement(i, k) * factorMatrix.getElement(k, j);
                }
                result[i][j] = sum;
            }
        }

        return new Matrix(result);

    }

    /**
     * Subtracts incoming matrix from current one
     * @param second matrix to subtract
     * @return result matrix
     */
    public Matrix subtractMatrices(Matrix second) {
        int rowLength = this.array.length;
        int columnLength = this.array[0].length;
        int[][] result = new int[rowLength][columnLength];

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                result[i][j] = this.array[i][j] - second.array[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Compounds incoming matrix with current one
     * @param second Matrix to compound
     * @return result matrix
     */
    public Matrix compoundMatrices(Matrix second) {

        int rowLength = this.getRowLength();
        int columnLength = this.getColumnLength(0);
        int[][] result = new int[rowLength][columnLength];

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                result[i][j] = this.getElement(i, j) + second.getElement(i, j);
            }
        }
        return new Matrix(result);

    }

    /**
     * Returns submatrix in defined indices
     * @param n row index
     * @param m column index
     * @return result matrix
     */
    public Matrix getSubmatrix(int n, int m) {
        int[][] result = new int[n][m];

        for (int i = 0; i < n; i++) {
            System.arraycopy(this.array[i], 0, result[i], 0, m);
        }

        return new Matrix(result);
    }

    // Object methods

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(this.array)
                .forEach(currArray -> {
                    Arrays.stream(currArray)
                            .forEach(element -> stringBuilder.append(element).append("\t"));
                    stringBuilder.append("\n");
                });

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.deepHashCode(this.array);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Matrix matrixToCompare = (Matrix) obj;

        // Check for amount rows equal
        if (this.array.length != matrixToCompare.array.length) {
            return false;
        }

        //Check for amount of columns equal
        for (int i = 0; i < this.array.length; i++) {
            if (this.array[i].length != matrixToCompare.array[i].length) {
                return false;
            }
        }

        // Check for each element to be equal
        for (int i = 0; i < this.array.length; i++) {
            int[] column = this.array[i];
            for (int j = 0; j < column.length; j++) {
                int thisMatrixElement = column[j];
                int matrixToCompareElement = matrixToCompare.array[i][j];

                if (thisMatrixElement != matrixToCompareElement) {
                    return false;
                }
            }
        }

        return true;
    }
}
