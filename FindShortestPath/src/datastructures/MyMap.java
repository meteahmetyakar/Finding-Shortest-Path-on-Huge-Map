package datastructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * it reads txt file as matrix
 */
public class MyMap {
    private int[] startPoint;
    private int[] endPoint;

    private int[][] map;
    private int rowSize;

    private int colSize;
    private String fileName;

    /**
     * @return returns start point
     */
    public int[] getStartPoint() {
        return startPoint;
    }

    /**
     * @return returns end point
     */
    public int[] getEndPoint() {
        return endPoint;
    }

    /**
     * @return returns map
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @return returns row size
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * @return returns col size
     */
    public int getColSize() {
        return colSize;
    }

    /**
     * @return returns file name of given map
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * constructor of Map
     * @param fileName file name of map
     */
    public MyMap(String fileName) {
        try
        {
            File file = new File(fileName);
            this.fileName = file.getName();
            Scanner scanner = new Scanner(file);
            String[] start = scanner.nextLine().split(",");
            startPoint = new int[]{Integer.parseInt(start[0]), Integer.parseInt(start[1])};

            String[] end = scanner.nextLine().split(",");
            endPoint = new int[]{Integer.parseInt(end[0]), Integer.parseInt(end[1])};

            List<String> lines = new ArrayList<>();

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                lines.add(line);
            }

            rowSize = lines.size();
            colSize = lines.get(0).split(",").length;

            map = new int[rowSize][colSize];
            for (int i = 0; i < rowSize; i++)
            {
                String[] values = lines.get(i).split(",");

                for(int j = 0; j<colSize; j++)
                    map[i][j] = Integer.parseInt(values[j]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}




