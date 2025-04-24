package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import implementation.Coordinates;
import implementation.Utilities;

public class UtilitiesTest {
    @Test
    public void testEuclideanDistance() {

        // Arrange
        Coordinates<Integer, Integer> pointA = new Coordinates<>(0, 0);
        Coordinates<Integer, Integer> pointB = new Coordinates<>(3, 4);

        // Act
        int distance = Utilities.euclideanDistance(pointA, pointB);

        // Assert
        assertEquals(5, distance, 0.001);
    }

    @Test
    public void testGetIndex() {
        // Arrange
        // Act
        int index0 = Utilities.getIndex(0, 1);
        int indexSwap0 = Utilities.getIndex(1, 0);
        int index6 = Utilities.getIndex(0, 4);
        int index8 = Utilities.getIndex(4, 2);
        int sameIndex = Utilities.getIndex(2, 2);
        // Assert
        assertEquals(0, index0);
        assertEquals(index0, indexSwap0);
        assertEquals(6, index6);
        assertEquals(8, index8);
        assertEquals(-1, sameIndex);
    }
}
