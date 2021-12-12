/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 7/12/2021    2:11 AM
 */
package gps;

public class DijkstraTable implements Comparable<DijkstraTable> {

    private final String cityVertex;
    private boolean known;
    private float distance = Integer.MAX_VALUE;
    private String path;

    public DijkstraTable(String cityVertex, boolean known, float distance, String path) {
        this.cityVertex = cityVertex;
        this.known = known;
        this.distance = distance;
        this.path = path;
    }

    public DijkstraTable(String cityVertex) {
        this.cityVertex = cityVertex;
    }


    public String getCityVertex() {
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
                "cityVertexName: " + cityVertex +
                ", known: " + known +
                ", distance: " + distance +
                ", path: " + path +
                "}\n";
    }

    @Override
    public int compareTo(DijkstraTable o) {
        return Float.compare(this.distance, o.distance);
    }
}
