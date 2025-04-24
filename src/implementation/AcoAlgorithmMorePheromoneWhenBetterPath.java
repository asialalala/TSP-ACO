package implementation;


public class AcoAlgorithmMorePheromoneWhenBetterPath extends AcoAlgorithm {
    private boolean isBetterSolution = false;
    private double pheromoneFactor;

    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     * @param beta            the parameter for distance influence
     */
    public AcoAlgorithmMorePheromoneWhenBetterPath(Cities cities, int pheromoneAmount, int numAnts,
            int numIterations,
            int beta, double pheromoneFactor) {
        this.pheromoneFactor = pheromoneFactor;
        super(cities, pheromoneAmount, numAnts, numIterations, beta);
    }

    /**
     * Updates pheromone levels based on the solution found by the ant.
     * If the solution is better then ant leaves more pheromone on the path.
     * The amount of pheromones is constant for every visited edge and depends on
     * the path length.
     * 
     * @param visitedCities the array of visited cities
     * @param distance      the distance of the path
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, double distance) {
        double deltaPheromone = 0;
        if (isBetterSolution) {
            deltaPheromone = pheromoneAmount * pheromoneFactor / distance;

        } else {
            deltaPheromone = pheromoneAmount / distance;

        }
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j && visitedCities[i] && visitedCities[j]) {
                    pheromoneMatrix[Utility.getIndex(i, j)] += deltaPheromone;
                }
            }
        }
    }

    /**
     * This method is not used in this algorithm.
     */
    @Override
    protected void updatePheromones(int currentCity, int nextCity){
        throw new UnsupportedOperationException("Invalid operation for update pheromones.");
    }

    /**
     * Runs the ACO algorithm to find the best path. Pheromones are updated after
     * each ant's solution.
     * For better solution pheromones are multiplied by the given factor.
     */
    @Override
    public void runAlgorithm() {
        for (int iteration = 0; iteration < this.numIterations; iteration++) {
            for (int ant = 0; ant < this.numAnts; ant++) {
                boolean[] visitedCities = new boolean[numCities];
                int[] path = new int[numCities];
                int currentCity = (int) (Math.random() * numCities);
                path[0] = currentCity;
                visitedCities[currentCity] = true;

                boolean success = true;
                for (int step = 1; step < numCities; step++) {
                    int nextCity = chooseNextCity(currentCity, visitedCities);
                    if (nextCity == -1) { // no move possible
                        success = false;
                        break;
                    }
                    path[step] = nextCity;
                    visitedCities[nextCity] = true;
                    currentCity = nextCity;
                }

                if (success) {
                    double distance = calculateTotalDistance(path);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPath = path.clone();
                        isBetterSolution = true;
                    }
                    updatePheromones(visitedCities, distance);
                }
                isBetterSolution = false; // Reset the flag for the next ant
            }

            evaporatePheromones();
        }
    }

}