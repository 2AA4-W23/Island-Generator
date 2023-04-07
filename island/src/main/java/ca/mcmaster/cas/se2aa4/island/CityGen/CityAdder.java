package ca.mcmaster.cas.se2aa4.island.CityGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

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
            centroid = PropertyAdder.addProperty(centroid, "thickness", "5");
            vertices.set(cityTile.getCentroidIdx(), centroid);
        }
        return vertices;
    }

    private static boolean checkEligible(Polygon tile){
        if(tile == null) return false;
        String tag = tagEx.extractValues(tile.getPropertiesList());
        return tag.equals("land") || tag.equals("aquifer");
    }
}
