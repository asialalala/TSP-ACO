package implementation;

import java.util.Random;

public abstract class AcoAlgorithm {
    protected Random generator;
    protected Cities cities;
    protected double[] pheromoneMatrix;
    protected int numCities;
    protected int pheromoneAmount;
    protected int numAnts;
    protected int numIterations;
    protected double alpha;
    protected double beta;
    protected double evaporationRate;
    protected int[] bestPath;
    protected int bestDistance = Integer.MAX_VALUE;

    public abstract void runAlgorithm();
    protected abstract void updatePheromones(boolean[] visitedCities, int distance);
    protected abstract void updatePheromones(int currentCity, int nextCity);


    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     * @param beta            the parameter for distance influence
     */
    AcoAlgorithm(Random generator, Cities cities, int pheromoneAmount, int numAnts, int numIterations, double alpha, double beta, double evaporationRate) {
        this.generator = generator;
        this.cities = cities;
        this.pheromoneAmount = pheromoneAmount;
        this.numCities = cities.getNumberOfCities();
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;

        // Initialize pheromone matrix
        int triangleSize = numCities * (numCities - 1) / 2;
        this.pheromoneMatrix = new double[triangleSize];
        for (int i = 0; i < triangleSize; i++) {
            pheromoneMatrix[i] = 1.0;
        }
    }

    /**
     * Evaporates pheromones over time
     */
    protected void evaporatePheromones() {
        for (int i = 0; i < pheromoneMatrix.length; i++) {
            pheromoneMatrix[i] *= evaporationRate;
        }
    }

    /**
     * Calculates the total distance of a given path
     * 
     * @param path the current path
     */
    protected int calculateTotalDistance(int[] path) {
        int total = 0;
        for (int i = 0; i < path.length - 1; i++) {
            total += cities.getDistance(path[i], path[i + 1]);
        }
        // Add distance from last city to the first city to complete the cycle
        total += cities.getDistance(path[path.length - 1], path[0]);
        return total;
    }

    /**
     * Chooses the next city based on pheromone levels and distance
     * 
     * @param currentCity   the current city
     * @param visitedCities the array of visited cities
     * @return the next city to visit
     */
    // TODO try to decrise number of loops or devide it to methods
    protected int chooseNextCity(int currentCity, boolean[] visitedCities) {
        double[] probabilities = new double[numCities];
        double sum = 0.0;

        // Oblicz prawdopodobieństwo wyboru dla każdego nieodwiedzonego miasta
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                double pheromone = Math.pow(pheromoneMatrix[Utilities.getIndex(currentCity, i)], alpha);
                int distance = cities.getDistance(currentCity, i);
                if (distance <= 0) return -1; // błąd w danych
                double desirability = pheromone * Math.pow(1.0 / distance, beta);
                probabilities[i] = desirability;
                sum += desirability;
            } else {
                probabilities[i] = 0;
            }
        }

        if (sum <= 0) return -1;

        // Normalizacja do prawdopodobieństw
        for (int i = 0; i < numCities; i++) {
            probabilities[i] /= sum;
        }

        // Stochastyczny wybór na podstawie rozkładu
        double rand = generator.nextDouble();
        double cumulative = 0.0;

        for (int i = 0; i < numCities; i++) {
            cumulative += probabilities[i];
            if (rand <= cumulative) {
                return i;
            }
        }

        // Fallback – w przypadku błędów numerycznych
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                return i;
            }
        }

        return -1;
    }

    protected int[] apply2Opt(int[] path) {
        boolean improvement = true;
        int size = path.length;

        while (improvement) {
            improvement = false;
            for (int i = 1; i < size - 2; i++) {
                for (int j = i + 1; j < size - 1; j++) {
                    int a = path[i - 1];
                    int b = path[i];
                    int c = path[j];
                    int d = path[j + 1];

                    int currentDistance = cities.getDistance(a, b) + cities.getDistance(c, d);
                    int newDistance = cities.getDistance(a, c) + cities.getDistance(b, d);

                    if (newDistance < currentDistance) {
                        reverseSubPath(path, i, j);
                        improvement = true;
                    }
                }
            }
        }

        return path;
    }

    private void reverseSubPath(int[] path, int start, int end) {
        while (start < end) {
            int temp = path[start];
            path[start] = path[end];
            path[end] = temp;
            start++;
            end--;
        }
    }


    public int[] getBestPath() {
        return bestPath;
    }

    public int getBestDistance() {
        return bestDistance;
    }
}
