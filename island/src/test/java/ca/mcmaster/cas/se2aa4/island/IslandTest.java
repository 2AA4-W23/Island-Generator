package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.generator.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class IslandTest {
    @Test
    public void checkRectangularIslandCreation(){
        Structs.Mesh testMesh = MeshCreator();
        Structs.Mesh islandMesh = IslandGenerator.Generate(testMesh, "rectangle");
        assertNotNull(islandMesh);
    }
    @Test
    public void checkCircularIslandCreation(){
        Structs.Mesh testMesh = MeshCreator();
        Structs.Mesh islandMesh = IslandGenerator.Generate(testMesh, "circle");
        assertNotNull(islandMesh);
    }
    @Test
    public void checkTileTags(){
        Structs.Mesh testMesh = MeshCreator();

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
        Structs.Mesh testMesh = MeshCreator();

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
        Structs.Mesh testMesh = generator.generate(2000, 5);
        return testMesh;
    }
}
