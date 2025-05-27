package implementation;


import java.util.Random;

public class AcoConstPheromoneUpdateForEveryEdge extends AcoAlgorithm {

    public AcoConstPheromoneUpdateForEveryEdge(Random generator, Cities cities, int pheromoneAmount, int numAnts,
                                               int numIterations, double alpha, double beta, double evaporationRate) {
        super(generator, cities, pheromoneAmount, numAnts, numIterations, alpha, beta, evaporationRate);
    }

    /**
     * This method is not used in this algorithm.
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities, int distance) {
        throw new UnsupportedOperationException("Invalid operation for update pheromones.");

    }

    /**
     * Updates pheromone levels, the same value for every edge.
     */
    @Override
    protected void updatePheromones(int currentCity, int nextCity) {
        double deltaPheromone = pheromoneAmount;
        pheromoneMatrix[Utilities.getIndex(currentCity, nextCity)] += deltaPheromone;
    }

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
                    updatePheromones(currentCity, nextCity);
                    path[step] = nextCity;
                    visitedCities[nextCity] = true;
                    currentCity = nextCity;
                }

                if (success) {
                    // path = super.apply2Opt(path);

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
