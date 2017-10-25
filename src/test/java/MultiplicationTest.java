import com.kisilevskiy.core.MatrixLinearMultiplier;
import com.kisilevskiy.core.MatrixParallelMultiplier;
import com.kisilevskiy.core.base.MatrixMultiplier;
import com.kisilevskiy.core.domain.Matrix;
import com.kisilevskiy.utils.MatrixHelper;
import org.junit.Test;

import static com.kisilevskiy.config.ApplicationConstants.LoggerTexts.LINEAR_ELAPSED_TIME;
import static com.kisilevskiy.config.ApplicationConstants.LoggerTexts.PARALLEL_ELAPSED_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MultiplicationTest {

    private MatrixMultiplier multiplier;

    @Test
    public void AssertsCorrectMultiplicationResult() {
        Matrix firstMatrix = MatrixHelper.getDefaultMatrix();
        Matrix secondMatrix = MatrixHelper.getDefaultMatrix();

        multiplier = new MatrixLinearMultiplier();
        Matrix linearResultMatrix = multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        assertEquals(linearResultMatrix, MatrixHelper.getDefaultMatricesMultiplicationResult());

        multiplier = new MatrixParallelMultiplier();
        Matrix parallelResultMatrix = multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        assertEquals(parallelResultMatrix, MatrixHelper.getDefaultMatricesMultiplicationResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void AssertsLinearMultiplicationIllegalArgumentExceptionHavingWrongMatricesSizes() {
        Matrix firstMatrix = MatrixHelper.generateMatrixRandomly(10, 10);
        Matrix secondMatrix = MatrixHelper.generateMatrixRandomly(5, 10);

        multiplier = new MatrixLinearMultiplier();
        multiplier.multiplyMatrices(firstMatrix, secondMatrix);
    }

    @Test
    public void AssertsParallelIsSlowerOnSmallDimensionsArrays() {
        Matrix firstMatrix = MatrixHelper.generateMatrixRandomly(10, 10);
        Matrix secondMatrix = MatrixHelper.generateMatrixRandomly(10, 10);

        multiplier = new MatrixLinearMultiplier();

        long beginTime = System.nanoTime();
        multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        long endTime = System.nanoTime();

        double linearElapsedTime = (endTime - beginTime) / 1000000000.0;

        multiplier = new MatrixParallelMultiplier();

        beginTime = System.nanoTime();
        multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        endTime = System.nanoTime();

        double parallelElapsedTime = (endTime - beginTime) / 1000000000.0;

        boolean assertion = parallelElapsedTime > linearElapsedTime;
        assertTrue(assertion);
    }

    @Test
    public void AssertsParallelIsFasterOnHighDimensionsArrays() {
        Matrix firstMatrix = MatrixHelper.generateMatrixRandomly(1500, 1500);
        Matrix secondMatrix = MatrixHelper.generateMatrixRandomly(1500, 1500);

        multiplier = new MatrixLinearMultiplier();

        long beginTime = System.nanoTime();
        multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        long endTime = System.nanoTime();

        double linearElapsedTime = (endTime - beginTime) / 1000000000.0;
        System.out.println(LINEAR_ELAPSED_TIME + linearElapsedTime);

        multiplier = new MatrixParallelMultiplier();

        beginTime = System.nanoTime();
        multiplier.multiplyMatrices(firstMatrix, secondMatrix);
        endTime = System.nanoTime();

        double parallelElapsedTime = (endTime - beginTime) / 1000000000.0;
        System.out.println(PARALLEL_ELAPSED_TIME + parallelElapsedTime);

        boolean assertion = parallelElapsedTime < linearElapsedTime;
        assertTrue(assertion);
    }

}
