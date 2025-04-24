package implementation;

import java.io.*;
import java.util.*;

public class TSPReader {
    public Map<Integer, Coordinates<Integer, Integer>> readTSPFile(String filePath) throws IOException {
        Map<Integer, Coordinates<Integer, Integer>> coordinates = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean coordSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("NODE_COORD_SECTION")) {
                    coordSection = true;
                    continue;
                } else if (coordSection && line.equals("EOF")) {
                    break;
                }

                if (coordSection) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 3) {
                        int node = Integer.parseInt(parts[0]);
                        int x = (int) Double.parseDouble(parts[1]);
                        int y = (int) Double.parseDouble(parts[2]);
                        coordinates.put(node, new Coordinates<>(x, y));
                    }
                }
            }
        }

        return coordinates;
    }

    public void printCoordinates(Map<Integer, Coordinates<Integer, Integer>> coordinates) {
        for (Map.Entry<Integer, Coordinates<Integer, Integer>> entry : coordinates.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
