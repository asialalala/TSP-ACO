package implementation;
public class Cities {
    private double[][] distanceMatrix;

    public Cities(int size) {
        distanceMatrix = new double[size][size];
        initializeMatrix();
    }

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
                } else if(distanceMatrix[i][j] == -1) {
                    int dist = (i + j) * 10; // Example distance calculation
                    distanceMatrix[i][j] = dist; 
                    distanceMatrix[j][i] = dist; // If the distance is symmetric

                }
            }
        }
    }

    public void printMatrix() {
        for (double[] row : distanceMatrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public double[][] getMatrix() {
        return distanceMatrix;
    }
}
