package ca.mcmaster.cas.se2aa4.island.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class VertexPolygonConnections extends Graph {

    public VertexPolygonConnections(List<Structs.Polygon> polygons, List<Structs.Segment> segments, List<Structs.Vertex> vertices){
        size = vertices.size();
        adjacencyList = new HashSet[size];
        for(Structs.Polygon p : polygons){
            for(Integer i : p.getSegmentIdxsList()){
                Structs.Segment s = segments.get(i);
                adjacencyList[s.getV1Idx()].add(polygons.indexOf(p));
                adjacencyList[s.getV2Idx()].add(polygons.indexOf(p));
            }
        }
    }

    public boolean isConnected(Structs.Vertex v, Structs.Polygon p, List<Structs.Vertex> vertices, List<Structs.Polygon> polygons){
        return isConnected(vertices.indexOf(v), polygons.indexOf(p));
    }

    public Set<Integer> getConnections(Structs.Vertex v, List<Structs.Vertex> vertices){
        return getConnections(vertices.indexOf(v));
    }
}
