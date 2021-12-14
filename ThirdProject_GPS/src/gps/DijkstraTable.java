/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 7/12/2021    2:11 AM
 */
package gps;

public class DijkstraTable {

    private final String cityVertex;
    private boolean visited;
    private float distance = Integer.MAX_VALUE;
    private String path;

    public DijkstraTable(String cityVertex) {
        this.cityVertex = cityVertex;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "DijkstraTable{" +
                "cityVertexName: " + cityVertex +
                ", known: " + visited +
                ", distance: " + distance +
                ", path: " + path +
                "}\n";
    }

}
