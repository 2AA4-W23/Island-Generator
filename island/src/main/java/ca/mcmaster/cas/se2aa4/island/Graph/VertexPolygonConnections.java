package ca.mcmaster.cas.se2aa4.island.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

public class VertexPolygonConnections extends Graph {

    private static Extractor tileTagEx = new TileTagExtractor();

    public VertexPolygonConnections(List<Structs.Polygon> polygons, List<Structs.Segment> segments, List<Structs.Vertex> vertices){
        size = vertices.size();
        adjacencyList = new HashSet[size];
        for(Structs.Polygon p : polygons){
            if(isNonAdjacentWaterTile(p, polygons)) continue;
            for(Integer i : p.getSegmentIdxsList()){
                Structs.Segment s = segments.get(i);    
                if(adjacencyList[s.getV1Idx()] == null) adjacencyList[s.getV1Idx()] = new HashSet<>();
                if(adjacencyList[s.getV2Idx()] == null) adjacencyList[s.getV2Idx()] = new HashSet<>();
                adjacencyList[s.getV1Idx()].add(polygons.indexOf(p));
                adjacencyList[s.getV2Idx()].add(polygons.indexOf(p));
            }
        }
    }

    public boolean isNonAdjacentWaterTile(Structs.Polygon tile, List<Structs.Polygon> polygons){
        String tag = tileTagEx.extractValues(tile.getPropertiesList());
        if(!tag.equals("ocean")) return false;
        for(Integer i : tile.getNeighborIdxsList()){
            tag = tileTagEx.extractValues(polygons.get(i).getPropertiesList());
            if(!tag.equals("ocean")) return false;
        }
        return true;
    }

    public boolean isConnected(Structs.Vertex v, Structs.Polygon p, List<Structs.Vertex> vertices, List<Structs.Polygon> polygons){
        return isConnected(vertices.indexOf(v), polygons.indexOf(p));
    }

    public Set<Integer> getConnections(Structs.Vertex v, List<Structs.Vertex> vertices){
        return getConnections(vertices.indexOf(v));
    }
}
