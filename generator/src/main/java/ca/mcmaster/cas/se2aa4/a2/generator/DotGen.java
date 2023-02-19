package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;;

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
//                System.out.println("Xval: " + xVal + "Yval: " + yVal);
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
            String thick = Integer.toString(bag.nextInt(2,7));
            String colorCode = red + "," + green + "," + blue;
            String alphaVal = Integer.toString(bag.nextInt(150,255));
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property thickness = Property.newBuilder().setKey("thickness").setValue(thick).build();
            Property alpha = Property.newBuilder().setKey("alpha").setValue(alphaVal).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).addProperties(thickness).addProperties(alpha).build();
            verticesWithColors.add(colored);
        }
        List<Segment> segments = new ArrayList<>();

        for (int i = 0; i < verticesWithColors.size() ; i++) {
            if (((i + 1) % (width / square_size + 1)) != 0 || i == 0) { // vertical segments
                Segment test = Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).build();
                Vertex v1 = verticesWithColors.get(test.getV1Idx());
                Vertex v2 = verticesWithColors.get(test.getV2Idx());
                String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                String alphaVal = Integer.toString(bag.nextInt(0,101));
                Property color = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                Property thickness = Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                Property alpha = Property.newBuilder().setKey("alpha").setValue(alphaVal).build();
                Segment coloredSegment = Segment.newBuilder(test).addProperties(color).addProperties(thickness).addProperties(alpha).build();
                segments.add(coloredSegment);
            }
            if (i + (width / square_size + 1) < verticesWithColors.size()) { // horizontal segements
                Segment test = Segment.newBuilder().setV1Idx(i).setV2Idx(i + (width / square_size + 1)).build();
                Vertex v1 = verticesWithColors.get(test.getV1Idx());
                Vertex v2 = verticesWithColors.get(test.getV2Idx());
                String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                String alphaVal = Integer.toString(bag.nextInt(0,101));
                Property color = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                Property thickness = Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                Property alpha = Property.newBuilder().setKey("alpha").setValue(alphaVal).build();
                Segment coloredSegment = Segment.newBuilder(test).addProperties(color).addProperties(thickness).addProperties(alpha).build();
                segments.add(coloredSegment);
            }
        }

