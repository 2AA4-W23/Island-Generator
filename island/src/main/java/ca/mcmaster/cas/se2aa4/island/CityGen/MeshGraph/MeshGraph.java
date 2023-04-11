
package ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Graph;

public class MeshGraph extends Graph<CentroidNode, NeighborEdge>{

    //construct a graph from a list of nodes
    public MeshGraph(List<CentroidNode> nodes){ 
        super(nodes);
        this.edges = NeighborEdge.getEdgesFromNodes(this.nodes);
        updateAdjacencyList(edges);
    }

    //construct a graph from a list of polygons and vertices    
    public MeshGraph(List<Polygon> tiles, List<Vertex> vertices){
        this(CentroidNode.getNodes(tiles, vertices));
    }
}
