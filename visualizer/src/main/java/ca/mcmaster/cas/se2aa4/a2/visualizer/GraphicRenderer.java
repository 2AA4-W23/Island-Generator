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
import java.util.Collections;
import java.util.List;

import org.apache.batik.transcoder.keys.Rectangle2DKey; 

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        List<Vertex> VertexList = aMesh.getVerticesList();
        List<Segment> SegmentList = aMesh.getSegmentsList();
        List<Polygon> PolygonList = aMesh.getPolygonsList();

        for (Polygon p : PolygonList) {
            double x = VertexList.get(SegmentList.get(p.getSegmentIdxs(0)).getV1Idx()).getX();
            double y = VertexList.get(SegmentList.get(p.getSegmentIdxs(0)).getV1Idx()).getY();
            Color old = canvas.getColor();
            canvas.setColor(extractColor(p.getPropertiesList()));
            Rectangle2D polygon = new Rectangle2D.Double(x,y,20,20);
            canvas.fill(polygon);
            canvas.setColor(old);
        }
        
        for (Segment s : SegmentList) {
            double x1 = VertexList.get(s.getV1Idx()).getX();
            double x2 = VertexList.get(s.getV2Idx()).getX();
            double y1 = VertexList.get(s.getV1Idx()).getY();
            double y2 = VertexList.get(s.getV2Idx()).getY();
            Color old1 = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            Line2D line = new Line2D.Double(x1, y1, x2,y2);
            canvas.draw(line);
            canvas.setColor(old1);
        }

        for (Vertex v: VertexList) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
        
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
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
