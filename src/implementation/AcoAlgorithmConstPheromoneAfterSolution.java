package implementation;

public class AcoAlgorithmConstPheromoneAfterSolution extends AcoAlgorithm {
   
    /**
     * Constructor to initialize the ACO algorithm
     * 
     * @param cities          the cities object containing the distance matrix
     * @param pheromoneAmount the amount of pheromone to be used
     * @param numAnts         the number of ants to be used
     * @param numIterations   the number of iterations to be performed
     * @param beta           the parameter for distance influence
     */

    public AcoAlgorithmConstPheromoneAfterSolution(Cities cities, int pheromoneAmount, int numAnts, int numIterations, int beta) {
        super(cities, pheromoneAmount, numAnts, numIterations, beta);
    }


    /**
     * Updates pheromone levels based on the solution found by the ant
     * 
     * @param visitedCities the array of visited cities
     */
    @Override
    protected void updatePheromones(boolean[] visitedCities) {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j && visitedCities[i] && visitedCities[j]) {
                    pheromoneMatrix[Utility.getIndex(i, j)] += pheromoneAmount / cities.getDistance(i, j);
                }
            }
        }
    }

    /**
     * Runs the ACO algorithm to find the best path
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
                    updatePheromones(visitedCities);
                }
            }

            evaporatePheromones();
        }
    }
}