//        for (int i = 0; i < segments.size(); i++) {
//            System.out.println("Index: " + i + " V1: " + segments.get(i).getV1Idx() + " V2: " + segments.get(i).getV2Idx());
//        }

        ArrayList<Polygon> polygons = new ArrayList<>();
        int i = 0;
        while(i < segments.size() - 25){
            ArrayList<Integer> pSegments = new ArrayList<>();
            pSegments.add(i);
            pSegments.add(i + 1);
            pSegments.add(i + 51);
            if((polygons.size()+1)%25==0){
                pSegments.add(i + 2);
            }else {
                pSegments.add(i + 3);
            }

            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Polygon p = Polygon.newBuilder().addAllSegmentIdxs(pSegments).build();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Polygon pColored = Polygon.newBuilder(p).addProperties(color).setCentroidIdx(verticesWithColors.size()).build();



            double x1 = verticesWithColors.get(segments.get(pColored.getSegmentIdxs(0)).getV1Idx()).getX();
            double y1 = verticesWithColors.get(segments.get(pColored.getSegmentIdxs(0)).getV1Idx()).getY();
            x1 += square_size/2;
            y1 += square_size/2;

            Vertex v1 = Vertex.newBuilder().setX(x1).setY(y1).build();
            colorCode = "0,0,0";
            color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex coloredv1 = Vertex.newBuilder(v1).addProperties(color).build();

            polygons.add(pColored);
            verticesWithColors.add(coloredv1);
            if((polygons.size())%25==0){
                i+=3;
            } else {
                i+=2;
            }
        }
        ArrayList<Polygon> polygonsIndexed = new ArrayList<>();

        for(int j = 0; j < polygons.size(); j++){
            ArrayList<Integer> neighbouridx = new ArrayList<>();
            ArrayList<Integer> seg1 = new ArrayList<>();
            seg1.add(polygons.get(j).getSegmentIdxs(0));
            seg1.add(polygons.get(j).getSegmentIdxs(1));
            seg1.add(polygons.get(j).getSegmentIdxs(2));
            seg1.add(polygons.get(j).getSegmentIdxs(3));
            for(int k = 0; k < polygons.size(); k++){
                if(j!=k){
                    ArrayList<Integer> seg2 = new ArrayList<>();
                    seg2.add(polygons.get(k).getSegmentIdxs(0));
                    seg2.add(polygons.get(k).getSegmentIdxs(1));
                    seg2.add(polygons.get(k).getSegmentIdxs(2));
                    seg2.add(polygons.get(k).getSegmentIdxs(3));
                    seg2.retainAll(seg1);
                    if(!seg2.isEmpty()){
                        neighbouridx.add(k);
                    }
                }
            }
            Polygon p2 = Polygon.newBuilder(polygons.get(j)).addAllNeighborIdxs(neighbouridx).build();
            polygonsIndexed.add(p2);
        }
        for (int j = 0; j < polygonsIndexed.size(); j++) {
            Polygon x = polygonsIndexed.get(j);
            System.out.print("Index: " + j);
            for (int k = 0; k < x.getNeighborIdxsCount(); k++) {
                System.out.print(" "+ x.getNeighborIdxs(k) + " ");
            }
            System.out.print("\n");
        }
        return Mesh.newBuilder().addAllPolygons(polygonsIndexed).addAllSegments(segments).addAllVertices(verticesWithColors).build();
    }

    public Mesh generateIrregular() {
        ArrayList<Vertex> centroids = new ArrayList<>();
        ArrayList<Coordinate> centroidCoordinates = new ArrayList<>();
        // Create all the vertices
        Random bag = new Random();
        for (int x = 0; x <= width; x += square_size) {
            for (int y = 0; y <= height; y += square_size) {
                double xVal = (double) bag.nextInt(x, x + square_size);
                double yVal = (double) bag.nextInt(y, y + square_size);
                xVal = Math.round(xVal * 100.0) / 100.0;
                yVal = Math.round(yVal * 100.0) / 100.0;
                System.out.println(xVal + " " + yVal);
                // System.out.println("Xval: " + xVal + "Yval: " + yVal);
                centroids.add(Vertex.newBuilder().setX(xVal).setY(yVal).build());
                centroidCoordinates.add(new Coordinate(xVal, yVal));
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them

        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
        vdb.setClipEnvelope(new Envelope(0,width,0,height));
        vdb.setSites(centroidCoordinates);
        Geometry diagram = vdb.getDiagram(new GeometryFactory());

        ArrayList<Vertex> vertices = new ArrayList<>();
        Set<Coordinate> coordSet = new HashSet<>();
        for(int i = 0; i < diagram.getNumGeometries(); i++) {
            Coordinate[] points = diagram.getGeometryN(i).getCoordinates();
            for(Coordinate point : points) {
                if(!coordSet.contains(point)){
                    vertices.add(Vertex.newBuilder().setX(point.x).setY(point.y).build());
                    coordSet.add(point);
                }
            }   
        }

        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        for (Vertex v : vertices) {
            verticesWithColors.add(Vertex.newBuilder(v).addProperties(Property.newBuilder().setKey("rgb_color").setValue("255,0,0").build()).build());
        }
        verticesWithColors.addAll(centroids);
        return Mesh.newBuilder()/*.addAllPolygons(polygonsIndexed).addAllSegments(segments)*/.addAllVertices(verticesWithColors).build();
    }

    private String extractPropertyAverage(List<Property> properties1, List<Property> properties2, String key) {
        String val1 = null;
        String val2 = null;
        for (Property p : properties1) {
            if (p.getKey().equals(key)) {
                val1 = p.getValue();
            }
        }
        for (Property p : properties2) {
            if (p.getKey().equals(key)) {
                val2 = p.getValue();
            }
        }
        if(key.equals("rgb_color")) {
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
        } else if (key.equals("thickness")) {
            if (val1 == null)
                val1 = "3";
            if (val2 == null)
                val2 = "3";
            int average = (Integer.parseInt(val1) + Integer.parseInt(val2)) / 2;
            return Integer.toString(average);
        } else if (key.equals("alpha")) {
            if (val1 == null)
                val1 = "50";
            if (val2 == null)
                val2 = "50";
            int average = (Integer.parseInt(val1) + Integer.parseInt(val2)) / 2;
            return Integer.toString(average);
        }
        return null;
    }
}
