package implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Cities {
    private double[] distanceMatrix;
    private Map<Integer, Pair<Integer, Integer>> coordinates;
    private List<Integer> cityKeys;
    private final int numberOfCities;
    private final int triangleSize;

    /**
     * Constructor to initialize the distance matrix
     *
     * @param coordinates - coordinates of all cities
     */
    public Cities(Map<Integer, Pair<Integer, Integer>> coordinates) {
        this.coordinates = coordinates;
        this.numberOfCities = coordinates.size();
        this.cityKeys = new ArrayList<>(coordinates.keySet());
        Collections.sort(this.cityKeys);
        triangleSize = numberOfCities * (numberOfCities - 1) / 2;
        distanceMatrix = new double[triangleSize];
        initializeMatrix();
    }

    /**
     * Initialize the distance matrix with distances between cities
     */
    private void initializeMatrix() {
        for (int i = 0; i < numberOfCities; i++) {
            int cityId1 = cityKeys.get(i);
            Pair<Integer, Integer> coord1 = coordinates.get(cityId1);

            for (int j = 0; j < i; j++) {
                int cityId2 = cityKeys.get(j);
                Pair<Integer, Integer> coord2 = coordinates.get(cityId2);

                double distance = Utility.euclideanDistance(coord1, coord2);
                distanceMatrix[getIndex(i, j)] = distance;
            }
        }

    }

    /**
     * Get the index in the distance matrix for the given city keys id
     *
     * @param i - first city key id
     * @param j - second city key id
     * @return the index in the distance matrix
     */
    private int getIndex(int i, int j) {
        if (i < j) {
            int temp = i;
            i = j;
            j = temp;
        }
        return (i * (i - 1)) / 2 + j;
    }

    /**
     * 
     * @return number of cities
     */
    public int getNumberOfCities() {
        return numberOfCities;
    }

    /**
     * Get the distance between two cities
     *
     * @param i - first city index
     * @param j - second city index
     * @return the distance between the two cities
     */
    public double getDistance(int i, int j) {
        if (i == j)
            return 0.0;
        return distanceMatrix[getIndex(i, j)];
    }

    /**
     * Print the distance matrix
     */
    public void printMatrix() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                System.out.printf("%.1f ", getDistance(i, j));
            }
            System.out.println();
        }
    }

    /**
     * Get the distance matrix
     * 
     * @return the distance matrix
     */
    public double[] getMatrix() {
        return distanceMatrix;
    }
}
