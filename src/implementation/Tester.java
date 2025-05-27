package implementation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Tester {
    private final int NO_VARIANTS = 5;
    private final int RUNS = 5;
    private final String[] variants = {
            "AcoAlgorithmMorePheromoneWhenBetterPath",
            "AcoAlgorithmPheromoneBasedOnEdgeWeight",
            "AcoConstPheromoneUpdateForEveryEdge",
            "AcoPheromoneUpdateBasedOnPathLength",
            "AcoUpdatePheromoneOnlyForBetterPath"
    };

    public void testChosenVariantBig(TSPReader tspReader, Random generator) throws IOException {
        double finalAlpha = 1.0;
        double finalBeta = 5.0;
        double finalPheromoneFactor = 2.0;
        double finalEvaporation = 0.9;
        int finalPheromoneAmount = 50;
        int finalAnts = 100;
        int finalIterations = 100;

        String BIGGER_DATA_DIRECTORY = "bigger_data";

        try (PrintWriter out = new PrintWriter(new FileWriter("variants/final.csv"))) {
            out.println("example,avg_distance,best_distance,avg_time");
            System.out.println("example,avg_distance,best_distance,avg_time");

            try (Stream<Path> paths = Files.list(Paths.get(BIGGER_DATA_DIRECTORY))) {
                paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".tsp"))
                        .forEach(path -> {
                            try {
                                String tspInput = path.toString();
                                System.out.println("Processing file: " + tspInput);

                                Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile(tspInput);
                                Cities cities = new Cities(coordinates);

                                double averageDistance = 0.0;
                                int bestDistance = Integer.MAX_VALUE;
                                double averageTime = 0.0;

                                for (int i = 0; i < RUNS; i++) {
                                    long seed = generator.nextLong();
                                    AcoAlgorithm algo = new AcoAlgorithmPheromoneBasedOnEdgeWeight(new Random(seed),
                                            cities, finalPheromoneAmount, finalAnts, finalIterations,
                                            finalAlpha, finalBeta, finalEvaporation);

                                    long startTime = System.nanoTime();
                                    algo.runAlgorithm();
                                    long endTime = System.nanoTime();
                                    long durationInNano = endTime - startTime;
                                    double durationInSeconds = durationInNano / 1_000_000_000.0;
                                    int receivedDistance = algo.getBestDistance();
                                    averageDistance += receivedDistance;
                                    averageTime += durationInSeconds;

                                    if (receivedDistance < bestDistance) {
                                        bestDistance = receivedDistance;
                                    }
                                }
                                averageDistance /= RUNS;
                                averageTime /= RUNS;
                                System.out.printf(Locale.US, "%s,%.2f,%d,%.2f%n",
                                        tspInput, averageDistance, bestDistance, averageTime);
                                out.printf(Locale.US, "%s,%.2f,%d,%.2f%n",
                                        tspInput, averageDistance, bestDistance, averageTime);
                            } catch (Exception e) {
                                System.err.println("Failed to process " + path + ": " + e.getMessage());
                            }
                        });
            }
        }
    }

    public void testVariantsWithChosenParametersSmall(TSPReader tspReader, Random generator) throws IOException {
        double finalAlpha = 1.0;
        double finalBeta = 5.0;
        double finalPheromoneFactor = 2.0;
        double finalEvaporation = 0.9;
        int finalPheromoneAmount = 50;
        int finalAnts = 100;
        int finalIterations = 100;

        String SMALLER_DATA_DIRECTORY = "smaller_data";

        try (PrintWriter out = new PrintWriter(new FileWriter("variants/variants.csv"))) {
            out.println("variant,example,avg_distance,best_distance,avg_time");

            try (Stream<Path> paths = Files.list(Paths.get(SMALLER_DATA_DIRECTORY))) {
                paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".tsp"))
                        .forEach(path -> {
                            try {
                                String tspInput = path.toString();
                                System.out.println("Processing file: " + tspInput);

                                Map<Integer, Coordinates<Integer, Integer>> coordinates = tspReader.readTSPFile(tspInput);
                                Cities cities = new Cities(coordinates);

                                double[] averageDistances = new double[variants.length];
                                int[] bestDistances = new int[variants.length];
                                for (int i = 0; i < variants.length; i++) {
                                    bestDistances[i] = Integer.MAX_VALUE;
                                }
                                double[] averageTimes = new double[variants.length];

                                for (int i = 0; i < RUNS; i++) {
                                    long seed = generator.nextLong();
                                    for (int j = 0; j < variants.length; j++) {
                                        AcoAlgorithm algo;
                                        switch (j) {
                                            case 1: {
                                                algo = new AcoAlgorithmPheromoneBasedOnEdgeWeight(new Random(seed),
                                                        cities, finalPheromoneAmount, finalAnts, finalIterations,
                                                        finalAlpha, finalBeta, finalEvaporation);
                                                break;
                                            }
                                            case 2: {
                                                algo = new AcoConstPheromoneUpdateForEveryEdge(new Random(seed),
                                                        cities, finalPheromoneAmount, finalAnts, finalIterations,
                                                        finalAlpha, finalBeta, finalEvaporation);
                                                break;
                                            }
                                            case 3: {
                                                algo = new AcoPheromoneUpdateBasedOnPathLength(new Random(seed),
                                                        cities, finalPheromoneAmount, finalAnts, finalIterations,
                                                        finalAlpha, finalBeta, finalEvaporation);
                                                break;
                                            }
                                            case 4: {
                                                algo = new AcoUpdatePheromoneOnlyForBetterPath(new Random(seed),
                                                        cities, finalPheromoneAmount, finalAnts, finalIterations,
                                                        finalAlpha, finalBeta, finalEvaporation);
                                                break;
                                            }
                                            default: {
                                                algo = new AcoAlgorithmMorePheromoneWhenBetterPath(new Random(seed),
                                                        cities, finalPheromoneAmount, finalAnts, finalIterations,
                                                        finalAlpha, finalBeta, finalEvaporation, finalPheromoneFactor);
                                                break;
                                            }
                                        }

                                        long startTime = System.nanoTime();
                                        algo.runAlgorithm();
                                        long endTime = System.nanoTime();
                                        long durationInNano = endTime - startTime;
                                        double durationInMillis = durationInNano / 1_000_000.0;
                                        int receivedDistance = algo.getBestDistance();
                                        averageDistances[j] += receivedDistance;
                                        averageTimes[j] += durationInMillis;

                                        if (receivedDistance < bestDistances[j]) {
                                            bestDistances[j] = receivedDistance;
                                        }
                                    }
                                }

                                for (int i = 0; i < variants.length; i++) {
                                    averageDistances[i] /= RUNS;
                                    averageTimes[i] /= RUNS;
                                    out.printf(Locale.US, "%s,%s,%.2f,%d,%.2f%n",
                                            variants[i], tspInput, averageDistances[i], bestDistances[i], averageTimes[i]);
                                }
                            } catch (Exception e) {
                                System.err.println("Failed to process " + path + ": " + e.getMessage());
                            }
                        });
            }
        }
    }

    public void testVariantsParameters(Cities cities, Random generator) throws IOException {
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

        double[] pheromoneAmountsD = java.util.Arrays.stream(pheromoneAmounts).asDoubleStream().toArray();
        double[] antsArrayD = java.util.Arrays.stream(antsArray).asDoubleStream().toArray();
        double[] iterationsArrayD = java.util.Arrays.stream(iterationsArray).asDoubleStream().toArray();

        int RUNS = 10;
        int NO_VARIANTS = 5;

        for (int variant = 2; variant < NO_VARIANTS; variant++) {
            saveResultsForParameter(variant, RUNS, "alpha", alphas, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "beta", betas, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "evaporationRate", evaporationRates, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "pheromoneFactor", pheromoneFactors, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "pheromoneAmount", pheromoneAmountsD, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "ants", antsArrayD, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);

            saveResultsForParameter(variant, RUNS, "iterations", iterationsArrayD, baseAlpha, baseBeta, baseEvaporation,
                    basePheromoneFactor, basePheromoneAmount, baseAnts, baseIterations, generator, cities);
        }
    }

    private void saveResultsForParameter(
            int variant,
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
        String directory_name = "params_opt/";
        switch (variant) {
            case 0: {
                directory_name += "AcoAlgorithmMorePheromoneWhenBetterPath/";
                break;
            }
            case 1: {
                directory_name += "AcoAlgorithmPheromoneBasedOnEdgeWeight/";
                break;
            }
            case 2: {
                directory_name += "AcoConstPheromoneUpdateForEveryEdge/";
                break;
            }
            case 3: {
                directory_name += "AcoPheromoneUpdateBasedOnPathLength/";
                break;
            }
            case 4: {
                directory_name += "AcoUpdatePheromoneOnlyForBetterPath/";
                break;
            }
            default: {
                directory_name += "AcoAlgorithmMorePheromoneWhenBetterPath/";
            }
        }
        String filename = directory_name + paramName + "_results.csv";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(paramName + ",average_distance,average_time");

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
                double totalTime = 0.0;
                for (int i = 0; i < runs; i++) {
                    AcoAlgorithm algo;
                    switch (variant) {
                        case 1: {
                            algo = new AcoAlgorithmPheromoneBasedOnEdgeWeight(new Random(generator.nextLong()),
                                    cities, pheromoneAmount, ants, iterations,
                                    alpha, beta, evaporation);
                            break;
                        }
                        case 2: {
                            algo = new AcoConstPheromoneUpdateForEveryEdge(new Random(generator.nextLong()),
                                    cities, pheromoneAmount, ants, iterations,
                                    alpha, beta, evaporation);
                            break;
                        }
                        case 3: {
                            algo = new AcoPheromoneUpdateBasedOnPathLength(new Random(generator.nextLong()),
                                    cities, pheromoneAmount, ants, iterations,
                                    alpha, beta, evaporation);
                            break;
                        }
                        case 4: {
                            algo = new AcoUpdatePheromoneOnlyForBetterPath(new Random(generator.nextLong()),
                                    cities, pheromoneAmount, ants, iterations,
                                    alpha, beta, evaporation);
                            break;
                        }
                        default: {
                            algo = new AcoAlgorithmMorePheromoneWhenBetterPath(new Random(generator.nextLong()),
                                    cities, pheromoneAmount, ants, iterations,
                                    alpha, beta, evaporation, pheromoneFactor);
                            break;
                        }
                    }

                    long startTime = System.nanoTime();
                    algo.runAlgorithm();
                    long endTime = System.nanoTime();
                    long durationInNano = endTime - startTime;
                    double durationInMillis = durationInNano / 1_000_000.0;
                    totalDistance += algo.getBestDistance();
                    totalTime += durationInMillis;
                }

                double avgDistance = totalDistance / (double) runs;
                double avgTime = totalTime / (double) runs;
                out.printf(Locale.US, "%.1f,%.2f,%.2f%n", val, avgDistance, avgTime);
            }
        }

        System.out.println("Zapisano wyniki do pliku: " + filename);
    }
}
