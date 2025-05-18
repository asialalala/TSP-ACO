package implementation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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

//        AcoAlgorithmMorePheromoneWhenBetterPath morePheromone;
//        AcoAlgorithm[] variants = new AcoAlgorithm[] {
//                AcoAlgorithm morePheromone = new AcoAlgorithmMorePheromoneWhenBetterPath(cities, 100, 50, 100,1)
//        }

        Random generator = new Random(42);
        double baseAlpha = 1.0;
        double baseBeta = 2.0;
        double basePheromoneFactor = 2.0;
        double baseEvaporation = 0.7;
        int basePheromoneAmount = 100;
        int baseAnts = 50;
        int baseIterations = 100;


        double[] alphas = {0.5, 1.0, 2.0, 5.0, 10.0};
        double[] betas = {0.5, 1.0, 2.0, 3.0, 4.0, 5.0, 10.0};
        double[] pheromoneFactors = {1.5, 2.0, 2.5, 3.0, 10.0};
        double[] evaporationRates = {0.1, 0.3, 0.5, 0.7, 0.9};
        int[] pheromoneAmounts = {50, 100, 150, 200, 250};
        int[] antsArray = {10, 50, 100, 150, 200};
        int[] iterationsArray = {50, 100, 150, 200, 250};

//        try (PrintWriter out = new PrintWriter(new FileWriter("alpha_results.csv"))) {
//            out.println("alpha,distance");
//
//            for (double alpha : alphas) {
//                AcoAlgorithm algo = new AcoAlgorithmMorePheromoneWhenBetterPath(
//                        generator, cities, basePheromoneAmount, baseAnts, baseIterations,
//                        alpha, baseBeta, baseEvaporation, basePheromoneFactor
//                );
//
//                algo.runAlgorithm();
//                int distance = algo.getBestDistance();
//                out.printf(Locale.US, "%.1f,%d%n", alpha, distance);
//            }
//        }

        double[] pheromoneAmountsD = java.util.Arrays.stream(pheromoneAmounts).asDoubleStream().toArray();
        double[] antsArrayD = java.util.Arrays.stream(antsArray).asDoubleStream().toArray();
        double[] iterationsArrayD = java.util.Arrays.stream(iterationsArray).asDoubleStream().toArray();

        int RUNS = 10;

        saveResultsForParameter(RUNS, "alpha", alphas, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "beta", betas, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "evaporationRate", evaporationRates, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "pheromoneFactor", pheromoneFactors, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "pheromoneAmount", pheromoneAmountsD, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "ants", antsArrayD, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

        saveResultsForParameter(RUNS, "iterations", iterationsArrayD, baseAlpha, baseBeta, baseEvaporation,
                basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);



//        for (double alpha : alphas) {
//            for (double beta : betas) {
//                for (double evaporationRate : evaporationRates) {
//                    for (int pheromoneAmount : pheromoneAmounts) {
//                        for (int ants : antsArray) {
//                            for (int iterations : iterationsArray) {
//                                for (double phe : pheromoneFactor) {
//                                    AcoAlgorithm algo = new AcoAlgorithmMorePheromoneWhenBetterPath(
//                                            generator, cities, pheromoneAmount, ants, iterations, alpha, beta,
//                                            evaporationRate, phe);
//
//                                    algo.runAlgorithm();
//                                    System.out.println(String.format("%d & %d & %d & %.1f & %.1f & %.1f & %.1f & %d \\\\",
//                                            pheromoneAmount, ants, iterations, alpha, beta, evaporationRate,
//                                            phe, algo.getBestDistance()));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

//        AcoAlgorithm aco = new AcoAlgorithmMorePheromoneWhenBetterPath(cities, 100, 50,
//                100,1.0, 10.0, 0.9, 2.0);
//        aco.runAlgorithm();
//
//        int[] bestPath = aco.getBestPath();
//        int bestDistance = aco.getBestDistance();

//        System.out.println("\n== Algorithm Finished ==");
//
//        System.out.println("The best path:");
//        for (int city : bestPath) {
//            System.out.print(city + " ");
//        }
//        System.out.println("\nThe shortest path's length: " + bestDistance);

//        if (visualize) {
//            System.out.print(fileName + " ");
//            for (int i = 0; i < bestPath.length; i++) {
//                System.out.print(bestPath[i]);
//                if (i < bestPath.length - 1) {
//                    System.out.print(",");
//                }
//            }
//            System.out.println();
//        }
    }

    public static void saveResultsForParameter(
            int runs,
            String paramName,
            double[] paramValues,
            double baseAlpha,
            double baseBeta,
            double baseEvaporation,
            double basePheromoneFactor,
            int basePheromoneAmount,
            int baseAnts,
            int baseIterations,
            Random generator,
            Cities cities
    ) throws IOException {
        String filename = paramName + "_results.csv";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(paramName + ",average_distance");

            for (double val : paramValues) {
                // Ustaw zmienne lokalne na bazowe
                double alpha = baseAlpha;
                double beta = baseBeta;
                double evaporation = baseEvaporation;
                double pheromoneFactor = basePheromoneFactor;
                int pheromoneAmount = basePheromoneAmount;
                int ants = baseAnts;
                int iterations = baseIterations;

                // Podmień odpowiedni parametr
                switch (paramName) {
                    case "alpha": alpha = val; break;
                    case "beta": beta = val; break;
                    case "evaporationRate": evaporation = val; break;
                    case "pheromoneFactor": pheromoneFactor = val; break;
                    case "pheromoneAmount": pheromoneAmount = (int) val; break;
                    case "ants": ants = (int) val; break;
                    case "iterations": iterations = (int) val; break;
                    default:
                        System.out.println("Nieznany parametr: " + paramName);
                        return;
                }

                int totalDistance = 0;
                for (int i = 0; i < runs; i++) {
                    AcoAlgorithm algo = new AcoAlgorithmMorePheromoneWhenBetterPath(
                            new Random(generator.nextLong()), // nowy seed dla każdej próby
                            cities, pheromoneAmount, ants, iterations,
                            alpha, beta, evaporation, pheromoneFactor
                    );
                    algo.runAlgorithm();
                    totalDistance += algo.getBestDistance();
                }

                double avgDistance = totalDistance / (double) runs;
                out.printf(Locale.US, "%.1f,%.2f%n", val, avgDistance);
            }
        }

        System.out.println("Zapisano wyniki do pliku: " + filename);
    }
}
