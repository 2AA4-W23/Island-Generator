package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.batik.ext.awt.geom.Polygon2D;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;;

public class GraphicRenderer {

    private Boolean alphaSet = false;
    private int alpha = 0;

    public void render(Mesh aMesh, Graphics2D canvas, Boolean debug, Boolean thickSet, int thick, Boolean alphaSet, int alpha) {
        this.alphaSet = alphaSet;
        this.alpha = alpha;
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        List<Vertex> VertexList = aMesh.getVerticesList();
        List<Segment> SegmentList = aMesh.getSegmentsList();
        List<Polygon> PolygonList = aMesh.getPolygonsList();

        int lowCentroidIdx = VertexList.size();
        int count1 = 0;
        for (Polygon p : PolygonList) {
//            System.out.println(p.getSegmentIdxsCount() + " segments in polygon");
            Coordinate points[] = new Coordinate[p.getSegmentIdxsCount()];
            Set<Integer> added = new HashSet<>();
            int j = 0;
            for(int i : p.getSegmentIdxsList()){
                Integer v1 = SegmentList.get(i).getV1Idx();
                Integer v2 = SegmentList.get(i).getV2Idx();
//                System.out.println("Segment " + i);
                try{
                    if(!added.contains(v1)){
                        float x = (float) VertexList.get(SegmentList.get(i).getV1Idx()).getX();
                        float y = (float) VertexList.get(SegmentList.get(i).getV1Idx()).getY();
                        points[j] = new Coordinate(x, y);
                        added.add(v1);
                        j++;
                    }
                    if(!added.contains(v2)){
                        float x = (float) VertexList.get(SegmentList.get(i).getV2Idx()).getX();
                        float y = (float) VertexList.get(SegmentList.get(i).getV2Idx()).getY();
                        points[j] = new Coordinate(x, y);
                        added.add(v2);
                        j++;
                    }
                } catch(Exception e){
                    break;
                }
            }
            lowCentroidIdx = Math.min(p.getCentroidIdx(), lowCentroidIdx);
            Color old = canvas.getColor();
            Color new1 = new Color(0,0,0);
            int[] intVals = extractColor(p.getPropertiesList());

            new1 = new Color(intVals[0], intVals[1], intVals[2]);
            System.out.println(new1.getAlpha());
////            new1.
//            if(count1 %3==0){
//                new1 = new Color(100,56,232);
//                new1 = old;
//            }
            for (int i = 0; i < 5; i++) {
                new1 = new1.brighter();
            }
            canvas.setColor(new1);

//            else if(count1%2 ==0) {
//                new1 = new Color(255,193,110);
//            } else {
//                new1 = new Color(173,216,250);
//            }
            count1 ++;

            if(debug){
                if(alphaSet) canvas.setColor(new Color(0,0,0,alpha));
                else canvas.setColor(new Color(0,0,0));
            }
            if(j != 0) {
                float[] x = new float[p.getSegmentIdxsCount()];
                float[] y = new float[p.getSegmentIdxsCount()];
                ConvexHull cv = new ConvexHull(points, new GeometryFactory());
                Coordinate[] orderedPoints = cv.getConvexHull().getCoordinates();    
                for(int i = 0; i < p.getSegmentIdxsCount(); i++) {
                    x[i] = (float) orderedPoints[i].getX();
                    y[i] = (float) orderedPoints[i].getY();
                }
                Polygon2D polygon = new Polygon2D(x,y,x.length);
                canvas.fill(polygon);
            }
            canvas.setColor(old);
        }
        int centroidIdx;

        if (debug) {
            for (Polygon p : PolygonList) {
                centroidIdx = p.getCentroidIdx();
                int numNeighbours = p.getNeighborIdxsCount();
                for (int i = 0; i < numNeighbours; i++) {
                    double cx1 = VertexList.get(centroidIdx).getX();
                    double cy1 = VertexList.get(centroidIdx).getY();
                    double cx2 = VertexList.get(PolygonList.get(p.getNeighborIdxs(i)).getCentroidIdx()).getX();
                    double cy2 = VertexList.get(PolygonList.get(p.getNeighborIdxs(i)).getCentroidIdx()).getY();
                    //System.out.println(centroidIdx + ": " + cx1 + " " + PolygonList.get(p.getNeighborIdxs(i)).getCentroidIdx() + ": " + cy1 + " " + cx2 + " " + cy2);
                    if(thickSet) {
                        canvas.setStroke(new BasicStroke(Math.max(1, thick - 1)));
                    }
                    Line2D line = new Line2D.Double(cx1, cy1, cx2, cy2);
                    Color old1 = canvas.getColor();
                    canvas.setColor(new Color(192, 192, 192));
                    if(alphaSet){
                        canvas.setColor(new Color(192,192,192,alpha));
                    }
                    canvas.draw(line);
                    canvas.setColor(old1);
                }

            }
        }

        if(debug){
            for (Segment s : SegmentList) {
                double x1 = VertexList.get(s.getV1Idx()).getX();
                double x2 = VertexList.get(s.getV2Idx()).getX();
                double y1 = VertexList.get(s.getV1Idx()).getY();
                double y2 = VertexList.get(s.getV2Idx()).getY();


                Color old1 = canvas.getColor();
                Stroke oldStroke = canvas.getStroke();
                canvas.setColor(extractColor(s.getPropertiesList(), alphaSet, alpha));
                canvas.setStroke(extractStroke(s.getPropertiesList()));

                if (debug){
                    canvas.setColor(new Color(90,90,90));
                    canvas.setStroke(new BasicStroke(1));
                    if(alphaSet){
                        canvas.setColor(new Color(90,90,90,alpha));
                    }
                } else if(!thickSet) {
                    int v1Thickness = extractThickness(VertexList.get(s.getV1Idx()).getPropertiesList());
                    int v2Thickness = extractThickness(VertexList.get(s.getV2Idx()).getPropertiesList());
                    if(Math.abs(y1 - y2) < 0.01) {
                        x1 += v1Thickness/2.0d;
                        x2 -= v2Thickness/2.0d;
                    }
                    else if(Math.abs(x1 - x2) < 0.01) {
                        y1 += v1Thickness/2.0d;
                        y2 -= v2Thickness/2.0d;
                    }
                }
                if(thickSet){
                    canvas.setStroke(new BasicStroke(Math.max(1, thick - 1)));
                }
                Line2D line = new Line2D.Double(x1, y1, x2, y2);
                canvas.draw(line);
                canvas.setStroke(oldStroke);
                canvas.setColor(old1);
            }
            int count = 0;
            for (Vertex v: VertexList) {
                int thickness = extractThickness(v.getPropertiesList());
                Color old = canvas.getColor();
                canvas.setColor(extractColor(v.getPropertiesList(), alphaSet, alpha));
                if(count >= lowCentroidIdx){
                    canvas.setColor(new Color(0,0,0,0));
                }
                if(debug){
                    thickness = 3;
                    if(count < lowCentroidIdx){
                        if(alphaSet) canvas.setColor(new Color(90,90,90, alpha));
                        else canvas.setColor(new Color(90,90,90));
                    } else{
                        if(alphaSet) canvas.setColor(new Color(255,0,0, alpha));
                        else canvas.setColor(new Color(255,0,0));
                    }
                }
                if(thickSet){
                    thickness = thick + 1;
                }
                double centre_x = v.getX() - (thickness/2.0d);
                double centre_y = v.getY() - (thickness/2.0d);
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, thickness, thickness);
                canvas.fill(point);
                canvas.setColor(old);
                count++;
            }
        }

    }

    private Color averageSegmentColor(List<Integer> edges, List<Segment> allSegments) {
        Color average;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        int edgesCounted = 0;
        for(int s : edges) {
            try {
                Segment edge = allSegments.get(s);
                Color c = extractColor(edge.getPropertiesList(), alphaSet, alpha);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
                a += c.getAlpha();
                edgesCounted++;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(s);
            }
        }
        if(edgesCounted != 0) {   
            r /= edgesCounted;
            g /= edgesCounted;
            b /= edgesCounted;
            a /= edgesCounted;
        }
        if(alphaSet)
            a = alpha;
        average = new Color(r, g, b, a);
        return average;
    }
    private int[] extractColor(List<Property> properties) {
        String color = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                color = p.getValue();
            }
        }
        if (color == null)
            color="0,0,0";
        System.out.println(color);
        String[] raw = color.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        System.out.println(red);
        System.out.println(blue);
        System.out.println(green);
        return new int[]{red, green, blue};
    }
    private Color extractColor(List<Property> properties, boolean alphaSet, int alphaVal) {
        String color = null;
        String alpha = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
//System.out.println(p.getValue());
                color = p.getValue();
            }
            if(p.getKey().equals("alpha")) {
                alpha = p.getValue();
            }
        }
        if (color == null)
            return Color.BLACK;
        
        String[] raw = color.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        if(alphaSet)
            return new Color(red,green,blue,alphaVal);
        else if(alpha != null)
            return new Color(red, green, blue, Integer.parseInt(alpha) % 255);
        else return new Color(red, green, blue, 200);
    }

    private int extractThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("thickness")) {
//                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 3;
        }
    }

    private Stroke extractStroke(List<Property> properties) {
        return new BasicStroke(extractThickness(properties) - 1);
    }

}
