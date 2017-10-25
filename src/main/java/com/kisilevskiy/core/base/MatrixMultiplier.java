package com.kisilevskiy.core.base;

import com.kisilevskiy.core.domain.Matrix;

@FunctionalInterface
public interface MatrixMultiplier {

    /**
     * Multiplies two matrices different way
     *
     * @param firstMatrix
     * @param secondMatrix
     * @return
     */
    Matrix multiplyMatrices(Matrix firstMatrix, Matrix secondMatrix);
    default Matrix multiplyMa2trices(Matrix firstMatrix, Matrix secondMatrix) {
        return null;
    }

}