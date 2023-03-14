package ca.mcmaster.Humidity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

public class TileHumidifier {


    private static HumidityExtractor humidEx = new HumidityExtractor();
    private static TileTagExtractor tagEx = new TileTagExtractor();
    private static Random rng = new Random();

    public static List<Structs.Polygon> setHumidities (List<Structs.Polygon> tiles) {
        Queue<Structs.Polygon> tileQ = new LinkedList<>();
        int index = 0;
        for(Structs.Polygon p : tiles){
            if(tagEx.extractValues(p.getPropertiesList()).equals("lake")){
                Structs.Property humidity = Structs.Property.newBuilder().setKey("humidity").setValue(Integer.toString(rng.nextInt(90,100))).build();
                Structs.Polygon humidP = Structs.Polygon.newBuilder(p).addProperties(humidity).build();
                tileQ.add(humidP);
                tiles.set(index, humidP);
            }
            index++;
        }
        while(!tileQ.isEmpty()){
            Structs.Polygon currentTile = tileQ.remove();
            if(!hasHumidity(currentTile)) {
                System.out.println("skipped");
                continue;
            }
            for(int i : currentTile.getNeighborIdxsList()){
                Structs.Polygon neighborTile = tiles.get(i);
                if(hasHumidity(neighborTile)) continue;
                int humidityVal = Math.max(Integer.parseInt(humidEx.extractValues(currentTile.getPropertiesList())) - rng.nextInt(8, 10), 0);
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
