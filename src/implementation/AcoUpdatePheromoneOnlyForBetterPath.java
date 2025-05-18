package implementation;


import java.util.Random;

public class AcoUpdatePheromoneOnlyForBetterPath extends AcoAlgorithm {

    public AcoUpdatePheromoneOnlyForBetterPath(Random generator, Cities cities, int pheromoneAmount, int numAnts,
                                               int numIterations, double alpha, double beta, double evaporationRate) {
        super(generator, cities, pheromoneAmount, numAnts, numIterations, alpha, beta, evaporationRate);
    }

    /**
     * Updates pheromone levels based on the solution found by the ant.
     * Pheromones are only updated if the solution is better than the previous best.
     * The amount of pheromones is constant for every visited edge and depends on
     * the path length.
     * 
     * @param visitedCities the array of visited cities
     * @param distance      the distance of the path
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, int distance) {
        double deltaPheromone = pheromoneAmount / distance;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j && visitedCities[i] && visitedCities[j]) {
                    pheromoneMatrix[Utilities.getIndex(i, j)] += deltaPheromone;
                }
            }
        }
    }

    /**
     * This method is not used in this algorithm.
     */
    @Override
    protected void updatePheromones(int currentCity, int nextCity) {
        throw new UnsupportedOperationException("Invalid operation for update pheromones.");
    }

    /**
     * Runs the ACO algorithm to find the best path. Pheromones are updated only for
     * better solutions.
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
                    path[step] = nextCity;
                    visitedCities[nextCity] = true;
                    currentCity = nextCity;
                }

                if (success) {
                    int distance = calculateTotalDistance(path);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPath = path.clone();
                        updatePheromones(visitedCities, distance);
                    }
                }
            }
        }
    }

}
