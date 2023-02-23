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
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int num_iterations = 10;

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
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Polygon p = Polygon.newBuilder().addAllSegmentIdxs(pSegments).build();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Polygon pColored = Polygon.newBuilder(p).addProperties(color).setCentroidIdx(verticesWithColors.size()).build();
            polygons.add(pColored);
            if((polygons.size())%25==0){
                i+=3;
            } else {
                i+=2;
            }
        }

        for(int x = square_size /2; x <= width; x += square_size){
            for(int y = square_size / 2; y <= height; y += square_size){
                Vertex v1 = Vertex.newBuilder().setX(x).setY(y).build();
                String colorCode = "0,0,0";
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Vertex coloredv1 = Vertex.newBuilder(v1).addProperties(color).build();
                verticesWithColors.add(coloredv1);
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

        for (int j = 0; j < polygonsIndexed.size(); j++) {
            Polygon x = polygonsIndexed.get(j);
            for (int k = 0; k < x.getNeighborIdxsCount(); k++) {
                
            }
        }

        System.out.println(polygonsIndexed);
        return Mesh.newBuilder().addAllPolygons(polygonsIndexed).addAllSegments(segments).addAllVertices(verticesWithColors).build();
    }

    public Mesh generateIrregular(int num_iterations, int numPolygons) {
        ArrayList<Vertex> centroids = new ArrayList<>();
        ArrayList<Coordinate> centroidCoordinates = new ArrayList<>();
        // Create all the vertices
        Random bag = new Random();
        int numCentroids = numPolygons;
        int spacing = width / 3;
        int centroidsPerArea = numCentroids / 9;
        for (int x = 0; x <= width; x += spacing) {
            for (int y = 0; y <= height; y += spacing) {
                for(int i = 0; i < centroidsPerArea; i++) {
                    double xVal = (double) bag.nextInt(x, x + spacing);
                    double yVal = (double) bag.nextInt(y, y + spacing);
                    xVal = Math.round(xVal * 100.0) / 100.0;
                    yVal = Math.round(yVal * 100.0) / 100.0;
                    // System.out.println("Xval: " + xVal + "Yval: " + yVal);
                    centroids.add(Vertex.newBuilder().setX(xVal).setY(yVal).build());
                    centroidCoordinates.add(new Coordinate(xVal, yVal));
                }
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> vertices = null;
        ArrayList<String> coordSet = null;
        ArrayList<String> segSet = null;
        ArrayList<Segment> segments = null;
        ArrayList<Polygon> polygons = null;
        for (int z = 0; z < num_iterations; z++) {
            VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
            vdb.setSites(centroidCoordinates);
            vdb.setClipEnvelope(new Envelope(0, width, 0, height));
            Geometry diagram = vdb.getDiagram(new GeometryFactory());

            vertices = new ArrayList<>();
            coordSet = new ArrayList<>();
            segSet = new ArrayList<>();
            segments = new ArrayList<>();
            polygons = new ArrayList<>();
            centroids = new ArrayList<>();
            centroidCoordinates = new ArrayList<>();
            int lastVertexIdx = -1;
            for (int i = 0; i < diagram.getNumGeometries(); i++) {
                Geometry g = diagram.getGeometryN(i);
                Coordinate[] points = g.getCoordinates();
                if(points.length <= 2) continue;
                ArrayList<Integer> pSegments = new ArrayList<>();
                for (Coordinate point : points) {
                    double x = (double) point.x;
                    double y = (double) point.y;
                    x = Math.round(x * 100.0) / 100.0;
                    y = Math.round(y * 100.0) / 100.0;
                    if (!coordSet.contains(x + "," + y)) {
                        Vertex v = Vertex.newBuilder().setX(x).setY(y).build();
                        int red = bag.nextInt(255);
                        int green = bag.nextInt(255);
                        int blue = bag.nextInt(255);
                        String thick = Integer.toString(bag.nextInt(2, 6));
                        String colorCode = red + "," + green + "," + blue;
                        String alphaVal = Integer.toString(bag.nextInt(150, 255));
                        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                        Property thickness = Property.newBuilder().setKey("thickness").setValue(thick).build();
                        Property alpha = Property.newBuilder().setKey("alpha").setValue(alphaVal).build();
                        Vertex colored = Vertex.newBuilder(v).addProperties(color).addProperties(thickness).addProperties(alpha).build();
                        vertices.add(colored);
                        coordSet.add(x + "," + y);
                        if (lastVertexIdx != -1) {
                            Segment s = Segment.newBuilder().setV1Idx(lastVertexIdx).setV2Idx(vertices.size() - 1).build();
                            Vertex v1 = vertices.get(s.getV1Idx());
                            Vertex v2 = vertices.get(s.getV2Idx());
                            String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                            String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                            String alpha1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
                            Property colorS = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                            Property thicknessS = Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                            Property alphaS = Property.newBuilder().setKey("alpha").setValue(alpha1).build();
                            Segment coloredSegment = Segment.newBuilder(s).addProperties(colorS).addProperties(thicknessS).addProperties(alphaS).build();
                            segments.add(coloredSegment);
                            String sEntry1 = v1.getX() + "," + v1.getY() + "," + v2.getX() + "," + v2.getY();
                            String sEntry2 = v2.getX() + "," + v2.getY() + "," + v1.getX() + "," + v1.getY();
                            segSet.add(sEntry1);
                            segSet.add(sEntry2);
                            //System.out.println(sEntry1);
                            pSegments.add(segments.size() - 1);
                        }
                        lastVertexIdx = vertices.size() - 1;
                    } else {
                        //System.out.println("duplicate vertex found");
                        int idx = coordSet.indexOf(x + "," + y);
                        if (lastVertexIdx != -1) {
                            Vertex v1 = vertices.get(lastVertexIdx);
                            Vertex v2 = vertices.get(idx);
                            if (!segSet.contains(v1.getX() + "," + v1.getY() + "," + v2.getX() + "," + v2.getY())) {
                                Segment s = Segment.newBuilder().setV1Idx(lastVertexIdx).setV2Idx(idx).build();
                                String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                                String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                                String alpha1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
                                Property colorS = Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                                Property thicknessS = Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                                Property alphaS = Property.newBuilder().setKey("alpha").setValue(alpha1).build();
                                Segment coloredSegment = Segment.newBuilder(s).addProperties(colorS).addProperties(thicknessS).addProperties(alphaS).build();
                                segments.add(coloredSegment);
                                String sEntry1 = v1.getX() + "," + v1.getY() + "," + v2.getX() + "," + v2.getY();
                                String sEntry2 = v2.getX() + "," + v2.getY() + "," + v1.getX() + "," + v1.getY();
                                segSet.add(sEntry1);
                                segSet.add(sEntry2);
                                // System.out.println(sEntry1);
                                pSegments.add(segments.size() - 1);
                            } else {
                                pSegments.add(segSet.indexOf(v1.getX() + "," + v1.getY() + "," + v2.getX() + "," + v2.getY()) / 2);
                                //System.out.println("duplicate segment found");
                            }
                        }
                        lastVertexIdx = idx;
                    }
                }
                List<List<Property>> props = new ArrayList<>();
                for (int idx : pSegments) {
                    props.add(segments.get(idx).getPropertiesList());
                }
                Polygon p = Polygon.newBuilder().addAllSegmentIdxs(pSegments).build();
                Property color = Property.newBuilder().setKey("color").setValue(extractPropertyAverageN(props, "rgb_color")).build();
                Property alpha = Property.newBuilder().setKey("alpha").setValue(extractPropertyAverageN(props, "alpha")).build();
                Polygon pColored = Polygon.newBuilder(p).addProperties(alpha).addProperties(color).build();
                if(pSegments.size() > 2) {
                    polygons.add(pColored);
                    double xVal = g.getCentroid().getX();
                    double yVal = g.getCentroid().getY();
                    xVal = Math.round(xVal * 100.0) / 100.0;
                    yVal = Math.round(yVal * 100.0) / 100.0;

                    Vertex centroid = Vertex.newBuilder().setX(xVal).setY(yVal).addProperties(alpha).addProperties(color).build();
                    centroids.add(centroid);
                    centroidCoordinates.add(new Coordinate(xVal,yVal));
                }
                lastVertexIdx = -1;

            }
//            System.out.println(polygons);
            if(z < num_iterations-1){
                ArrayList prev = new ArrayList<>();
//                System.out.println(centroids.size());
//                System.out.println(centroidCoordinates.size());
                centroids = new ArrayList<>();
                centroidCoordinates = new ArrayList<>();
                for (Polygon x: polygons) {
//                    System.out.println(x.getCentroidIdx());
//                    System.out.println(x);
                    ArrayList initialCoordinates = new ArrayList<>();
                    for (int i = 0; i < x.getSegmentIdxsCount(); i++) {
                        Segment s = segments.get(x.getSegmentIdxs(i));
                        if (!initialCoordinates.contains(s.getV1Idx())){
                            initialCoordinates.add(s.getV1Idx());
                        }
                        if (!initialCoordinates.contains(s.getV2Idx())){
                            initialCoordinates.add(s.getV2Idx());
                        }
                    }
//                    System.out.println(initialCoordinates);

                    double coord[] = extractCentroidPolygon(vertices, initialCoordinates);
                    centroids.add(Vertex.newBuilder().setX(coord[0]).setY(coord[1]).build());
                    centroidCoordinates.add(new Coordinate(coord[0], coord[1]));
                    prev = initialCoordinates;
                }
                System.out.println(centroids.size());
                System.out.println(centroidCoordinates.size());
            }


        }

//        System.out.println(centroids);
//        System.out.println(centroidCoordinates);
//
//        System.out.println(centroids.size());
//        System.out.println(polygons.size());

        int initialVerticesSize = vertices.size();
        for(int i = 0; i < polygons.size(); i++){
            Polygon pCentroid = Polygon.newBuilder(polygons.get(i)).setCentroidIdx(vertices.size() + i).build();
            polygons.set(i, pCentroid);
            System.out.println(centroids.get(i).getPropertiesList());
        }

//        DelaunayTriangulationBuilder dt = new DelaunayTriangulationBuilder();
//        dt.setSites(centroidCoordinates);
//        Geometry delaunay = dt.getTriangles(new GeometryFactory());
//        for (int i = 0; i < delaunay.getNumGeometries(); i++) {
//            Coordinate[] coords = delaunay.getGeometryN(i).getCoordinates();
//            ArrayList<Vertex> triangleVertices = new ArrayList<>();
//            for (Coordinate c : coords) {
 //               double x = (double) c.x;
//                double y = (double) c.y;
//                x = Math.round(x * 100.0) / 100.0;
//                y = Math.round(y * 100.0) / 100.0;
//               Vertex v = Vertex.newBuilder().setX(x).setY(y).build();
//                triangleVertices.add(v);
//            }

        vertices.addAll(centroids);
        for (int i = 0; i < centroids.size(); i++) {
            Vertex v = centroids.get(i);
            String x = String.valueOf(v.getX());
            String y = String.valueOf(v.getY());
            coordSet.add(x+","+y);
        }
//        System.out.println(initialVerticesSize);
        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
        dtb.setSites(centroidCoordinates);
        Geometry neighbours_diagram = dtb.getEdges(new GeometryFactory());
        HashMap <Integer, ArrayList> neighbourVals = new HashMap<>();
        for (int i = 0; i < neighbours_diagram.getNumGeometries(); i++) {
            Geometry nd = neighbours_diagram.getGeometryN(i);
            Coordinate points[] = nd.getCoordinates();
//            System.out.println(points.length);
            Coordinate a = points[0];
            Coordinate b = points[1];
            String aCoord = a.x+","+a.y;
            String bCoord = b.x+","+b.y;
            //System.out.println(aCoord + " " + bCoord);
            //System.out.println(coordSet.contains(aCoord));
            int aidx = coordSet.indexOf(aCoord)-initialVerticesSize;
            int bidx = coordSet.indexOf(bCoord)-initialVerticesSize;
//            System.out.println(aidx);
//            System.out.println(bidx);
            ArrayList aInputList = new ArrayList<>();
            ArrayList bInputList = new ArrayList<>();
            aInputList.add(bidx);
            bInputList.add(aidx);
            if(neighbourVals.containsKey(aidx)){
                ArrayList ans = neighbourVals.get(aidx);
                ans.add(bidx);
                neighbourVals.put(aidx,ans);
            } else {
                neighbourVals.put(aidx, aInputList);
                ArrayList ans = neighbourVals.get(aidx);
                ans.add(bidx);
                neighbourVals.put(aidx,ans);
            }
            if(neighbourVals.containsKey(bidx)){
                ArrayList ans = neighbourVals.get(bidx);
                ans.add(aidx);
                neighbourVals.put(bidx,ans);
            } else {
                neighbourVals.put(bidx, bInputList);
                ArrayList ans = neighbourVals.get(bidx);
                ans.add(aidx);
                neighbourVals.put(bidx,ans);
            }
        }
//        System.out.println(centroids.size());
//        System.out.println(coordSet);
//        System.out.println(neighbours_diagram);
       // System.out.println(neighbourVals);
        for(int i = 0; i < polygons.size(); i++){
            try{
                ArrayList nList = neighbourVals.get(i);
                Polygon pNeighbours = Polygon.newBuilder(polygons.get(i)).addAllNeighborIdxs(nList).build();
                polygons.set(i, pNeighbours);
            } catch (Exception e){
                //System.out.println("Null");
            }

        }
        return Mesh.newBuilder().addAllPolygons(polygons).addAllSegments(segments).addAllVertices(vertices).build();
    }

    private double[] extractCentroidPolygon(ArrayList<Vertex> vertices, ArrayList<Integer> initCoordinates){
        double x = (double) 0;
        double y = (double) 0;
        for(Object coord : initCoordinates){
            Vertex v = (Vertex) vertices.get((int) coord);
            x += v.getX();
            y += v.getY();
        }
        double xVal = x/initCoordinates.size();
        double yVal = y/initCoordinates.size();

        xVal = Math.round(xVal * 100.0) / 100.0;
        yVal = Math.round(yVal * 100.0) / 100.0;
        return new double[]{xVal, yVal};
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
    private String extractPropertyAverageN(List<List<Property>> properties, String key) {
        List<String> vals = new ArrayList<>();
        String result = "";
        for(List<Property> props : properties) {
            String prop = "";
            for(Property p : props){
                if(p.getKey().equals(key)) {
                    prop = p.getValue();
                }
            }
            vals.add(prop);
            if(key.equals("rgb_color")) {
                if(prop.equals("")){
                    prop = "0,0,0";
                }
                String[] raw = prop.split(",");
                int red = Integer.parseInt(raw[0]);
                int green = Integer.parseInt(raw[1]);
                int blue = Integer.parseInt(raw[2]);
                String[] currentRaw = result.split(",");
                int currentRed,currentBlue,currentGreen;
                if(result.equals("result")){
                    currentRed = Integer.parseInt(currentRaw[0]);
                    currentGreen = Integer.parseInt(currentRaw[1]);
                    currentBlue = Integer.parseInt(currentRaw[2]);
                } else {
                    currentRed = 0;
                    currentGreen = 0;
                    currentBlue = 0;
                }
                result = (red + currentRed) + "," + (green + currentGreen) + "," + (blue + currentBlue);
            } else if (key.equals("thickness")) {
                if(prop.equals("")){
                    prop = "3";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + Integer.parseInt(prop) + "";
            } else if (key.equals("alpha")) {
                if (prop.equals("")){
                    prop = "75";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + Integer.parseInt(prop) + "";
            }
        }
        return result;
    }
}
