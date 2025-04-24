package test;

import static org.junit.Assert.assertEquals;

import implementation.Coordinates;
import implementation.TSPReader;
import org.junit.Test;

import implementation.Cities;

import java.io.IOException;
import java.util.Map;

public class CitiesTest {
    @Test
    public void testInitializeMatrix() throws IOException {
        TSPReader tspReader = new TSPReader();
        Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/xqf131.tsp");

        // Arrange and act
        Cities cities = new Cities(coordinates);
        int[] matrix = cities.getMatrix();

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
    }
}
