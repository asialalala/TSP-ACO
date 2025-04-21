package implementation;

import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Cities cities = new Cities(5);
        cities.printMatrix();
        System.out.println("Distance between city 0 and city 1: " + cities.getMatrix()[1][0]);   
        System.out.println("Distance between city 0 and city 1: " + cities.getMatrix()[0][1]);
        TSPReader tspReader = new TSPReader();
        Map<Integer, Pair<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/smallerTSPs/xqf131.tsp");
        tspReader.printCoordinates(coordinates);
    }
}
