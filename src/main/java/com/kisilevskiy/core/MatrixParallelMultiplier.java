package com.kisilevskiy.core;

import com.kisilevskiy.core.base.MatrixMultiplier;
import com.kisilevskiy.core.domain.Matrix;
import com.kisilevskiy.utils.MatrixHelper;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Strassen_algorithm">Strassen algorithm</a>
 */
public class MatrixParallelMultiplier implements MatrixMultiplier {

    @Override
    public Matrix multiplyMatrices(Matrix firstMatrix, Matrix secondMatrix) {
        // Preprocessing. Strassen algorithm works only with square matrices
        int newDimension = MatrixHelper.getNewDimension(firstMatrix, secondMatrix);
        Matrix resizedFirstMatrix = MatrixHelper.resizeMatrixToSquareForm(firstMatrix, newDimension);
        Matrix resizedSecondMatrix = MatrixHelper.resizeMatrixToSquareForm(secondMatrix, newDimension);

        StrassenAlgorithm task = new StrassenAlgorithm(resizedFirstMatrix, resizedSecondMatrix, newDimension);
        ForkJoinPool pool = new ForkJoinPool();
        Matrix strassenResult = pool.invoke(task);

        return strassenResult.getSubmatrix(firstMatrix.getRowLength(), secondMatrix.getRowLength());
    }

    private static class StrassenAlgorithm extends RecursiveTask<Matrix> {

        int dimension;
        Matrix firstMatrix;
        Matrix secondMatrix;

        StrassenAlgorithm(Matrix firstMatrix, Matrix secondMatrix, int dimension) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.dimension = dimension;
        }

        @Override
        protected Matrix compute() {
            if (dimension <= 64) {
                return firstMatrix.multiplyMatrices(secondMatrix);
            }

            dimension = dimension >> 1;

            Matrix a11 = new Matrix(dimension, dimension);
            Matrix a12 = new Matrix(dimension, dimension);
            Matrix a21 = new Matrix(dimension, dimension);
            Matrix a22 = new Matrix(dimension, dimension);

            Matrix b11 = new Matrix(dimension, dimension);
            Matrix b12 = new Matrix(dimension, dimension);
            Matrix b21 = new Matrix(dimension, dimension);
            Matrix b22 = new Matrix(dimension, dimension);

            firstMatrix.splitIntoFourMatrices(a11, a12, a21, a22);
            secondMatrix.splitIntoFourMatrices(b11, b12, b21, b22);

            StrassenAlgorithm task_p1 = new StrassenAlgorithm(a11.compoundMatrices(a22), b11.compoundMatrices(b22), dimension);
            StrassenAlgorithm task_p2 = new StrassenAlgorithm(a21.compoundMatrices(a22), b11, dimension);
            StrassenAlgorithm task_p3 = new StrassenAlgorithm(a11, b12.subtractMatrices(b22), dimension);
            StrassenAlgorithm task_p4 = new StrassenAlgorithm(a22, b21.subtractMatrices(b11), dimension);
            StrassenAlgorithm task_p5 = new StrassenAlgorithm(a11.compoundMatrices(a12), b22, dimension);
            StrassenAlgorithm task_p6 = new StrassenAlgorithm(a21.subtractMatrices(a11), b11.compoundMatrices(b12), dimension);
            StrassenAlgorithm task_p7 = new StrassenAlgorithm(a12.subtractMatrices(a22), b21.compoundMatrices(b22), dimension);

            task_p1.fork();
            task_p2.fork();
            task_p3.fork();
            task_p4.fork();
            task_p5.fork();
            task_p6.fork();
            task_p7.fork();

            Matrix p1 = task_p1.join();
            Matrix p2 = task_p2.join();
            Matrix p3 = task_p3.join();
            Matrix p4 = task_p4.join();
            Matrix p5 = task_p5.join();
            Matrix p6 = task_p6.join();
            Matrix p7 = task_p7.join();

            Matrix c11 = p1.compoundMatrices(p4).compoundMatrices(p7.subtractMatrices(p5));
            Matrix c12 = p3.compoundMatrices(p5);
            Matrix c21 = p2.compoundMatrices(p4);
            Matrix c22 = p1.subtractMatrices(p2).compoundMatrices(p3.compoundMatrices(p6));

            return MatrixHelper.collectMatrix(c11, c12, c21, c22);
        }

    }

}
