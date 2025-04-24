package implementation;

import java.io.File;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        boolean visualize = false;

        for (String arg : args) {
            if (arg.equals("-p")) {
                visualize = true;
            }
        }

        TSPReader tspReader = new TSPReader();
        String tspInput = "src/data/xqf131.tsp";
        String fileName = new File(tspInput).getName();

        Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile(tspInput);

        // tspReader.printCoordinates(coordinates);
        Cities cities = new Cities(coordinates);
//        System.out.println("== Distance Matrix ==");
//        // cities.printMatrix();
//
//        System.out.println("\n== Sample Distance ==");
//        System.out.println("Distance between city 1 and city 2: " + cities.getDistance(0, 1));
//        System.out.println("Distance between city 2 and city 5: " + cities.getDistance(1, 4));
//
//        // Start ACO Algorithm
//        System.out.println("\n== Running ACO Algorithm ==");

        AcoAlgorithm aco = new AcoPheromoneUpdateBasedOnPathLength(cities, 100, 50, 100,1);
        aco.runAlgorithm();

        int[] bestPath = aco.getBestPath();
        int bestDistance = aco.getBestDistance();

//        System.out.println("\n== Algorithm Finished ==");
//
//        System.out.println("The best path:");
//        for (int city : bestPath) {
//            System.out.print(city + " ");
//        }
//        System.out.println("\nThe shortest path's length: " + bestDistance);

        if (visualize) {
            System.out.print(fileName + " ");
            for (int i = 0; i < bestPath.length; i++) {
                System.out.print(bestPath[i]);
                if (i < bestPath.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }
}
