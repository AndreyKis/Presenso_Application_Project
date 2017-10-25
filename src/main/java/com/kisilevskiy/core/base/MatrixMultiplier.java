package com.kisilevskiy.core.base;

import com.kisilevskiy.core.domain.Matrix;

@FunctionalInterface
public interface MatrixMultiplier {

    /**
     * Multiplies two matrices different way
     *
     * @param firstMatrix firstMatrix
     * @param secondMatrix factorMatrix
     * @return multiplication result
     */
    Matrix multiplyMatrices(Matrix firstMatrix, Matrix secondMatrix);

}