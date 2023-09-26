package datastructures;

import color.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * It creates graph with given map
 */
public class MyGraph
{
    /**
     * node which have vertex and weight
     */
    class Node{
        int vertex;
        int weight;
        public Node(int vertex, int weight){
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    /**
     * Used algoritmhs
     */
    public enum Algorithm{
        DIJKSTRA,
        BFS;

        public int getIndex()
        {
            switch (this)
            {
                case DIJKSTRA:
                    return 0;
                case BFS:
                    return 1;
                default:
                    return -1;
            }
        }

    }

    /**
     * it kept shortestPaths
     */
    private int[][] shortestPaths;

    public int[][] getShortestPaths()
    {
        return shortestPaths;
    }

    /**
     * graph for map, it using adjacent list tecnique
     */
    private Map<Integer, List<Node>> graph;

    /**
     * this hashmap is used to get the coordinate of the node in the map.
     */
    private Map<Integer, String> nodeToCoordinate;
    /**
     * this hashmap is used to get the node of the coordinate in the map.
     */
    private Map<String, Integer> coordinateToNode;
    /**
     * node count
     */
    private int nodeSize;
    /**
     * the given map as parameter
     */
    private MyMap map;

    /**
     * create graph with given map
     * @param map map
     */
    public MyGraph(MyMap map) {
        this.map = map;

        shortestPaths = new int[2][]; //index 0 for dijkstra, index 1 for BFS
        nodeToCoordinate = new HashMap<>();
        coordinateToNode = new HashMap<>();
        nodeSize = 0;

        // initialize nodeSize, nodeToCoordinate and coordinateToNode maps
        for (int i = 0; i < map.getRowSize(); i++)
        {
            for(int j = 0; j<map.getColSize(); j++)
            {
                if(map.getMap()[i][j] == 0)
                {
                    String tmp = i + "-" + j; //take coordinate as String
                    nodeToCoordinate.put(nodeSize,tmp);
                    coordinateToNode.put(tmp,nodeSize);
                    nodeSize++;
                }
            }
        }

        graph = new HashMap<>();

        // creating graph with nodes and its neighbors
        for (int i = 0; i < map.getRowSize(); i++)
        {
            for (int j = 0; j < map.getColSize(); j++)
            {
                if(map.getMap()[i][j] == 0)
                {
                    Integer u = coordinateToNode.get(i+"-"+j);
                    graph.putIfAbsent(u, new ArrayList<>());

                    if(i >= 1 && map.getMap()[i-1][j] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i-1)+"-"+j), 1));

                    if(i+1 < map.getRowSize() && map.getMap()[i+1][j] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i+1)+"-"+j),1));

                    if(j >= 1 && map.getMap()[i][j-1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get(i+"-"+(j-1)),1));

                    if(j+1 < map.getColSize() && map.getMap()[i][j+1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get(i+"-"+(j+1)),1));

