package ca.mcmaster.cas.se2aa4.island.RiverGen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class VertexGraph {

    private int size;
    private Set<Integer>[] adjacencyList;

    public VertexGraph(List<Structs.Segment> segments, List<Structs.Vertex> vertices){
        size = vertices.size();
        adjacencyList = new HashSet[size];
        for(Structs.Segment s : segments){
            adjacencyList[s.getV1Idx()].add(s.getV2Idx());
            adjacencyList[s.getV2Idx()].add(s.getV1Idx());
        }
    }

    public boolean isConnected(Structs.Vertex v1, Structs.Vertex v2, List<Structs.Vertex> vertices){
        try{
            return adjacencyList[vertices.indexOf(v1)].contains(vertices.indexOf(v2));
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isConnected(int i1, int i2){
        try{
            return adjacencyList[i1].contains(i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public Set<Integer> getConnections(Structs.Vertex v, List<Structs.Vertex> vertices){
        try {
            return adjacencyList[vertices.indexOf(v)];
        } catch (NumberFormatException e) {
            return new HashSet<>();
        }
    }

    public Set<Integer> getConnections(int i){
        try {
            return adjacencyList[i];
        } catch (NumberFormatException e) {
            return new HashSet<>();
        }
    }

    public int size(){
        return this.size;
    }
        
}
