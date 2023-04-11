package ca.mcmaster.cas.se2aa4.island.CityGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.CityGen.MarkovNameGen.NameGenerator;
import ca.mcmaster.cas.se2aa4.island.CityGen.MarkovNameGen.Trainer;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.CentroidNode;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RiverThicknessExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.SaturationExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class CityAdder {

    private static TileTagExtractor tagEx = new TileTagExtractor();
    private static HumidityExtractor humidEx = new HumidityExtractor();
    private static SaturationExtractor satEx = new SaturationExtractor();
    private static RiverThicknessExtractor thickEx = new RiverThicknessExtractor();
    private static AltitudeExtractor altEx = new AltitudeExtractor();
    private static Random rng = RandomNumber.getRandomInstance();

    public static List<Vertex> addCities(List<Polygon> tiles, List<Vertex> vertices, int numCities){
        for(int i = 0; i < numCities; i++){
            Polygon cityTile;
            do { 
                cityTile = tiles.get(rng.nextInt(tiles.size()));
            } while(!checkEligible(cityTile)); //pick random tile that is not ocean or lake
            Vertex centroid = vertices.get(cityTile.getCentroidIdx());
            centroid = PropertyAdder.addProperty(centroid, "city_name", NameGenerator.generateName(Trainer.getData()));
            centroid = PropertyAdder.addProperty(centroid, "rgb_color", "255,0,0");
            centroid = PropertyAdder.addProperty(centroid, "thickness", rng.nextInt(3, 7) + "");
            //add properties for visualizing
            vertices.set(cityTile.getCentroidIdx(), centroid);
            //replace existing vertex with new one
        }
        return vertices;
    }

    public static List<CentroidNode> getCityNodes(List<CentroidNode> nodes){
        List<CentroidNode> cities = new ArrayList<>();
        for(CentroidNode node : nodes) { //find and return all nodes with a city tag
            if(node.getValue("city_name").equals("null")) continue;
            cities.add(node);
        }
        return cities;
    }

    public static CentroidNode findCentralCity(List<CentroidNode> cities){
        CentroidNode hub = cities.get(0);
        int maxScore = 0;
        for(CentroidNode city : cities){
            int score = 0;
            int humid = 0;
            int saturation = 0;
            int thickness = 0;
            int alt = 0;
            //scoring criteria
            Polygon tile = city.getTile();
            Vertex centroid = city.getVertex();

            int centrality = (int) Math.abs(centroid.getX() - 250) + (int) Math.abs(centroid.getY() - 250); 
            //compute centrality score

            try{
                humid = Integer.parseInt(humidEx.extractValues(tile.getPropertiesList()));
                saturation = Integer.parseInt(satEx.extractValues(tile.getPropertiesList()));
                thickness = Integer.parseInt(thickEx.extractValues(centroid.getPropertiesList()));
                alt = Integer.parseInt(altEx.extractValues(tile.getPropertiesList()));
                //extract values
            }  catch (Exception e ) {}

            score += humid * 30 + saturation * 30 + thickness * 70 - centrality - alt * 2;
            //compute weighted score based on attributes

            if(score > maxScore) { //replace current hub with new city if it has a higher score
                hub = city;
                maxScore = score;
            }
        }
        return hub;
    }

    //ensures cities dont spawn on ocean or lakes
    private static boolean checkEligible(Polygon tile){
        if(tile == null) return false;
        String tag = tagEx.extractValues(tile.getPropertiesList());
        return tag.equals("land") || tag.equals("aquifer");
    }

}
