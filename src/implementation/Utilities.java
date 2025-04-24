package implementation;

public final class Utilities {

    private Utilities() {
    }

    /**
     * Get the index in the distance matrix for the given city keys id
     *
     * @param i - first city key id
     * @param j - second city key id
     * @return the index in the distance matrix
     */
    // TODO: should it thow exception if i == j?
    public static int getIndex(int i, int j) {
        if (i == j) {
            return -1; // Invalid index for the same city
        }
        if (i < j) {
            int temp = i;
            i = j;
            j = temp;
        }
        return i * (i - 1) / 2 + j;
    }

    /**
     * Get the distance between a and b
     * 
     * @param a - the first city
     * @param b - the second city
     * @return the coordinates of the cities
     */
    public static int euclideanDistance(Coordinates<Integer, Integer> a, Coordinates<Integer, Integer> b) {
        int dx = a.getFirst() - b.getFirst();
        int dy = a.getSecond() - b.getSecond();
        return (int) Math.round(Math.sqrt((double) (dx * dx) + dy * dy));
    }
}
