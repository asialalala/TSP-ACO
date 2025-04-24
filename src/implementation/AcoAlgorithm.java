package implementation;

public abstract class AcoAlgorithm {
 
    protected Cities cities;
    protected double[] pheromoneMatrix;
    protected int numCities;
    protected int pheromoneAmount;
    protected int numAnts;
    protected int numIterations;
    protected int beta;
    protected int[] bestPath;
    protected double bestDistance = Double.MAX_VALUE;

    public abstract void runAlgorithm();
    protected abstract void updatePheromones(boolean[] visitedCities);

    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     * @param beta           the parameter for distance influence
     */
    AcoAlgorithm(Cities cities, int pheromoneAmount, int numAnts, int numIterations, int beta) {
        this.cities = cities;
        this.pheromoneAmount = pheromoneAmount;
        this.numCities = cities.getNumberOfCities();
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.beta = beta;

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
            pheromoneMatrix[i] *= 0.9;
        }
    }

    /**
     * Calculates the total distance of a given path
     * 
     * @param path the current path
     */
    protected double calculateTotalDistance(int[] path) {
        double total = 0;
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
        // Count the sum of pheromone/(distance^beta) for unvisited cities
        double sum = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                double pheromone = pheromoneMatrix[Utility.getIndex(currentCity, i)];
                double distance = cities.getDistance(currentCity, i);
                if (distance <= 0) {
                    return -1;
                }
                sum += pheromone / Math.pow(distance, beta);
            }
        }
        if (sum <= 0) {
            return -1;
        }

        // Fulfill the decision vector
        double[] decisionVector = new double[numCities];
        double decisionSum = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                double pheromone = pheromoneMatrix[Utility.getIndex(currentCity, i)];
                double distance = cities.getDistance(currentCity, i);
                if (distance <= 0) {
                    return -1;
                }
                decisionVector[i] = (pheromone / Math.pow(distance, beta)) / sum;
                decisionSum += decisionVector[i];
            }
        }
        if (decisionSum <= 0) {
            return -1;
        }

        // Choose the next city based on the decision vector
        double[] probabilities = new double[numCities];
        int nextCity = 0;
        double highestProbability = -1.0;

        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                probabilities[i] = decisionVector[i] / decisionSum;
            } else {
                probabilities[i] = 0;
            }
            if (probabilities[i] > highestProbability) {
                highestProbability = probabilities[i];
                nextCity = i;
            }
        }

        return nextCity;
    }

    public int[] getBestPath() {
        return bestPath;
    }

    public double getBestDistance() {
        return bestDistance;
    }
}
