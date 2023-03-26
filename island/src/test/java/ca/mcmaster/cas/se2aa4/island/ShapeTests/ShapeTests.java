package ca.mcmaster.cas.se2aa4.island.ShapeTests;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Shape.Circle;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.Shape.Rectangle;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;
import ca.mcmaster.cas.se2aa4.island.TestSuite;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShapeTests implements TestSuite {
    @Test
    public void PolygonCreation(){
        Shape Irregular = new Irregular();
        Shape Circle = new Circle();
        Shape Rectangle = new Rectangle();
        Irregular.create();
        Circle.create();
        Rectangle.create();
        assertEquals(true, Circle.contains(250,250));
        assertEquals(false, Circle.contains(50,50));
        assertEquals(false, Circle.contains(0,0));
        assertEquals(false, Circle.contains(-100,-100));

        assertEquals(true, Rectangle.contains(250,250));
        assertEquals(true, Rectangle.contains(50,50));
        assertEquals(false, Rectangle.contains(0,0));
        assertEquals(false, Rectangle.contains(-100,-100));

        assertEquals(false, Irregular.contains(0,0));
        assertEquals(false, Irregular.contains(-100,-100));
    }
    @Test
    public void checkIrregularIslandCreation(){
        config.shapeObj = new Irregular();
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
    @Test
    public void checkRectangularIslandCreation(){
        config.shapeObj = new Rectangle();
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
    @Test
    public void checkCircularIslandCreation(){
        config.shapeObj = new Circle();
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
}
