package ca.mcmaster.cas.se2aa4.island.Shape;

import org.apache.batik.ext.awt.geom.Polygon2D;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.algorithm.hull.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

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

        int numPoints = bag.nextInt(50,75);
        Coordinate[] pts =  new Coordinate[numPoints+1];
        pts[0] = new Coordinate(250,250);


        for (int i = 1; i < numPoints+1; i++) {
            int x = bag.nextInt(25,475);
            int y = bag.nextInt(25,475);
            pts[i] = new Coordinate(x,y);
        }
        for(Coordinate c: pts){
//            System.out.println(c);
        }
//        ConcaveHull cv = new ConcaveHull();
//        ConvexHull c = new ConvexHull(pts, new GeometryFactory());

        Geometry g1 = new GeometryFactory().createLineString(pts);
        this.shape = ConcaveHull.concaveHullByLengthRatio(g1, 0.5, false);
//        this.shape = c.getConvexHull();

    }
}
