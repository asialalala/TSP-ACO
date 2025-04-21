package implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Cities {
    // private double[][] distanceMatrix;
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
        // distanceMatrix = new double[numberOfCities][numberOfCities];
        triangleSize = numberOfCities * (numberOfCities - 1) / 2;
        distanceMatrix = new double[triangleSize];
        initializeMatrix();
    }

    /**
     * Initialize the distance matrix with distances between cities
     */
    // TODO initialize distance between cites from provided params (from a file?)
    private void initializeMatrix() {
        for (int i = 0; i < numberOfCities; i++) {
            int cityId1 = cityKeys.get(i);
            Pair<Integer, Integer> coord1 = coordinates.get(cityId1);

            for (int j = 0; j < i; j++) {
                int cityId2 = cityKeys.get(j);
                Pair<Integer, Integer> coord2 = coordinates.get(cityId2);

                double distance = euclideanDistance(coord1, coord2);
                distanceMatrix[getIndex(i, j)] = distance;
            }
        }

//        for (int i = 0; i < numberOfCities; i++) {
//            int cityId1 = cityKeys.get(i);
//            Pair<Integer, Integer> coord1 = coordinates.get(cityId1);
//
//            for (int j = 0; j < numberOfCities; j++) {
//                int cityId2 = cityKeys.get(j);
//                if (i == j) {
//                    distanceMatrix[i][j] = 0;
//                } else if (distanceMatrix[i][j] == 0) {
//                    Pair<Integer, Integer> coord2 = coordinates.get(cityId2);
//                    double distance = euclideanDistance(coord1, coord2);
//
//                    distanceMatrix[i][j] = distance;
//                    distanceMatrix[j][i] = distance;
//                }
//            }
//        }
//        // TODO use triangular matrix to save memory
//        for (int i = 0; i < distanceMatrix.length; i++) {
//            for (int j = 0; j < distanceMatrix[i].length; j++) {
//                distanceMatrix[i][j] = -1; // Initialize with -1 to indicate no distance set
//            }
//        }
//        // Set distances between cities
//        // TODO use triangular matrix to save memory
//        for (int i = 0; i < distanceMatrix.length; i++) {
//            for (int j = 0; j < distanceMatrix[i].length; j++) {
//                if (i == j) {
//                    distanceMatrix[i][j] = 0; // Distance to itself is 0
//                } else if (distanceMatrix[i][j] == -1) {
//                    int dist = (i + j) * 10; // Example distance calculation
//                    distanceMatrix[i][j] = dist;
//                    distanceMatrix[j][i] = dist; // If the distance is symmetric
//
//                }
//            }
//        }
    }

    private int getIndex(int i, int j) {
        if (i < j) {
            int temp = i;
            i = j;
            j = temp;
        }
        return (i * (i - 1)) / 2 + j;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public double getDistance(int i, int j) {
        if (i == j) return 0.0;
        return distanceMatrix[getIndex(i, j)];
    }

    public void printMatrix() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                System.out.printf("%.1f ", getDistance(i, j));
            }
            System.out.println();
        }
    }

//    /**
//     * Print the distance matrix
//     */
//    public void printMatrix() {
//        for (double[] row : distanceMatrix) {
//            for (double value : row) {
//                System.out.print(value + " ");
//            }
//            System.out.println();
//        }
//    }

    /**
     * Get the distance matrix
     * 
     * @return the distance matrix
     */
//    public double[][] getMatrix() {
//        return distanceMatrix;
//    }
    public double[] getMatrix() {
        return distanceMatrix;
    }

    private double euclideanDistance(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        int dx = a.getFirst() - b.getFirst();
        int dy = a.getSecond() - b.getSecond();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
