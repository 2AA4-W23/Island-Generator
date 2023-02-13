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
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas, Boolean debug) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        List<Vertex> VertexList = aMesh.getVerticesList();
        List<Segment> SegmentList = aMesh.getSegmentsList();
        List<Polygon> PolygonList = aMesh.getPolygonsList();

        int lowCentroidIdx = VertexList.size();

        for (Polygon p : PolygonList) {
            double x = VertexList.get(SegmentList.get(p.getSegmentIdxs(0)).getV1Idx()).getX();
            double y = VertexList.get(SegmentList.get(p.getSegmentIdxs(0)).getV1Idx()).getY();
            lowCentroidIdx = Math.min(p.getCentroidIdx(), lowCentroidIdx);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(p.getPropertiesList()));
            if(debug){
                canvas.setColor(new Color(0,0,0));
            }
            Rectangle2D polygon = new Rectangle2D.Double(x,y,20,20);
            canvas.fill(polygon);
            canvas.setColor(old);
        }
        int centroidIdx = 0;

        if (debug) {
            for (Polygon p : PolygonList) {
                centroidIdx = p.getCentroidIdx();
                int numNeighbours = p.getNeighborIdxsCount();
                for (int i = 0; i < numNeighbours; i++) {
                    double cx1 = VertexList.get(centroidIdx).getX();
                    double cy1 = VertexList.get(centroidIdx).getY();
                    double cx2 = VertexList.get(PolygonList.get(p.getNeighborIdxs(i)).getCentroidIdx()).getX();
                    double cy2 = VertexList.get(PolygonList.get(p.getNeighborIdxs(i)).getCentroidIdx()).getY();
                    Line2D line = new Line2D.Double(cx1, cy1, cx2, cy2);
                    Color old1 = canvas.getColor();
                    canvas.setColor(new Color(192, 192, 192));
                    canvas.draw(line);
                    canvas.setColor(old1);
                }

            }
        }

        
        for (Segment s : SegmentList) {
            double x1 = VertexList.get(s.getV1Idx()).getX();
            double x2 = VertexList.get(s.getV2Idx()).getX();
            double y1 = VertexList.get(s.getV1Idx()).getY();
            double y2 = VertexList.get(s.getV2Idx()).getY();
            Color old1 = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            if (debug){
                canvas.setColor(new Color(90,90,90));
            }
            Line2D line = new Line2D.Double(x1, y1, x2,y2);
            canvas.draw(line);
            canvas.setColor(old1);
        }
        int count = 0;
        for (Vertex v: VertexList) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            if(debug){
                if(count < lowCentroidIdx){
                    canvas.setColor(new Color(90,90,90));
                } else{
                    canvas.setColor(new Color(255,0,0));
                }
            }
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
            count ++;
        }
        
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
//                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
