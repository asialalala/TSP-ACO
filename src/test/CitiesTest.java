package test;

import static org.junit.Assert.assertEquals;

import implementation.Pair;
import implementation.TSPReader;
import org.junit.Test;

import implementation.Cities;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CitiesTest {
    @Test
    public void testInitializeMatrix() throws IOException {
        TSPReader tspReader = new TSPReader();
        Map<Integer, Pair<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/smallerTSPs/xqf131.tsp");

        // Arrange and act
        Cities cities = new Cities(coordinates);
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
    }

    @Test
    public void testEuclideanDistance() {

        // Arrange
        Pair<Integer, Integer> pointA = new Pair<>(0, 0);
        Pair<Integer, Integer> pointB = new Pair<>(3, 4);

        double distance = 0.0;
        Method euclideanDistanceMethod = null;

        // Act
        try {
            euclideanDistanceMethod = Cities.class.getDeclaredMethod("euclideanDistance", Pair.class, Pair.class);
            if (euclideanDistanceMethod != null) {
                euclideanDistanceMethod.setAccessible(true); // Ustawienie metody jako dostępnej
                distance = (double) euclideanDistanceMethod.invoke(new Cities(Map.of()), pointA, pointB);
                System.err.println("Distance: " + distance);
            } else {
                throw new IllegalStateException("euclideanDistanceMethod is null");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testGetIndex() {
        // Arrange
        Method getIndex = null;
        int index1 = 0;
        int index2 = 0;

        // Act
        try {
            getIndex = Cities.class.getDeclaredMethod("getIndex", int.class, int.class);
            if (getIndex != null) {
                index1 = (int) getIndex.invoke(3, 1);
                index1 = (int) getIndex.invoke(1, 3);

            } else {
                throw new IllegalStateException("getIndex is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        assertEquals(index1, index2);
    }
}
