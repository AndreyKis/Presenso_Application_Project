package com.kisilevskiy.core;

import com.kisilevskiy.core.base.MatrixMultiplier;
import com.kisilevskiy.core.domain.Matrix;

import static com.kisilevskiy.config.ApplicationConstants.ExceptionMessages.WRONG_MATRICES_SIZES;

public class MatrixLinearMultiplier implements MatrixMultiplier {

    @Override
    public Matrix multiplyMatrices(Matrix firstMatrix, Matrix secondMatrix) throws IllegalArgumentException {
        int firstMatrixColumnLength = firstMatrix.getColumnLength(0);
        int secondMatrixRowLength = secondMatrix.getRowLength();

        // We are not resizing matrices in linear implementation
        if(firstMatrixColumnLength != secondMatrixRowLength) {
            throw new IllegalArgumentException(WRONG_MATRICES_SIZES);
        }

        int maxRowLength = Math.max(firstMatrix.getRowLength(), secondMatrix.getRowLength());
        int maxColumnLength = Math.max(firstMatrix.getMaxColumn(), secondMatrix.getMaxColumn());
        int[][] result = new int[maxRowLength][maxColumnLength];

        for (int i = 0; i < maxRowLength; i++) {
            for (int j = 0; j < maxColumnLength; j++) {
                result[i][j] = 0;
                for (int k = 0; k < firstMatrix.getColumnLength(i); k++) {
                    result[i][j] = result[i][j] + firstMatrix.getElement(i, k) * secondMatrix.getElement(k, j);
                }
            }
        }

        return new Matrix(result);
    }

}
