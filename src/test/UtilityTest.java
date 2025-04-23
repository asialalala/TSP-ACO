package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import implementation.Pair;
import implementation.Utility;

public class UtilityTest {
    @Test
    public void testEuclideanDistance() {

        // Arrange
        Pair<Integer, Integer> pointA = new Pair<>(0, 0);
        Pair<Integer, Integer> pointB = new Pair<>(3, 4);

        // Act
        double distance = Utility.euclideanDistance(pointA, pointB);

        // Assert
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testGetIndex() {
        // Arrange
        // Act
        int index0 = Utility.getIndex(0, 1);
        int indexSwap0 = Utility.getIndex(1, 0);
        int index6 = Utility.getIndex(0, 4);
        int index8 = Utility.getIndex(4, 2);
        int sameIndex = Utility.getIndex(2, 2);
        // Assert
        assertEquals(0, index0);
        assertEquals(index0, indexSwap0);
        assertEquals(6, index6);
        assertEquals(8, index8);
        assertEquals(-1, sameIndex);
    }
}
