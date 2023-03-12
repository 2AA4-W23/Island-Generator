package ca.mcmaster.cas.se2aa4.island;
import ca.mcmaster.cas.se2aa4.a2.generator.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.LakeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.Shape.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class IslandTest {
    Extractor tileTagEx = new TileTagExtractor();
    Extractor lakeTagEx = new LakeExtractor();
    Structs.Mesh testMesh = MeshCreator();

    @Test
    public void LakeGenerationTest(){
        Configuration config = new Configuration();
        config.shapeObj = new Irregular();
        config.inputMesh = this.testMesh;
        config.num_lakes = 7;
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Polygon> plist = FinalMesh.getPolygonsList();

        List<Structs.Polygon> lakeTiles = new ArrayList<>();
        List lakeList = new ArrayList<>();
        for(Structs.Polygon p: plist){
            String tileTag = tileTagEx.extractValues(p.getPropertiesList());
            if(tileTag.equals("lake")){
                lakeTiles.add(p);
            }
        }
        for(Structs.Polygon lake: lakeTiles){
            String numLakes = lakeTagEx.extractValues(lake.getPropertiesList());
            if(!lakeList.contains(numLakes)){
                lakeList.add(numLakes);
            }
        }
        assertEquals(7, lakeList.size());
    }
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

        assertEquals(true, Irregular.contains(250,250));
        assertEquals(false, Irregular.contains(0,0));
        assertEquals(false, Irregular.contains(-100,-100));
    }
    @Test
    public void checkIrregularIslandCreation(){
        Configuration config = new Configuration();
        config.shapeObj = new Irregular();
        config.inputMesh = this.testMesh;
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
    @Test
    public void checkRectangularIslandCreation(){
        Configuration config = new Configuration();
        config.shapeObj = new Rectangle();
        config.inputMesh = testMesh;
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
    @Test
    public void checkCircularIslandCreation(){
        Configuration config = new Configuration();
        config.shapeObj = new Circle();
        config.inputMesh = testMesh;
        Structs.Mesh islandMesh = IslandGenerator.Generate(config);
        assertNotNull(islandMesh);
    }
    @Test
    public void checkTileTags(){

        Lagoon lagoonMap = new Lagoon();
        Structs.Mesh newMesh = lagoonMap.LagoonTerrain(testMesh);
        List<Structs.Polygon> pList = newMesh.getPolygonsList();
        boolean checkTiles = true;
        String tiles;
        for(Structs.Polygon p: pList){
            tiles = lagoonMap.extractTag(p.getPropertiesList());
            if(tiles.equals("null")){
                checkTiles = false;
                break;
            }
        }
        assertEquals(checkTiles, true);
    }
    @Test
    public void checkRGB(){

        Lagoon lagoonMap = new Lagoon();
        Structs.Mesh newMesh = lagoonMap.LagoonTerrain(testMesh);
        List<Structs.Polygon> pList = newMesh.getPolygonsList();
        boolean checkRgbColors = true;
        String tiles;
        String rgb;
        for(Structs.Polygon p: pList){
            tiles = lagoonMap.extractTag(p.getPropertiesList());
            rgb = lagoonMap.extractColor(p.getPropertiesList());
            if(tiles.equals("ocean")){
                if(!rgb.equals("43,101,236")) {
                    checkRgbColors =false;
                    break;
                }
            } else if (tiles.equals("land")) {
                if(!rgb.equals("144,238,144")) {
                    checkRgbColors =false;
                    break;
                }
            } else if (tiles.equals("lagoon")) {
                if(!rgb.equals("70,130,180")) {
                    checkRgbColors =false;
                    break;
                }
            } else if (tiles.equals("beach")) {
                if(!rgb.equals("194,178,128")) {
                    checkRgbColors =false;
                    break;
                }
            } else {
                checkRgbColors =false;
                break;
            }
        }
        assertEquals(checkRgbColors, true);
    }

    private Structs.Mesh MeshCreator(){
        //Create Mesh
        MeshGenerator generator = new IrregularMeshGenerator();
        Structs.Mesh testMesh = generator.generate(3, 1000);
        return testMesh;
    }



}
