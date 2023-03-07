package ca.mcmaster.cas.se2aa4.island.Shape;

import java.awt.geom.Rectangle2D;

public class Rectangle implements Shape{
    Rectangle2D rectangle;
    @Override
    public boolean contains(double x, double y) {
        return this.rectangle.contains(x, y);
    }

    @Override
    public void create() {
        this.rectangle = new java.awt.Rectangle(50, 50, 400, 400);
    }
}
