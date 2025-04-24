package implementation;

import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        TSPReader tspReader = new TSPReader();
        Map<Integer, Pair<Integer, Integer>> coordinates = tspReader.readTSPFile("src/data/smallerTSPs/xqf131.tsp");
        // tspReader.printCoordinates(coordinates);
        Cities cities = new Cities(coordinates);
        System.out.println("== Distance Matrix ==");
        cities.printMatrix();

        System.out.println("\n== Sample Distance ==");
        System.out.println("Distance between city 1 and city 2: " + cities.getDistance(0, 1));
        System.out.println("Distance between city 2 and city 5: " + cities.getDistance(1, 4));

        // Start ACO Algorithm
        System.out.println("\n== Running ACO Algorithm ==");

        AcoAlgorithm aco = new AcoAlgorithmConstPheromoneAfterSolution(cities, 100, 50, 100,1);
        aco.runAlgorithm();

        int[] bestPath = aco.getBestPath();
        double bestDistance = aco.getBestDistance();

        System.out.println("\n== Algorithm Finished ==");

        System.out.println("The best path:");
        for (int city : bestPath) {
            System.out.print(city + " ");
        }
        System.out.println("\nThe shortest path's length: " + bestDistance);
    }
}
