package gps;

public class Test {
    public static void main(String[] args) {

        City c1= new City("Ashdod",34.6553f,31.8044f, (short) 251, (short) 347);
        City c2= new City("Nablus",35.2621f,32.2227f, (short) 251, (short) 347);
        City c3= new City("Jericho",35.4618f,31.8611f, (short) 251, (short) 347);
        City c4= new City("Lod",34.8881f,31.9510f, (short) 251, (short) 347);
        City c5= new City("Ramla",34.8729f,31.9316f, (short) 251, (short) 347);
        City c6= new City("Beitunia",35.1698f,31.8893f, (short) 251, (short) 347);
        City c7= new City("Birzeit",35.1960f,31.9753f, (short) 251, (short) 347);
        City c8= new City("xx",35.1960f,31.9753f, (short) 251, (short) 347);

        Graph gps  = new Graph(8);
        gps.addNewCities(c1);
        gps.addNewCities(c2);
        gps.addNewCities(c3);
        gps.addNewCities(c4);
        gps.addNewCities(c5);
        gps.addNewCities(c6);
        gps.addNewCities(c7);
        gps.addNewCities(c8);

        gps.addAdjacent("Birzeit","Beitunia");
        gps.addAdjacent("Lod","Beitunia");
        gps.addAdjacent("Lod","Ramla");
        gps.addAdjacent("Jericho","Beitunia");
        gps.addAdjacent("Ramla","Ashdod");
        gps.addAdjacent("Ashdod","Lod");
        gps.addAdjacent("Ramla","Nablus");

       ShortestPath s = gps.findShortestPath("Birzeit","Jericho");


    }
}
