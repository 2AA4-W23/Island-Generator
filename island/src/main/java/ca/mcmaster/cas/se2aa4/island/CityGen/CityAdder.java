package ca.mcmaster.cas.se2aa4.island.CityGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.CentroidNode;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Attribute;

public class CityAdder {

    private static TileTagExtractor tagEx = new TileTagExtractor();
    private static Random rng = RandomNumber.getRandomInstance();

    public static List<Vertex> addCities(List<Polygon> tiles, List<Vertex> vertices, int numCities){
        for(int i = 0; i < numCities; i++){
            Polygon cityTile;
            do { 
                cityTile = tiles.get(rng.nextInt(tiles.size()));
            } while(!checkEligible(cityTile));
            Vertex centroid = vertices.get(cityTile.getCentroidIdx());
            centroid = PropertyAdder.addProperty(centroid, "city_name", i + "");
            centroid = PropertyAdder.addProperty(centroid, "rgb_color", "255,0,0");
            centroid = PropertyAdder.addProperty(centroid, "thickness", rng.nextInt(3, 7) + "");
            vertices.set(cityTile.getCentroidIdx(), centroid);
        }
        return vertices;
    }

    public static List<CentroidNode> getCityNodes(List<CentroidNode> nodes){
        List<CentroidNode> cities = new ArrayList<>();
        for(CentroidNode node : nodes) {
            if(node.getValue("city_name").equals("null")) continue;
            cities.add(node);
        }
        return cities;
    }

    public static CentroidNode findCentralCity(List<CentroidNode> cities){
        CentroidNode hub = cities.get(0);
        double minScore = 600;
        for(CentroidNode city : cities){
            if(city == null) System.out.println("null");
            double x = city.getVertex().getX();
            double y = city.getVertex().getY();
            double cityScore = Math.abs(250 - x + 250 - y);
            if(cityScore < minScore) {
                hub = city;
                minScore = cityScore;
            }
        }
        hub.addAttribute(new Attribute("rgb_color", "0,255,0"));
        return hub;
    }

    private static boolean checkEligible(Polygon tile){
        if(tile == null) return false;
        String tag = tagEx.extractValues(tile.getPropertiesList());
        return tag.equals("land") || tag.equals("aquifer");
    }

}
