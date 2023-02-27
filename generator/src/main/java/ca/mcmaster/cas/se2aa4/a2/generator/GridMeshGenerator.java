package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridMeshGenerator implements  MeshGenerator{

    @Override
    public void SetInitialValues(int num_iterations, int numPolygons) {

    }

    @Override
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
                //System.out.println(x + " " + y + " " + (x / 20 * 26 + y / 20));
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
        for (Vertex v : vertices) {
            int red = this.bag.nextInt(255);
            int green = this.bag.nextInt(255);
            int blue = this.bag.nextInt(255);
            String thick = Integer.toString(this.bag.nextInt(2,7));
            String colorCode = red + "," + green + "," + blue;
            String alphaVal = Integer.toString(this.bag.nextInt(150,255));
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
                String alphaVal = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
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
                String alphaVal = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
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

        List<Vertex> centroids = new ArrayList<>();
        for(int x = square_size /2; x <= width; x += square_size){
            for(int y = square_size / 2; y <= height; y += square_size){
                x = Math.round(x * 100) / 100;
                y = Math.round(y * 100) / 100;
                Vertex v1 = Vertex.newBuilder().setX(x).setY(y).build();
                String colorCode = "0,0,0";
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Vertex coloredv1 = Vertex.newBuilder(v1).addProperties(color).build();
                centroids.add(coloredv1);
            }
        }

        ArrayList<Polygon> polygons = new ArrayList<>();
        int i = 0;
        int offset = 0;
        while(i < segments.size() - 25){
            ArrayList<Integer> pSegments = new ArrayList<>();
            pSegments.add(i);
            if((polygons.size()+1)%25==0){
                pSegments.add(i + 2);
            } else {
                pSegments.add(i + 3);
            }
            if(polygons.size() >= ((width / square_size) - 1) * (height / square_size)){
                pSegments.add(i + 51 - offset);
                offset++;
            } else pSegments.add(i + 51);
            pSegments.add(i + 1);
            int red = this.bag.nextInt(255);
            int green = this.bag.nextInt(255);
            int blue = this.bag.nextInt(255);
            int a = this.bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Polygon p = Polygon.newBuilder().addAllSegmentIdxs(pSegments).setCentroidIdx(verticesWithColors.size() + polygons.size()).build();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property alpha = Property.newBuilder().setKey("alpha").setValue(a + "").build();
            Polygon pColored = Polygon.newBuilder(p).addProperties(color).addProperties(alpha).build();
            polygons.add(pColored);
            if((polygons.size())%25==0){
                i+=3;
            } else {
                i+=2;
            }
        }

        ArrayList<Polygon> polygonsIndexed = new ArrayList<>();

        for(int j = 0; j < polygons.size(); j++){
            ArrayList<Integer> neighbouridx = new ArrayList<>();
            Set<Integer> seg1 = new HashSet<>();
            seg1.addAll(polygons.get(j).getSegmentIdxsList());
            for(int k = 0; k < polygons.size(); k++){
                if(j!=k){
                    Set<Integer> seg2 = new HashSet<>();
                    seg2.addAll(polygons.get(k).getSegmentIdxsList());
                    seg2.retainAll(seg1);
                    if(!seg2.isEmpty()){
                        neighbouridx.add(k);
                    }
                }
            }
            Polygon p2 = Polygon.newBuilder(polygons.get(j)).addAllNeighborIdxs(neighbouridx).build();
            polygonsIndexed.add(p2);
        }

        verticesWithColors.addAll(centroids);
        return Mesh.newBuilder().addAllPolygons(polygonsIndexed).addAllSegments(segments).addAllVertices(verticesWithColors).build();
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
