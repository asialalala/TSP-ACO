package implementation;


import java.util.Random;

public class AcoAlgorithmPheromoneBasedOnEdgeWeight extends AcoAlgorithm {

    public AcoAlgorithmPheromoneBasedOnEdgeWeight(Random generator, Cities cities, int pheromoneAmount, int numAnts,
                                                  int numIterations, double alpha, double beta, double evaporationRate,
                                                  double pheromoneFactor) {
        super(generator, cities, pheromoneAmount, numAnts, numIterations, alpha, beta, evaporationRate);
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
        pheromoneMatrix[Utilities.getIndex(currentCity, nextCity)] += deltaPheromone;
    }

    /**
     * This method is not used in this algorithm.
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, int distance) {
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
                int currentCity = (int) (super.generator.nextDouble() * numCities);
                path[0] = currentCity;
                visitedCities[currentCity] = true;

                boolean success = true;
                for (int step = 1; step < numCities; step++) {
                    int nextCity = chooseNextCity(currentCity, visitedCities);
                    if (nextCity == -1) { // no move possible
                        success = false;
                        break;
                    }
                    // Update pheromones after each step
                    updatePheromones(currentCity, nextCity);
                    path[step] = nextCity;
                    visitedCities[nextCity] = true;
                    currentCity = nextCity;

                }

                if (success) {
                    int distance = calculateTotalDistance(path);
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