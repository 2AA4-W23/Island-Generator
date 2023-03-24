package ca.mcmaster.cas.se2aa4.island.Shape;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.algorithm.hull.*;

import java.util.Random;

public class Irregular implements Shape{
    Geometry shape;

    @Override
    public boolean contains(double x, double y) {
        PrecisionModel pm = new PrecisionModel();
        Geometry point = new Point(new Coordinate(x,y), pm, 4);

        return this.shape.contains(point);
    }
    @Override
    public void create() {
        int numPoints = rng.nextInt(50,75);
        Coordinate[] pts =  new Coordinate[numPoints+1];
        pts[0] = new Coordinate(250,250);
        for (int i = 1; i < numPoints+1; i++) {
            int x = rng.nextInt(25,475);
            int y = rng.nextInt(25,475);
            pts[i] = new Coordinate(x,y);
        }
        Geometry g1 = new GeometryFactory().createLineString(pts);
        this.shape = ConcaveHull.concaveHullByLengthRatio(g1, 0.5, false);

    }
}
