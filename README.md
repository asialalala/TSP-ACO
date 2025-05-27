## Description

This repository contains an implementation of the Ant Colony Optimization (ACO) algorithm for solving the Traveling Salesman Problem (TSP).

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
  - `implementation`: the folder with the implementation of the problem and the algorithm
  - `test`: the folder with unit tests for the implementation
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Usage

To run the project and solve a .tsp file instance using the ACO algorithm, use the provided `run.sh` script.

**Requirements**
- Java (e.g., OpenJDK)
- Python 3 (only if you want to visualize results)
- Required Python packages (e.g., matplotlib, networkx)
- Compiled Java classes in out/production/TSP-ACO

**Running the Algorithm**

Use the Bash script like this:

`./run.sh <path_to_tsp_file> [-p]`

- `<path_to_tsp_file>`: Path to the .tsp input file (required).

- `-p`: Optional flag to visualize the result using a Python script.

The script will print the best path and its length. If `-p` is provided, the best path will also be passed to the Python visualizer.

## Cities Class

The `Cities` class is responsible for managing the distance matrix between cities. It includes:
- **Matrix Initialization**: Initializes a symmetric matrix with distances between cities.
- **Matrix Printing**: Prints the matrix to the console.
- **Matrix Access**: Provides access to the distance matrix for further computations.

Example usage:
```java
Cities cities = new Cities(5);
cities.printMatrix();
double[][] matrix = cities.getMatrix();
```

## AcoAlgorithm Class

The `AcoAlgorithm` class is responsible for running the Ant Colony Optimization (ACO) algorithm to solve the Traveling Salesman Problem (TSP). It includes:
- **Pheromone Initialization**: Initializes the pheromone matrix for the algorithm.
- **City Selection**: Chooses the next city to visit based on pheromone levels and distances.
- **Pheromone Update**: Updates pheromone levels based on the solutions found by ants.
- **Evaporation**: Simulates pheromone evaporation over time.
- **Algorithm Execution**: Runs the ACO algorithm for a specified number of iterations and ants.

Example usage:
```java
AcoAlgorithm aco = new AcoAlgorithm(cities, 100, 50, 100, 1);
aco.runAlgorithm();

int[] bestPath = aco.getBestPath();
double bestDistance = aco.getBestDistance();

System.out.println("Best Path: " + Arrays.toString(bestPath));
System.out.println("Best Distance: " + bestDistance);
```

