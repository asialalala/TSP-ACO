package implementation;

import java.io.File;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: ./run.sh <path_to_file> [-p]");
            return;
        }

        boolean visualize = false;

        String tspInput = args[0];


        if (args.length == 2) {
            if (args[1].equals("-p")) {
                visualize = true;
            }
            else {
                System.err.println("Usage: ./run.sh <path_to_file> [-p]");
                return;
            }
        }

        TSPReader tspReader = new TSPReader();
        String fileName = new File(tspInput).getName();

        Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile(tspInput);

        Cities cities = new Cities(coordinates);

        Random generator = new Random();

        // FINAL PARAMETERS
        double finalAlpha = 1.0;
        double finalBeta = 5.0;
        double finalPheromoneFactor = 2.0;
        double finalEvaporation = 0.9;
        int finalPheromoneAmount = 50;
        int finalAnts = 100;
        int finalIterations = 100;

        AcoAlgorithm aco = new AcoAlgorithmPheromoneBasedOnEdgeWeight(generator, cities, finalPheromoneAmount, finalAnts,
                finalIterations,finalAlpha, finalBeta, finalEvaporation);
        aco.runAlgorithm();

        int[] bestPath = aco.getBestPath();
        int bestDistance = aco.getBestDistance();

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
        else {
            System.out.println("TSP instance: " + fileName);
            System.out.println("The best path:");
            for (int city : bestPath) {
                System.out.print(city + " ");
            }
            System.out.println("\nThe shortest path's length: " + bestDistance);
        }
    }
}
