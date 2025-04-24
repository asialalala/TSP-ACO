package implementation.AcoAlgorithms;

import implementation.Cities;
import implementation.Utility;

public class AcoPheromoneUpdateBasedOnPathLength extends AcoAlgorithm {

    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     * @param beta            the parameter for distance influence
     */

    public AcoPheromoneUpdateBasedOnPathLength(Cities cities, int pheromoneAmount, int numAnts, int numIterations,
            int beta) {
        super(cities, pheromoneAmount, numAnts, numIterations, beta);
    }


    /**
     * Updates pheromone levels based on the solution found by the ant.
     * The amount of pheromones is const for every visited edge and depends on the
     * path length.
     * 
     * @param visitedCities the array of visited cities
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, double distance) {
        double deltaPheromone = pheromoneAmount / distance;
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
     * For every solution amount of pheromones is the same
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
                    }
                    updatePheromones(visitedCities, distance);
                }
            }

            evaporatePheromones();
        }
    }
}
