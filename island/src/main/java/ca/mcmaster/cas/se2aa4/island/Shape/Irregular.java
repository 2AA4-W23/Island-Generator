package ca.mcmaster.cas.se2aa4.island.Shape;

import org.apache.batik.ext.awt.geom.Polygon2D;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.algorithm.hull.*;

import java.util.Random;

public class Irregular implements Shape{
    Geometry shape;
    Random bag = new Random();
    @Override
    public boolean contains(double x, double y) {
        PrecisionModel pm = new PrecisionModel();
        Geometry point = new Point(new Coordinate(x,y), pm, 4);

        return this.shape.contains(point);
    }

    @Override
    public void create() {

        int numPoints = bag.nextInt(50,100);
        Coordinate[] pts =  new Coordinate[numPoints];


        for (int i = 0; i < numPoints; i++) {
            int x = bag.nextInt(100,400);
            int y = bag.nextInt(100,400);
            pts[i] = new Coordinate(x,y);
        }
//        ConcaveHull cv = new ConcaveHull();
        ConvexHull c = new ConvexHull(pts, new GeometryFactory());
        this.shape = c.getConvexHull();

    }
}
