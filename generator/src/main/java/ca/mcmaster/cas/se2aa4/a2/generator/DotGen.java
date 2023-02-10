package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for (int x = 0; x <= width; x += square_size) {
            for (int y = 0; y <= height; y += square_size) {
                double xVal = (double) x;
                double yVal = (double) y;
                xVal = Math.round(xVal * 100.0) / 100.0;
                yVal = Math.round(yVal * 100.0) / 100.0;
                System.out.println("Xval: " + xVal);
                vertices.add(Vertex.newBuilder().setX(xVal).setY(yVal).build());
                // vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double)
                // y).build());
                // vertices.add(Vertex.newBuilder().setX((double) x).setY((double)
                // y+square_size).build());
                // vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double)
                // y+square_size).build());
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for (Vertex v : vertices) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }
        Set<Segment> segments = new HashSet<>();

        for (int i = 0; i < verticesWithColors.size() - 1; i++) {
            if (((i + 1) % (width / square_size + 1)) != 0 || i == 0) { // horizontal segments
                Segment test = Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).build();
                Vertex v1 = verticesWithColors.get(test.getV1Idx());
                Vertex v2 = verticesWithColors.get(test.getV2Idx());
                String color1 = extractColorAverage(v1.getPropertiesList(), v2.getPropertiesList());
                Property color = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                Segment coloredSegment = Segment.newBuilder(test).addProperties(color).build();
                segments.add(coloredSegment);
            }
            if (i + (width / square_size + 1) < verticesWithColors.size()) { // vertical segements
                Segment test = Segment.newBuilder().setV1Idx(i).setV2Idx(i + (width / square_size + 1)).build();
                Vertex v1 = verticesWithColors.get(test.getV1Idx());
                Vertex v2 = verticesWithColors.get(test.getV2Idx());
                String color1 = extractColorAverage(v1.getPropertiesList(), v2.getPropertiesList());
                Property color = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                Segment coloredSegment = Segment.newBuilder(test).addProperties(color).build();
                segments.add(coloredSegment);
            }
        }

        // for (int i = 0; i < 10; i++) {
        // System.out.println(verticesWithColors.get(i));
        // }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

    private String extractColorAverage(List<Property> properties1, List<Property> properties2) {
        String val1 = null;
        String val2 = null;
        for (Property p : properties1) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }
        for (Property p : properties2) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val2 = p.getValue();
            }
        }
        if (val1 == null)
            val1 = "0,0,0";
        if (val2 == null)
            val2 = "0,0,0";
        String[] raw1 = val1.split(",");
        String[] raw2 = val2.split(",");
        int red = (Integer.parseInt(raw1[0]) + Integer.parseInt(raw2[0])) / 2;
        int green = (Integer.parseInt(raw1[1]) + Integer.parseInt(raw2[1])) / 2;
        int blue = (Integer.parseInt(raw1[2]) + Integer.parseInt(raw2[2])) / 2;
        String color = red + "," + green + "," + blue;
        return color;
    }
}
