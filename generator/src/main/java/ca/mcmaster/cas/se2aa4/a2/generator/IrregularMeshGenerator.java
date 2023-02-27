package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IrregularMeshGenerator implements MeshGenerator{
    public int num_iterations = 10;
    public int numPolygons = 0;
    @Override
    public Structs.Mesh generate() {
        ArrayList<Structs.Vertex> centroids = new ArrayList<>();
        ArrayList<Coordinate> centroidCoordinates = new ArrayList<>();
        // Create all the vertices
        int numCentroids = numPolygons;
        int spacing = width / 3;
        int centroidsPerArea = numCentroids / 9;
        for (int x = 0; x <= width; x += spacing) {
            for (int y = 0; y <= height; y += spacing) {
                for(int i = 0; i < centroidsPerArea; i++) {
                    double xVal = (double) this.bag.nextInt(x, x + spacing);
                    double yVal = (double) this.bag.nextInt(y, y + spacing);
                    xVal = Math.round(xVal * 100.0) / 100.0;
                    yVal = Math.round(yVal * 100.0) / 100.0;
                    // System.out.println("Xval: " + xVal + "Yval: " + yVal);
                    centroids.add(Structs.Vertex.newBuilder().setX(xVal).setY(yVal).build());
                    centroidCoordinates.add(new Coordinate(xVal, yVal));
                }
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Structs.Vertex> vertices = null;
        ArrayList<String> coordSet = null;
        ArrayList<String> segSet = null;
        ArrayList<Structs.Segment> segments = null;
        ArrayList<Structs.Polygon> polygons = null;
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
                        Structs.Vertex v = Structs.Vertex.newBuilder().setX(x).setY(y).build();
                        int red = this.bag.nextInt(255);
                        int green = this.bag.nextInt(255);
                        int blue = this.bag.nextInt(255);
                        String thick = Integer.toString(this.bag.nextInt(2, 6));
                        String colorCode = red + "," + green + "," + blue;
                        String alphaVal = Integer.toString(this.bag.nextInt(150, 255));
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                        Structs.Property thickness = Structs.Property.newBuilder().setKey("thickness").setValue(thick).build();
                        Structs.Property alpha = Structs.Property.newBuilder().setKey("alpha").setValue(alphaVal).build();
                        Structs.Vertex colored = Structs.Vertex.newBuilder(v).addProperties(color).addProperties(thickness).addProperties(alpha).build();
                        vertices.add(colored);
                        coordSet.add(x + "," + y);
                        if (lastVertexIdx != -1) {
                            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(lastVertexIdx).setV2Idx(vertices.size() - 1).build();
                            Structs.Vertex v1 = vertices.get(s.getV1Idx());
                            Structs.Vertex v2 = vertices.get(s.getV2Idx());
                            String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                            String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                            String alpha1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
                            Structs.Property colorS = Structs.Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                            Structs.Property thicknessS = Structs.Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                            Structs.Property alphaS = Structs.Property.newBuilder().setKey("alpha").setValue(alpha1).build();
                            Structs.Segment coloredSegment = Structs.Segment.newBuilder(s).addProperties(colorS).addProperties(thicknessS).addProperties(alphaS).build();
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
                            Structs.Vertex v1 = vertices.get(lastVertexIdx);
                            Structs.Vertex v2 = vertices.get(idx);
                            if (!segSet.contains(v1.getX() + "," + v1.getY() + "," + v2.getX() + "," + v2.getY())) {
                                Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(lastVertexIdx).setV2Idx(idx).build();
                                String color1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "rgb_color");
                                String thickness1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "thickness");
                                String alpha1 = extractPropertyAverage(v1.getPropertiesList(), v2.getPropertiesList(), "alpha");
                                Structs.Property colorS = Structs.Property.newBuilder().setKey("rgb_color").setValue(color1).build();
                                Structs.Property thicknessS = Structs.Property.newBuilder().setKey("thickness").setValue(thickness1).build();
                                Structs.Property alphaS = Structs.Property.newBuilder().setKey("alpha").setValue(alpha1).build();
                                Structs.Segment coloredSegment = Structs.Segment.newBuilder(s).addProperties(colorS).addProperties(thicknessS).addProperties(alphaS).build();
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
                List<List<Structs.Property>> props = new ArrayList<>();
                for (int idx : pSegments) {
                    props.add(segments.get(idx).getPropertiesList());
                }
                Structs.Polygon p = Structs.Polygon.newBuilder().addAllSegmentIdxs(pSegments).build();
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(plavgExtractor.extractValues(props, "rgb_color")).build();
                Structs.Property alpha = Structs.Property.newBuilder().setKey("alpha").setValue(plavgExtractor.extractValues(props, "alpha")).build();
                Structs.Polygon pColored = Structs.Polygon.newBuilder(p).addProperties(alpha).addProperties(color).build();
                if(pSegments.size() > 2) {
                    polygons.add(pColored);
                    double xVal = g.getCentroid().getX();
                    double yVal = g.getCentroid().getY();
                    xVal = Math.round(xVal * 100.0) / 100.0;
                    yVal = Math.round(yVal * 100.0) / 100.0;

                    Structs.Vertex centroid = Structs.Vertex.newBuilder().setX(xVal).setY(yVal).addProperties(alpha).addProperties(color).build();
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
                for (Structs.Polygon x: polygons) {
//                    System.out.println(x.getCentroidIdx());
//                    System.out.println(x);
                    ArrayList initialCoordinates = new ArrayList<>();
                    for (int i = 0; i < x.getSegmentIdxsCount(); i++) {
                        Structs.Segment s = segments.get(x.getSegmentIdxs(i));
                        if (!initialCoordinates.contains(s.getV1Idx())){
                            initialCoordinates.add(s.getV1Idx());
                        }
                        if (!initialCoordinates.contains(s.getV2Idx())){
                            initialCoordinates.add(s.getV2Idx());
                        }
                    }
//                    System.out.println(initialCoordinates);

                    double coord[] = extractCentroidPolygon(vertices, initialCoordinates);
                    centroids.add(Structs.Vertex.newBuilder().setX(coord[0]).setY(coord[1]).build());
                    centroidCoordinates.add(new Coordinate(coord[0], coord[1]));
                    prev = initialCoordinates;
                }
                System.out.println(centroids.size());
                System.out.println(centroidCoordinates.size());
            }


        }


        int initialVerticesSize = vertices.size();
        for(int i = 0; i < polygons.size(); i++){
            Structs.Polygon pCentroid = Structs.Polygon.newBuilder(polygons.get(i)).setCentroidIdx(vertices.size() + i).build();
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
            Structs.Vertex v = centroids.get(i);
            String x = String.valueOf(v.getX());
            String y = String.valueOf(v.getY());
            coordSet.add(x+","+y);
        }
//        System.out.println(initialVerticesSize);
        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
        dtb.setSites(centroidCoordinates);
        Geometry neighbours_diagram = dtb.getEdges(new GeometryFactory());
        HashMap<Integer, ArrayList> neighbourVals = new HashMap<>();
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

        for(int i = 0; i < polygons.size(); i++){
            try{
                ArrayList nList = neighbourVals.get(i);
                Structs.Polygon pNeighbours = Structs.Polygon.newBuilder(polygons.get(i)).addAllNeighborIdxs(nList).build();
                polygons.set(i, pNeighbours);
            } catch (Exception e){
                //System.out.println("Null");
            }

        }
        return Structs.Mesh.newBuilder().addAllPolygons(polygons).addAllSegments(segments).addAllVertices(vertices).build();
    }

    public void SetInitialValues(int iter, int numP) {
        this.num_iterations = iter;
        this.numPolygons = numP;
    }
    
    private double[] extractCentroidPolygon(ArrayList<Structs.Vertex> vertices, ArrayList<Integer> initCoordinates){
        double x = (double) 0;
        double y = (double) 0;
        for(Object coord : initCoordinates){
            Structs.Vertex v = (Structs.Vertex) vertices.get((int) coord);
            x += v.getX();
            y += v.getY();
        }
        double xVal = x/initCoordinates.size();
        double yVal = y/initCoordinates.size();

        xVal = Math.round(xVal * 100.0) / 100.0;
        yVal = Math.round(yVal * 100.0) / 100.0;
        return new double[]{xVal, yVal};
    }

    private String extractPropertyAverage(List<Structs.Property> properties1, List<Structs.Property> properties2, String key) {
        String val1 = null;
        String val2 = null;
        for (Structs.Property p : properties1) {
            if (p.getKey().equals(key)) {
                val1 = p.getValue();
            }
        }
        for (Structs.Property p : properties2) {
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
    private String extractPropertyAverageN(List<List<Structs.Property>> properties, String key) {
        String result = "";
        for(List<Structs.Property> props : properties) {
            String prop = "";
            int counted = props.size() * 2;
            for(Structs.Property p : props){
                if(p.getKey().equals(key)) {
                    prop = p.getValue();
                }
            }
            if(key.equals("rgb_color")) {
                if(prop.equals("")){
                    prop = "0,0,0";
                }
                String[] raw = prop.split(",");
                int red = Integer.parseInt(raw[0]) ;
                int green = Integer.parseInt(raw[1]);
                int blue = Integer.parseInt(raw[2]);
                String[] currentRaw = result.split(",");
                int currentRed,currentBlue,currentGreen;
                if(!result.equals("")){
                    currentRed = Integer.parseInt(currentRaw[0]) / counted;
                    currentGreen = Integer.parseInt(currentRaw[1]) / counted;
                    currentBlue = Integer.parseInt(currentRaw[2]) / counted;
                } else {
                    currentRed = 0;
                    currentGreen = 0;
                    currentBlue = 0;
                }
                result = (red / counted + currentRed)  + "," + (green/ counted + currentGreen) + "," + (blue/ counted + currentBlue);
            } else if (key.equals("thickness")) {
                if(prop.equals("")){
                    prop = "3";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + (Integer.parseInt(prop) /counted)+ "";
            } else if (key.equals("alpha")) {
                if (prop.equals("")){
                    prop = "75";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + (Integer.parseInt(prop) / counted) + "";
            }
        }
        return result;
    }

    @Override
    public Mesh generate(int num_iter, int numP) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
}
