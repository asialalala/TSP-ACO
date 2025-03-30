public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Cities cities = new Cities(5);
        cities.printMatrix();
        System.out.println("Distance between city 0 and city 1: " + cities.getMatrix()[1][0]);   
        System.out.println("Distance between city 0 and city 1: " + cities.getMatrix()[0][1]);   
    }
}
