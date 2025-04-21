## Description

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
  - `implementation`: the folder with implementation of the problem and the algorithm
  - `test`: the folder with unit tests for the implementation
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

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
The `AcoAlgorithm` class is responsible for running the ant colony optimization algorithm. It includes:
TODO! - finish this description.


