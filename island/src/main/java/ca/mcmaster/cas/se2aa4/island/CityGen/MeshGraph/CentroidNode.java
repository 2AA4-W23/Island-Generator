package ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Attribute;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;

public class CentroidNode extends Node {

    private final Vertex v;
    private final Polygon tile;
    private static TileTagExtractor tagEx = new TileTagExtractor();

    public CentroidNode(Polygon tile, List<Vertex> vertices, int id) {
        super(id);
        this.tile = tile;
        this.v = vertices.get(tile.getCentroidIdx());
    }

    public static List<CentroidNode> getNodes(List<Polygon> tiles, List<Vertex> vertices){
        List<CentroidNode> nodes = new ArrayList<>();
        for(Polygon tile : tiles) {
            String tag = tagEx.extractValues(tile.getPropertiesList());
            if(tag.equals("ocean") || tag.equals("lake") || tag.equals("endor_lake")) continue;
            CentroidNode node = new CentroidNode(tile, vertices, tile.getCentroidIdx());
            node.addAttributesFromProperties();
            nodes.add(node);
        }
        return nodes;
    }

    public static CentroidNode getNodeById(List<CentroidNode> nodes, int id) {
        for(CentroidNode node : nodes) {
            if(node.id == id) return node;
        }
        return null;
    }

    public void addAttributesFromProperties(){
        List<Property> props = this.getVertex().getPropertiesList();
        for(Property prop : props){
            addAttribute(new Attribute(prop.getKey(), prop.getValue()));
        }
    }

    public Polygon getTile() {
        return tile;
    }

    public Vertex getVertex(){
        return v;
    }
}