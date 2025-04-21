package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import implementation.Cities;

public class CitiesTest {
    @Test
    public void testInitializeMatrix() {

        // Arrange and act
        Cities cities = new Cities(5);
        double[][] matrix = cities.getMatrix();

        // Assert
        assertEquals(5, matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(5, matrix[i].length);
        }

        for (int i = 0; i < matrix.length; i++) {
            assertEquals(0.0, matrix[i][i], 0.001); // Check the distance to itself

        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != j) {
                    assertEquals(matrix[i][j], matrix[j][i], 0.001); // Check the symmetric distances
                }
            }
        }
    }
}
