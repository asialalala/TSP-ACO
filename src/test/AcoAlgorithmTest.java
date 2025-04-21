package test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import implementation.Pair;
import implementation.TSPReader;
import org.junit.Test;

import implementation.AcoAlgorithm;
import implementation.Cities;

public class AcoAlgorithmTest {

    @Test
    public void testChooseNextCity() throws IOException {
        TSPReader tspReader = new TSPReader();
        Map<Integer, Pair<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/smallerTSPs/xqf131.tsp");

        Cities cities = new Cities(coordinates);
        AcoAlgorithm acoAlgorithm = new AcoAlgorithm(cities, 1, 1, 1);
        // Arrange
        boolean[] visitedCities = new boolean[cities.getMatrix().length];
        visitedCities[0] = true; // Mark the first city as visited
        int currentCity = 0;
        int nextCity = -1;
        Method chooseNextCity = null;
        try {
            chooseNextCity = AcoAlgorithm.class.getDeclaredMethod("chooseNextCity", int.class, boolean[].class);
            chooseNextCity.setAccessible(true); // Allow access to private method
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Act
        try {
            nextCity = (int) chooseNextCity.invoke(acoAlgorithm, currentCity, visitedCities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Next city: " + nextCity);

        //Assert
    }
}
