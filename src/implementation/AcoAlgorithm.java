package implementation;

public class AcoAlgorithm {

    private double[][] pheromoneMatrix;
    private double[][] distanceMatrix;
    private int numCities;
    private int pheromoneAmount;
    private int numAnts;
    private int numIterations;

    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     */
    public AcoAlgorithm(Cities cities, int pheromoneAmount, int numAnts, int numIterations) {
        this.pheromoneAmount = pheromoneAmount;
        this.distanceMatrix = cities.getMatrix();
        this.numCities = distanceMatrix.length;
        this.numAnts = numAnts;
        this.numIterations = numIterations;

        // Initialize pheromone matrix
        // TODO use triangular matrix to save memory

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] = 1.0; // Initial pheromone level
            }
        }
    }

    /**
     * Chooses the next city based on pheromone levels and distance
     * 
     * @param currentCity   the current city
     * @param visitedCities the array of visited cities
     * @return the next city to visit
     */
    // TODO try to decrise number of loops or devide it to methods
    private int chooseNextCity(int currentCity, boolean[] visitedCities) {
        // Count the sum of pheromone/distance for unvisited cities
        double sum = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                double pheromone = pheromoneMatrix[currentCity][i];
                double distance = distanceMatrix[currentCity][i];
                if (distance <= 0) {
                    return -1;
                }
                sum += pheromone / distance;
            }
        }
        if (sum <= 0) {
            return -1;
        }

        // Full fill the decision vector
        double[] decisionVector = new double[numCities];
        double decisionSum = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                double pheromone = pheromoneMatrix[currentCity][i];
                double distance = distanceMatrix[currentCity][i];
                if (distance <= 0) {
                    return -1;
                }
                decisionVector[i] = (pheromone / distance) / sum;
                decisionSum += decisionVector[i];
            }
        }
        if(decisionSum <= 0) {
            return -1;
        }

        // Choose the next city based on the decision vector
        double[] probabilities = new double[numCities];
        int nextCity = 0;
        double highestProbability = probabilities[0];


        for (int i = 0; i < numCities; i++) {
            if (!visitedCities[i]) {
                probabilities[i] = decisionVector[i]/decisionSum;
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

    /**
     * Updates pheromone levels based on the solution found by the ant
     * 
     * @param visitedCities the array of visited cities
     */
    // TODO use triangular matrix to save memory
    private void updatePheromones(boolean[] visitedCities) {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (visitedCities[i] && visitedCities[j]) {
                    pheromoneMatrix[i][j] += pheromoneAmount / distanceMatrix[i][j];
                }
            }
        }
    }

    /**
     * Evaporates pheromones over time
     */
    private void evaporatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                this.pheromoneMatrix[i][j] *= 0.9; // Example evaporation rate
            }
        }
    }

    /**
     * Runs the ACO algorithm to find the best path
     */
    // TODO add iterations over the ants loop
    public void runAlgorithm() {

        // Main ACO loop
        for (int ant = 0; ant < this.numAnts; ant++) {
            // Initialize base city randomly for each ant
            boolean[] visitedCities = new boolean[numCities];
            int currentCity = (int) (Math.random() * numCities);
            visitedCities[currentCity] = true;

            // Construct solution (visitedCities)
            for (int step = 0; step < numCities - 1; step++) {
                currentCity = chooseNextCity(currentCity, visitedCities);
                visitedCities[currentCity] = true;
            }

            updatePheromones(visitedCities);
        }

        evaporatePheromones();
    }

}
