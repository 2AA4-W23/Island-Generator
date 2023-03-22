package ca.mcmaster.cas.se2aa4.island.Properties;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class PropertyAdder {
    public static Structs.Polygon addProperty(Structs.Polygon polygon, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        polygon = Structs.Polygon.newBuilder(polygon).addProperties(prop).build();
        return polygon;
    }
    public static Structs.Segment addProperty(Structs.Segment segment, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        segment = Structs.Segment.newBuilder(segment).addProperties(prop).build();
        return segment;
    }
    public static Structs.Vertex addProperty(Structs.Vertex vertex, String tag, String val) {
        Structs.Property prop = Structs.Property.newBuilder().setKey(tag).setValue(val).build(); 
        vertex = Structs.Vertex.newBuilder(vertex).addProperties(prop).build();
        return vertex;
    }
}
