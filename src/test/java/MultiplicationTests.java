import com.kisilevskiy.core.MatrixLinearMultiplier;
import com.kisilevskiy.core.MatrixParallelMultiplier;
import com.kisilevskiy.core.base.MatrixMultiplier;
import com.kisilevskiy.core.domain.Matrix;
import com.kisilevskiy.utils.MatrixHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MultiplicationTests {

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
        Matrix linearResultMatrix = multiplier.multiplyMatrices(firstMatrix, secondMatrix);

        multiplier = new MatrixParallelMultiplier();
        Matrix parallelResultMatrix = multiplier.multiplyMatrices(firstMatrix, secondMatrix);
    }

}