                    if(i >= 1 && j >=1 && map.getMap()[i-1][j-1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i-1)+"-"+(j-1)),1));

                    if(i >= 1 && j+1 < map.getColSize() && map.getMap()[i-1][j+1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i-1)+"-"+(j+1)),1));

                    if(i+1 < map.getRowSize() && j >= 1 && map.getMap()[i+1][j-1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i+1)+"-"+(j-1)),1));

                    if(i+1 < map.getRowSize() && j+1 < map.getColSize() && map.getMap()[i+1][j+1] == 0)
                        graph.get(u).add(new Node(coordinateToNode.get((i+1)+"-"+(j+1)),1));

                }
            }
        }


    }

    /**
     * implementation of the dijkstra algorithm
     * @param start start coordinates
     * @param end end coordinates
     * @return distance from start to end
     */
    public int findPathByDijkstra(int[] start, int[] end) {
        int src;
        int dest;
        try
        {
            //if given start and end coordinates doesn't convert to nodes, it throws NullPointerException, this mean is given coordinates are invalid
            src = coordinateToNode.get(start[0]+"-"+start[1]);
            dest = coordinateToNode.get(end[0]+"-"+end[1]);
        } catch (NullPointerException e)
        {
            return -2;
        }

        /**
         * hold distance from start to currentNode (every index is a node, distance[2] hold from start to node2 distance)
         */
        int[] distance = new int[nodeSize];
        Arrays.fill(distance, Integer.MAX_VALUE);

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));

        /**
         * each index corresponds to a node, and the values at each index indicate the source node from which it is reached.
         */
        int[] path = new int[nodeSize];
        //If path[dest] is still -1 at the end of the program, it means that the target was not reached.
        path[dest] = -1;

        distance[src] = 0;
        queue.add(new Node(src,0));

        /**
         * dijkstra algorithm
         */
        while(!queue.isEmpty())
        {
            Node currNode = queue.poll(); //take the smallest one in Priority Queue

            for(Node childNode : graph.get(currNode.vertex))
            {
                if(distance[childNode.vertex] > distance[currNode.vertex] + childNode.weight)
                {
                    distance[childNode.vertex] = distance[currNode.vertex] + childNode.weight;
                    path[childNode.vertex] = currNode.vertex;

                    queue.add(new Node(childNode.vertex, distance[childNode.vertex]));
                }
            }
        }

        if(path[dest] == -1)
            return -1;

        shortestPaths[Algorithm.DIJKSTRA.getIndex()] = path;


        return distance[dest] + 1; //distance until dest and plus weight of dest so 1
    }

    /**
     * implementation of the Breadth-First Search
     * @param start start coordinates
     * @param end end coordinates
     * @return distance from start to end
     */
    public int findPathByBFS(int[] start, int[] end)
    {
        int src;
        int dest;
        try
        {
            //if given start and end coordinates doesn't convert to nodes, it throws NullPointerException, this mean is given coordinates are invalid
            src = coordinateToNode.get(start[0]+"-"+start[1]);
            dest = coordinateToNode.get(end[0]+"-"+end[1]);
        } catch (NullPointerException e)
        {
            return -2;
        }

        /**
         * hold distance from start to currentNode (every index is a node, distance[2] hold from start to node2 distance)
         */
        int[] distance = new int[nodeSize];

        /**
         * each index represents a node and it is kept whether the node has been visited or not.
         */
        boolean[] visited = new boolean[nodeSize];

        /**
         * each index corresponds to a node, and the values at each index indicate the source node from which it is reached.
         */
        int[] path = new int[nodeSize];
        //If path[dest] is still -1 at the end of the program, it means that the target was not reached.
        path[dest] = -1;

        visited[src] = true;
        distance[src] = 0;

        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        /**
         * Breadth-First Search algorithm
         */
        while(!q.isEmpty())
        {
            Integer currNode = q.poll();
            for(int i=0; i<graph.get(currNode).size(); i++)
            {
                if(!visited[graph.get(currNode).get(i).vertex])
                {
                    visited[graph.get(currNode).get(i).vertex] = true;
                    distance[graph.get(currNode).get(i).vertex] = distance[currNode]+1;
                    path[graph.get(currNode).get(i).vertex] = currNode;

                    q.add(graph.get(currNode).get(i).vertex);

                    if(graph.get(currNode).get(i).vertex == dest)
                    {
                        q.clear();
                        break;
                    }

                }
            }
        }

        if(path[dest] == -1)
            return -1;

        shortestPaths[1] = path;

        return distance[dest] + 1; //distance until dest and plus weight of dest so 1

    }

    /**
     * it draws path in map with elements of given path
     * @param start start coordinates
     * @param end end coordinates
     * @param type type of path algorithm
     * @param color color of path
     */
    public void drawPath(int[] start, int[] end, Color color, Algorithm type)
    {
        int src;
        int dest;
        try
        {
            //if given start and end coordinates doesn't convert to nodes, it throws NullPointerException, this mean is given coordinates are invalid
            src = coordinateToNode.get(start[0]+"-"+start[1]);
            dest = coordinateToNode.get(end[0]+"-"+end[1]);
        } catch (NullPointerException e)
        {
            return;
        }

        int[] path = new int[]{};
        if(type == Algorithm.BFS)
            path = shortestPaths[Algorithm.BFS.getIndex()];
        else if(type == Algorithm.DIJKSTRA)
            path = shortestPaths[Algorithm.DIJKSTRA.getIndex()];

        String[] coordinate = nodeToCoordinate.get(dest).split("-");
        map.getMap()[Integer.parseInt(coordinate[0])][Integer.parseInt(coordinate[1])] = color.getColor();

        int curr;
        while(dest != src)
        {
            curr = path[dest];
            coordinate = nodeToCoordinate.get(curr).split("-");
            map.getMap()[Integer.parseInt(coordinate[0])][Integer.parseInt(coordinate[1])] = color.getColor();
            dest = curr;
        }

    }

    public void writeCoordinates(int[] start, int[] end, Algorithm type)
    {

        int src;
        int dest;
        try
        {
            //if given start and end coordinates doesn't convert to nodes, it throws NullPointerException, this mean is given coordinates are invalid
            src = coordinateToNode.get(start[0]+"-"+start[1]);
            dest = coordinateToNode.get(end[0]+"-"+end[1]);
        } catch (NullPointerException e)
        {
            return;
        }

        int[] path = new int[]{};
        String algoName = "";
        if(type == Algorithm.BFS)
        {
            path = shortestPaths[Algorithm.BFS.getIndex()];
            algoName = "BFS";
        }
        else if(type == Algorithm.DIJKSTRA)
        {
            path = shortestPaths[Algorithm.DIJKSTRA.getIndex()];
            algoName = "Dijkstra";
        }

        Path filePath = Paths.get("output");
        try {
            if(!Files.exists(filePath))
                Files.createDirectories(filePath);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path.of("").toAbsolutePath() + "\\output\\" + map.getFileName().split("\\.")[0]+"_ShortestPath_"+algoName+"_Coordinates.txt")))
        {
            writer.write("[");
            writer.write("["+nodeToCoordinate.get(dest)+"]");

            int curr;
            while(dest != src)
            {
                curr = path[dest];
                writer.write(",["+nodeToCoordinate.get(curr)+"]");
                dest = curr;
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

    }

    /**
     * save map as png
     */
    public void saveAsPng()
    {
        BufferedImage image = new BufferedImage(map.getRowSize(), map.getColSize(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < map.getRowSize(); x++) {
            for (int y = 0; y < map.getColSize(); y++)
            {
                Color color;
                if(map.getMap()[x][y] == 0)
                    color = Color.BLACK;
                else if(map.getMap()[x][y] == Color.WHITE.getColor())
                    color = Color.WHITE;
                else if(map.getMap()[x][y] == Color.RED.getColor())
                    color = Color.RED;
                else
                    color = Color.BLUE;

                image.setRGB(x, y, color.getColor());
            }
        }

        //File ImageFile = new File(map.getFileName().split("\\.")[0]+".png");
        File ImageFile = new File( Path.of("").toAbsolutePath() + "\\output\\" + map.getFileName().split("\\.")[0]+".png");
        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
