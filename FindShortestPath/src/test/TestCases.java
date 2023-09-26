package test;

import color.Color;
import datastructures.MyGraph;
import datastructures.MyMap;

/**
 * it is template of test cases
 */
public class TestCases implements Runnable {

    private  String FileName;
    private int X_SIZE;
    private int Y_SIZE;

    public TestCases(String FileName, int X_SIZE, int Y_SIZE) {
        this.FileName = FileName;
        this.X_SIZE = X_SIZE;
    	this.Y_SIZE = Y_SIZE;
    }


    /**
     * test template
     */
    public void test(){
    	
    	System.out.println("\n\n*******************\nMap is " + this.FileName + " with X_SIZE " + this.X_SIZE + " and Y_SIZE " + this.Y_SIZE + "\n********************\n");

        MyMap map = new MyMap("mapfiles\\"+this.FileName);
        MyGraph graph = new MyGraph(map);

        long startTimeBFS = System.currentTimeMillis();
            int bfsDistance = graph.findPathByBFS(map.getStartPoint(),map.getEndPoint());
        long endTimeBFS = System.currentTimeMillis();
        long  elapsedTimeBFS = endTimeBFS - startTimeBFS;

        long startTimeDijkstra = System.currentTimeMillis();
            int dijkstraDistance = graph.findPathByDijkstra(map.getStartPoint(),map.getEndPoint());
        long endTimeDijkstra = System.currentTimeMillis();
        long  elapsedTimeDijkstra = endTimeDijkstra - startTimeDijkstra;

        graph.drawPath(map.getStartPoint(), map.getEndPoint(), Color.WHITE, MyGraph.Algorithm.BFS);
        graph.writeCoordinates(map.getStartPoint(), map.getEndPoint(), MyGraph.Algorithm.BFS);

        graph.drawPath(map.getStartPoint(), map.getEndPoint(), Color.RED, MyGraph.Algorithm.DIJKSTRA);
        graph.writeCoordinates(map.getStartPoint(), map.getEndPoint(), MyGraph.Algorithm.DIJKSTRA);


        System.out.print(this.FileName + " | ");
        System.out.print("bfs = ");
        if (bfsDistance >= 0) {
            System.out.print(bfsDistance+" Nodes, elapsed time is " + elapsedTimeBFS + " milliseconds");
        } else {
            if (bfsDistance == -2) {
                System.out.print("There is no node in start or end point ");
            } else {
                System.out.print("There is no any path ");
            }
        }

        System.out.print(" | dijkstra = ");
        if (dijkstraDistance >= 0) {
            System.out.print(dijkstraDistance+" Nodes, elapsed time is " + elapsedTimeDijkstra + " milliseconds");
        } else {
            if (dijkstraDistance == -2) {
                System.out.print("There is no node in start or end point ");
            } else {
                System.out.print("There is no any path ");
            }
        }
        System.out.println();

        if(bfsDistance >= 0 || dijkstraDistance >= 0)
            graph.saveAsPng();
    }

    /**
     * it runs test
     */
    @Override
    public void run() {
        test();
    }
}

