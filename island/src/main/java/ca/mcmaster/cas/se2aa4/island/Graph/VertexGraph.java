package ca.mcmaster.cas.se2aa4.island.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class VertexGraph extends Graph {

    private Map<String, Structs.Segment> vertexToSegmentMap = new HashMap<>();

    public VertexGraph(List<Structs .Segment> segments, List<Structs.Vertex> vertices){
        size = vertices.size();
        adjacencyList = new HashSet[size];
        for(Structs.Segment s : segments){
            if(adjacencyList[s.getV1Idx()] == null) adjacencyList[s.getV1Idx()] = new HashSet<>();
            if(adjacencyList[s.getV2Idx()] == null) adjacencyList[s.getV2Idx()] = new HashSet<>();
            adjacencyList[s.getV1Idx()].add(s.getV2Idx());
            adjacencyList[s.getV2Idx()].add(s.getV1Idx());
            vertexToSegmentMap.put(s.getV1Idx() + " " + s.getV2Idx(), s);
        }
    }

    public void refreshSegments(List<Structs.Segment> segments){
        for(Structs.Segment s : segments) {
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            vertexToSegmentMap.replace(v1 + " " + v2, s);
        }
    }

    public Structs.Segment getSegment(int v1, int v2){
        if(!isConnected(v1, v2)) return null;
        return vertexToSegmentMap.get(v1 + " " + v2);
    }   

    public Structs.Segment getSegment(Structs.Vertex v1, Structs.Vertex v2, List<Structs.Vertex> vertices){
        return getSegment(vertices.indexOf(v1), vertices.indexOf(v2));
    }   

    public boolean isConnected(Structs.Vertex v1, Structs.Vertex v2, List<Structs.Vertex> vertices){
        return isConnected(vertices.indexOf(v1), vertices.indexOf(v2));
    }

    public Set<Integer> getConnections(Structs.Vertex v, List<Structs.Vertex> vertices){
        return getConnections(vertices.indexOf(v));
    }
        
}
