package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import implementation.AcoAlgorithm;
import implementation.Cities;

public class AcoAlgorithmTest {

    private Cities cities = new Cities(3);
    private AcoAlgorithm acoAlgorithm = new AcoAlgorithm(cities, 1, 1, 1);
    
    @Test 
    public void testChooseNextCity() {
        // Arrange
        boolean[] visitedCities = new boolean[cities.getMatrix().length];
        visitedCities[0] = true; // Mark the first city as visited
        int currentCity = 0;
        int nextCity = -1;
        Method chooseNextCity = null;
        try {
            chooseNextCity = AcoAlgorithm.class.getDeclaredMethod("chooseNextCity", int.class, boolean[].class);

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
