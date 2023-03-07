package ca.mcmaster.cas.se2aa4.island.Shape;

import java.awt.geom.Ellipse2D;

public class Circle implements Shape{

    Ellipse2D circle;
    @Override
    public boolean contains(double x, double y) {
        return this.circle.contains(x, y);
    }

    @Override
    public void create() {
        this.circle = new Ellipse2D.Double(50,50,400,400);
    }
}
