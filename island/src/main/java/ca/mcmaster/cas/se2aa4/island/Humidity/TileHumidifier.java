package ca.mcmaster.cas.se2aa4.island.Humidity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

public class TileHumidifier {


    private static HumidityExtractor humidEx = new HumidityExtractor();
    private static TileTagExtractor tagEx = new TileTagExtractor();
    private static Random rng = new Random();
    private static EdgeTagExtractor edgeEx = new EdgeTagExtractor();

    public static List<Structs.Polygon> setHumidities (List<Structs.Polygon> tiles, List<Structs.Segment> segments) {
        Queue<Structs.Polygon> tileQ = new LinkedList<>();
        int index = 0;
        for(Structs.Polygon p : tiles){ 
            //set humidity values to high for lakes and aquifers
            //add lake and aquifer tiles to queue as starting points
            if(tagEx.extractValues(p.getPropertiesList()).equals("lake")){
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(rng.nextInt(90,100))).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            } else if(tagEx.extractValues(p.getPropertiesList()).equals("aquifer")){
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(rng.nextInt(80,90))).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            } else {
                int numRivers = 0;
                for(Integer i : p.getSegmentIdxsList()){
                    Structs.Segment s = segments.get(i);
                    String tag = edgeEx.extractValues(s.getPropertiesList());
                    //System.out.println(tag);
                    if(tag.equals("river")) numRivers++;
                }
                if(numRivers == 0) {
                    index++;
                    continue;
                }
                int percentRivers = numRivers / p.getSegmentIdxsCount();
                int humidVal = rng.nextInt(Math.max(percentRivers - 20, 20), Math.max(percentRivers - 10, 30));
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(humidVal + "").build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            
            }
            index++;
        }
        //breadth first search of neighboring tiles of lakes/aquifers
        while(!tileQ.isEmpty()){ 
            Structs.Polygon currentTile = tileQ.remove();
            for(int i : currentTile.getNeighborIdxsList()){
                Structs.Polygon neighborTile = tiles.get(i);
                int humidityVal = Math.max(Integer.parseInt(humidEx.extractValues(currentTile.getPropertiesList())) - rng.nextInt(8, 10), 0);
                if(hasHumidity(neighborTile)) {
                    int currentHumidity = Integer.parseInt(humidEx.extractValues(neighborTile.getPropertiesList()));
                    if(currentHumidity >= humidityVal) continue;
                }
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(humidityVal)).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(neighborTile).addProperties(humidity).build();
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
