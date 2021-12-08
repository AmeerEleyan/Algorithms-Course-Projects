package gps;

public class TestMain {
    public static void main(String[] args) {
        City[] cities = new City[7];

        cities[0] = new City("City1", 1000, 2400);
        cities[1] = new City("City2", 2800, 3000);
        cities[2] = new City("City3", 2400, 2500);
        cities[3] = new City("City4", 4000, 0);
        cities[4] = new City("City5", 4500, 3800);
        cities[5] = new City("City6", 6000, 1500);
       cities[6] = new City("City7", 4800, 1000);

        Graph graph = new Graph(cities);

        graph.addAdjacent("City1", "City2");
        graph.addAdjacent("City1", "City4");
        graph.addAdjacent("City2", "City3");
        graph.addAdjacent("City2", "City5");
        graph.addAdjacent("City3", "City5");
        graph.addAdjacent("City3", "City4");
        graph.addAdjacent("City3", "City6");
        graph.addAdjacent("City4", "City6");
        graph.addAdjacent("City5", "City6");
        graph.addAdjacent("City6", "City7");
        graph.addAdjacent("City7", "City1");
     //   graph.findShortestPath("City1","City2");


        Vertex v = new Vertex(new City("Rammallah",35.2034f,31.9038f));
        v.addAdjacent(new City("Betlahem",35.0998f, 31.5326f));
        System.out.println(v.getAdjacent());

    }
}
