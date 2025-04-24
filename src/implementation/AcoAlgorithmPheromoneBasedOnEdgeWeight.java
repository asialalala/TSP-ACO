package implementation;


public class AcoAlgorithmPheromoneBasedOnEdgeWeight extends AcoAlgorithm {

    public AcoAlgorithmPheromoneBasedOnEdgeWeight(Cities cities, int pheromoneAmount, int numAnts,
            int numIterations, int beta) {
        super(cities, pheromoneAmount, numAnts, numIterations, beta);
    }

    /**
     * Updates pheromone levels based on the edge weight (length).
     * 
     * @param visitedCities the array of visited cities
     * @param currentCity   the current city
     * @param nextCity      the next city to visit
     */
    @Override
    protected void updatePheromones(int currentCity, int nextCity) {
        double deltaPheromone = pheromoneAmount / cities.getDistance(currentCity, nextCity);
        pheromoneMatrix[Utility.getIndex(currentCity, nextCity)] += deltaPheromone;
    }

    /**
     * This method is not used in this algorithm.
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, double distance) {
        throw new UnsupportedOperationException("Invalid operation for update pheromones.");
    }

    /**
     * Runs the ACO algorithm to find the best path. Pheromones are updated after
     * each ant's step.
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
                    updatePheromones(visitedCities, nextCity);

                }

                if (success) {
                    double distance = calculateTotalDistance(path);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPath = path.clone();
                    }
                }
            }
            evaporatePheromones();
        }
    }
}