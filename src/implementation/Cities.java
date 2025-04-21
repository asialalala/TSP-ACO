package implementation;

public class Cities {
    private double[][] distanceMatrix;

    /**
     * Constructor to initialize the distance matrix
     * 
     * @param size the number of cities
     */
    public Cities(int size) {
        distanceMatrix = new double[size][size];
        initializeMatrix();
    }

    /**
     * Initialize the distance matrix with distances between cities
     */
    // TODO initialize distance between cites from provided params (from a file?)
    private void initializeMatrix() {
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = -1; // Initialize with -1 to indicate no distance set
            }
        }
        // Set distances between cities
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0; // Distance to itself is 0
                } else if (distanceMatrix[i][j] == -1) {
                    int dist = (i + j) * 10; // Example distance calculation
                    distanceMatrix[i][j] = dist;
                    distanceMatrix[j][i] = dist; // If the distance is symmetric

                }
            }
        }
    }

    /**
     * Print the distance matrix
     */
    public void printMatrix() {
        for (double[] row : distanceMatrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    /**
     * Get the distance matrix
     * 
     * @return the distance matrix
     */
    public double[][] getMatrix() {
        return distanceMatrix;
    }
}
