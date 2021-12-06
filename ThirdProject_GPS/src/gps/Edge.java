/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    8:17 PM
 */
package gps;

public class Edge {

    private City sourceGraph;
    private City destinationGraph;
    private float distance;

    // Use it when initialize the graph structure
    public Edge(City sourceGraph) {
        this.sourceGraph = sourceGraph;
        this.destinationGraph = null;
        this.distance = 0;
    }


    // Use it when start adding adjacent
    public Edge(City sourceGraph, City destinationGraph) {
        this.sourceGraph = sourceGraph;
        this.destinationGraph = destinationGraph;
        this.distance = getDistance(sourceGraph, destinationGraph);
    }

    public City getSourceVertex() {
        return sourceGraph;
    }

    public void setSourceVertex(City sourceGraph) {
        this.sourceGraph = sourceGraph;
    }

    public City getDestinationVertex() {
        return destinationGraph;
    }

    public void setDestinationVertex(City destinationGraph) {
        this.destinationGraph = destinationGraph;
    }

    private float getDistance(City sourceGraph, City destinationGraph) {
        float differenceX = destinationGraph.getLayoutX() - sourceGraph.getLayoutX();
        float distanceY = destinationGraph.getLayoutY() - sourceGraph.getLayoutY();
        return (float) Math.sqrt(Math.pow(differenceX, 2) + Math.pow(distanceY, 2));
    }

    public City getSourceGraph() {
        return sourceGraph;
    }

    public void setSourceGraph(City sourceGraph) {
        this.sourceGraph = sourceGraph;
    }

    public City getDestinationGraph() {
        return destinationGraph;
    }

    public void setDestinationGraph(City destinationGraph) {
        this.destinationGraph = destinationGraph;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "sourceGraph=" + sourceGraph +
                ", destinationGraph=" + destinationGraph +
                ", distance=" + distance +
                '}';
    }
}
