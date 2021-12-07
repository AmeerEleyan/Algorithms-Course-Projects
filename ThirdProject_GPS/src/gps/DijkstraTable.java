/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 7/12/2021    2:11 AM
 */
package gps;

public class DijkstraTable {

    private final Vertex cityVertex;
    private boolean known;
    private float distance = Integer.MAX_VALUE;
    private String path;

    public DijkstraTable(Vertex cityVertex) {
        this.cityVertex = cityVertex;
    }

    public Vertex getCityVertex() {
        return cityVertex;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
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
                "cityVertexName: " + cityVertex.getCity().getCityName() +
                ", known: " + known +
                ", distance: " + distance +
                ", path: " + path +
                "}\n";
    }
}
