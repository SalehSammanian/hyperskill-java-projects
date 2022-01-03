package maze;

import java.util.Set;
import java.util.TreeSet;

public class Graph {

    private final int nodes;

    private final double[][] adjMatrix;

    public Graph(int nodes) {
        this.nodes = nodes;
        adjMatrix = new double[nodes][nodes];
    }

    public void setEdgeWeight(int node1, int node2, double weight) {
        adjMatrix[node1][node2] = weight;
        adjMatrix[node2][node1] = weight;
    }

    public double getEdgeWeight(int node1, int node2) {
        return adjMatrix[node1][node2];
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public Graph getMinimalSpanningTree() {

        Set<Integer> connectedNodes = new TreeSet<>();
        connectedNodes.add(0);

        Graph spanningTree = new Graph(this.nodes);

        while (connectedNodes.size() != this.nodes) {

            double minWeight = Double.POSITIVE_INFINITY;
            int node1 = -1;
            int node2 = -1;

            for (Integer node : connectedNodes) {
                for (int i = 0; i < this.nodes; i++) {
                    if (adjMatrix[node][i] != 0 && !connectedNodes.contains(i) && minWeight > adjMatrix[node][i]) {
                        node1 = node;
                        node2 = i;
                        minWeight = adjMatrix[node][i];
                    }
                }
            }

            connectedNodes.add(node2);

            spanningTree.setEdgeWeight(node1, node2, minWeight);

        }

        return spanningTree;
    }

}
