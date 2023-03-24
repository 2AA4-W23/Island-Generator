package ca.mcmaster.cas.se2aa4.island.Humidity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RiverThicknessExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.SaturationExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.SoilProfile;

public class TileHumidifier {


    private static HumidityExtractor humidEx = new HumidityExtractor();
    private static TileTagExtractor tagEx = new TileTagExtractor();
    private static Random rng = RandomNumber.getRandomInstance();
    private static EdgeTagExtractor edgeEx = new EdgeTagExtractor();
    private static RiverThicknessExtractor thickEx = new RiverThicknessExtractor();
    private static SaturationExtractor satEx = new SaturationExtractor();

    public static List<Structs.Polygon> setHumidities (List<Structs.Polygon> tiles, List<Structs.Segment> segments, SoilProfile sp) {
        Queue<Structs.Polygon> tileQ = new LinkedList<>();
        int index = 0;
        for(Structs.Polygon p : tiles){ 
            //set humidity values to high for lakes and aquifers
            //add lake and aquifer tiles to queue as starting points
            String tag = tagEx.extractValues(p.getPropertiesList());
            if(tag.equals("lake") || tag.equals("endor_lake")){
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(rng.nextInt(90,100))).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            } else if(tag.equals("aquifer")){
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(rng.nextInt(80,90))).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            } else {
                int numRivers = 0;
                int totalThickness = 0;
                for(Integer i : p.getSegmentIdxsList()){
                    Structs.Segment s = segments.get(i);
                    String edgetag = edgeEx.extractValues(s.getPropertiesList());
                    String thick = thickEx.extractValues(s.getPropertiesList());
                    //System.out.println(tag);
                    if(edgetag.equals("river")) {
                        numRivers++;
                        totalThickness += Integer.parseInt(thick);
                    }

                }
                if(numRivers == 0) {
                    index++;
                    continue;
                }
                int percentRivers = numRivers / p.getSegmentIdxsCount();
                double multiplier = (double) totalThickness / 2.5 / (double) numRivers;
                int humidVal = (int) (rng.nextInt(Math.max(percentRivers - 20, 20), Math.max(percentRivers - 10, 25)) * multiplier);
                Structs.Polygon humidP = PropertyAdder.addProperty(p, "humidity", humidVal + "");
                tileQ.add(humidP);
                tiles.set(index, humidP);
            
            }
            index++;
        }
        //breadth first search of neighboring tiles of lakes/aquifers
        while(!tileQ.isEmpty()){ 
            Structs.Polygon currentTile = tileQ.remove();
            int parentHumidity = Integer.parseInt(humidEx.extractValues(currentTile.getPropertiesList()));
            for(int i : currentTile.getNeighborIdxsList()){
                Structs.Polygon neighborTile = tiles.get(i);
                int absorbed = Math.min(sp.getAbsorptionLevel(), sp.getMaxSaturation());
                int humidityVal = Math.max(parentHumidity - absorbed, 0);
                if(hasHumidity(neighborTile)) {
                    int currentHumidity = Integer.parseInt(humidEx.extractValues(neighborTile.getPropertiesList()));
                    if(currentHumidity >= humidityVal) continue;
                }
                Structs.Polygon humidP = PropertyAdder.addProperty(neighborTile, "humidity", humidityVal + "");
                humidP = PropertyAdder.addProperty(humidP, "saturation", absorbed + "");
                tileQ.add(humidP);
                tiles.set(i, humidP);
            }
        }
        return tiles;
    }

    private static boolean hasHumidity(Structs.Polygon tile){
        return !humidEx.extractValues(tile.getPropertiesList()).equals("null");
    }
}
