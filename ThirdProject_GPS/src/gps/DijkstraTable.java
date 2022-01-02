/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 7/12/2021    2:11 AM
 */
package gps;

public class DijkstraTable {

    private boolean visited;
    private float distance = Integer.MAX_VALUE;
    private String path;

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

    public void reSetValue(){
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.path = null;
    }

    @Override
    public String toString() {
        return "DijkstraTable{" +
                ", known: " + visited +
                ", distance: " + distance +
                ", path: " + path +
                "}\n";
    }

}
