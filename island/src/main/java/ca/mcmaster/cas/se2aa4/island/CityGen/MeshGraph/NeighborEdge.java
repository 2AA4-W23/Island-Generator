package ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph;

import java.util.ArrayList;
import java.util.List;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Edge;

public class NeighborEdge extends Edge<CentroidNode>{

    public NeighborEdge(CentroidNode v1, CentroidNode v2) {
        super(v1, v2);
    }

    //construct a list of edges from centroidnode properties
    public static List<NeighborEdge> getEdgesFromNodes(List<CentroidNode> nodes){
        List<NeighborEdge> edges = new ArrayList<>();
        for(CentroidNode node : nodes) {
            for(int i : node.getTile().getNeighborIdxsList()){ //check all neighbors of associated tiles of node
                CentroidNode neighbor = CentroidNode.getNodeById(nodes, i);
                if(neighbor == null) continue;
                edges.add(new NeighborEdge(node, neighbor));
            }
        }
        return edges;
    }
    
}
