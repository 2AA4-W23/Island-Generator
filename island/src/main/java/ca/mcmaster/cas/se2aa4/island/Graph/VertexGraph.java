package ca.mcmaster.cas.se2aa4.island.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class VertexGraph extends Graph {

    public VertexGraph(List<Structs.Segment> segments, List<Structs.Vertex> vertices){
        size = vertices.size();
        adjacencyList = new HashSet[size];
        for(Structs.Segment s : segments){
            adjacencyList[s.getV1Idx()].add(s.getV2Idx());
            adjacencyList[s.getV2Idx()].add(s.getV1Idx());
        }
    }

    public boolean isConnected(Structs.Vertex v1, Structs.Vertex v2, List<Structs.Vertex> vertices){
        return isConnected(vertices.indexOf(v1), vertices.indexOf(v2));
    }

    public Set<Integer> getConnections(Structs.Vertex v, List<Structs.Vertex> vertices){
        return getConnections(vertices.indexOf(v));
    }
        
}
