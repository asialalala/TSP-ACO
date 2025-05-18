package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

import implementation.Coordinates;
import implementation.TSPReader;
import org.junit.Test;

import implementation.AcoAlgorithm;
import implementation.AcoAlgorithmMorePheromoneWhenBetterPath;
import implementation.AcoAlgorithmPheromoneBasedOnEdgeWeight;
import implementation.AcoConstPheromoneUpdateForEveryEdge;
import implementation.AcoPheromoneUpdateBasedOnPathLength;
import implementation.AcoUpdatePheromoneOnlyForBetterPath;
import implementation.Cities;

public class AcoAlgorithmTest {

    @Test
    public void testChooseNextCity() throws IOException {
        // Arrange
       TSPReader tspReader = new TSPReader();
       Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/test.tsp");

       Random generator = new Random(42);
       Cities cities = new Cities(coordinates);
       AcoAlgorithm acoAlgorithm = new AcoUpdatePheromoneOnlyForBetterPath(generator, cities, 1, 1, 1,1,1,1,1);
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

       //Assert
       assertEquals(2, nextCity);

    }
}
