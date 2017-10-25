package com.kisilevskiy;

import com.kisilevskiy.core.MatrixLinearMultiplier;
import com.kisilevskiy.core.MatrixParallelMultiplier;
import com.kisilevskiy.core.base.MatrixMultiplier;
import com.kisilevskiy.core.domain.Matrix;
import com.kisilevskiy.utils.MatrixHelper;

public class Main {

    public static void main(String[] args) {
        Matrix firstMatrix = MatrixHelper.generateMatrixRandomly(10, 9);
        Matrix secondMatrix = MatrixHelper.generateMatrixRandomly(10, 9);

        MatrixMultiplier linearMultiplier = new MatrixLinearMultiplier();
        Matrix linearResult = linearMultiplier.multiplyMatrices(firstMatrix, secondMatrix);

        MatrixMultiplier parallelMultiplier = new MatrixParallelMultiplier();
        Matrix parallelResult = parallelMultiplier.multiplyMatrices(firstMatrix, secondMatrix);
    }

}
