package ca.mcmaster.cas.se2aa4.island.Properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class PropertyAdder {
    public static Structs.Polygon addProperty(Structs.Polygon polygon, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        Structs.Polygon newPolygon = Structs.Polygon.newBuilder(polygon).addProperties(prop).build();
        return newPolygon;
    }
    public static Structs.Segment addProperty(Structs.Segment segment, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        Structs.Segment newSegment = Structs.Segment.newBuilder(segment).addProperties(prop).build();
        return newSegment;
    }
    public static Structs.Vertex addProperty(Structs.Vertex vertex, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        Structs.Vertex newVertex = Structs.Vertex.newBuilder(vertex).addProperties(prop).build();
        return newVertex;
    }
}
