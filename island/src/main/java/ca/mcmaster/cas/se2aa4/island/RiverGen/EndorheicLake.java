package ca.mcmaster.cas.se2aa4.island.RiverGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;
import ca.mcmaster.cas.se2aa4.island.LakeGen.WaterBody;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class EndorheicLake extends WaterBody{

    private static EdgeTagExtractor edgeTagEx = new EdgeTagExtractor();
    private static TileTagExtractor tileTagEx = new TileTagExtractor();
    private static Random rng = RandomNumber.getRandomInstance();

    private static boolean checkEligibleLakeTile(Structs.Polygon tile, List<Structs.Polygon> tiles, List<Structs.Segment> segments, List<Structs.Vertex> vertices, VertexPolygonConnections G) {
        if(tile == null) return false;
        for(int idx : tile.getNeighborIdxsList()){
            String tag = tileTagEx.extractValues(tiles.get(idx).getPropertiesList());
            if(!tag.equals("land")) return false;
            for(Integer i : tile.getSegmentIdxsList()){
                Structs.Segment s = segments.get(i);
                String segTag = edgeTagEx.extractValues(s.getPropertiesList());
                if(segTag.equals("river")) return false;
                Structs.Vertex v1 = vertices.get(s.getV1Idx());
                Structs.Vertex v2 = vertices.get(s.getV2Idx());
                if(AddRivers.isWaterVertex(v1, vertices, G, tiles)) return false;
                if(AddRivers.isWaterVertex(v2, vertices, G, tiles)) return false;
            }
        }
        return true;
    }

    public static boolean addEndorheicLake(List<Structs.Polygon> tiles, List<Structs.Vertex> vertices, List<Structs.Segment> segments, Structs.Vertex initialPoint, VertexPolygonConnections G, int num){
        if(initialPoint == null) return false;
        Structs.Polygon initialTile = getInitialLakeTile(tiles, vertices, segments, initialPoint, G);
        if(initialTile == null) return false;
        List<Structs.Polygon> tilesInLake = new ArrayList<>();
        tilesInLake.add(initialTile);
        int lakeSize = rng.nextInt(2,5);
        int checks = 0;
        while(tilesInLake.size() < lakeSize && checks < 100){
            Structs.Polygon nextTile = tilesInLake.get(rng.nextInt(tilesInLake.size()));
            Structs.Polygon tileToAdd = tiles.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
            checks++;
            if(!checkEligibleLakeTile(tileToAdd, tiles, segments, vertices, G)) continue;
            tilesInLake.add(tileToAdd);
        }
        for(Structs.Polygon p : tilesInLake){
            Structs.Polygon pLake = PropertyAdder.addProperty(p, "rgb_color", "10,100,255");
            pLake = PropertyAdder.addProperty(pLake, "tile_tag", "endor_lake");
            pLake = PropertyAdder.addProperty(pLake, "endor_lake_num", num + "");
            int idx = tiles.indexOf(p);
            if(idx != -1) tiles.set(idx, pLake);
            else return false;
        } 
        return true;
    }
    
    static Structs.Polygon getInitialLakeTile(List<Structs.Polygon> tiles, List<Structs.Vertex> vertices, List<Structs.Segment> segments, Structs.Vertex initialPoint, VertexPolygonConnections G){
        Structs.Polygon initialTile = null;
        Set<Integer> connections = G.getConnections(initialPoint, vertices);
        if(connections == null || connections.size() == 0) return null;
        for(Integer i : connections) {
            initialTile = tiles.get(i);
            if(!checkEligibleLakeTile(initialTile, tiles, segments, vertices, G)) initialTile = null;
            else break;
        }
        if(initialTile == null) return null;
        if(G.isNonAdjacentWaterTile(initialTile, tiles)) return null;
        return initialTile;
    }
}
