package implementation.AcoAlgorithms;

import implementation.Cities;

public class AcoAlgorithmUpdatePheromoneOnlyForBetterSolution extends AcoAlgorithm {

    public AcoAlgorithmUpdatePheromoneOnlyForBetterSolution(Cities cities, int pheromoneAmount, int numAnts,
            int numIterations, int beta) {
        super(cities, pheromoneAmount, numAnts, numIterations, beta);
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
    protected void updatePheromones(boolean[] visitedCities, double distance) {
        if (distance < bestDistance) {
            for (int i = 0; i < visitedCities.length; i++) {
                if (visitedCities[i]) {
                    pheromoneMatrix[i] += pheromoneAmount / distance;
                }
            }
        }
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
                        updatePheromones(visitedCities, distance);
                    }
                }
            }
        }
    }

}
