package test;

import static org.junit.Assert.assertEquals;

import implementation.Pair;
import implementation.TSPReader;
import org.junit.Test;

import implementation.Cities;

import java.io.IOException;
import java.util.Map;

public class CitiesTest {
    @Test
    public void testInitializeMatrix() throws IOException {
        TSPReader tspReader = new TSPReader();
        Map<Integer, Pair<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/smallerTSPs/xqf131.tsp");

        // Arrange and act
        Cities cities = new Cities(coordinates);
        // double[][] matrix = cities.getMatrix();
        double[] matrix = cities.getMatrix();

        // Assert
        int numCities = cities.getNumberOfCities();
        int expectedSize = numCities * (numCities - 1) / 2;
        assertEquals(expectedSize, matrix.length);

        // Check distances to itself (which should be 0 in the matrix)
        for (int i = 0; i < numCities; i++) {
            assertEquals(0.0, cities.getDistance(i, i), 0.001); // Distance to itself is 0
        }

        // Check the symmetric distances
        for (int i = 0; i < numCities; i++) {
            for (int j = i + 1; j < numCities; j++) {
                assertEquals(cities.getDistance(i, j), cities.getDistance(j, i), 0.001);
            }
        }

//        for (int i = 0; i < matrix.length; i++) {
//            assertEquals(5, matrix[i].length);
//        }
//
//        for (int i = 0; i < matrix.length; i++) {
//            assertEquals(0.0, matrix[i][i], 0.001); // Check the distance to itself
//        }

//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                if (i != j) {
//                    assertEquals(matrix[i][j], matrix[j][i], 0.001); // Check the symmetric distances
//                }
//            }
//        }
    }
}
