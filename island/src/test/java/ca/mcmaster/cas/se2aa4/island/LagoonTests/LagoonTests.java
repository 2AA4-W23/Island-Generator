package ca.mcmaster.cas.se2aa4.island.LagoonTests;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Lagoon;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;
import ca.mcmaster.cas.se2aa4.island.TestSuite;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LagoonTests implements TestSuite {
    @Test
    public void LakeGenerationTest(){
        Configuration config = new Configuration();
        config.shapeObj = new Irregular();
        config.inputMesh = this.globalMesh;
        config.num_lakes = 7;
        config.num_aquifers = 2;
        config.biomeProfile = new BaseBiomeProfile();
        config.altProfile = new RandomAltitude();
        config.soilProfile = new WetSoilProfile();
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Polygon> plist = FinalMesh.getPolygonsList();

        List<Structs.Polygon> lakeTiles = new ArrayList<>();
        List<String> lakeList = new ArrayList<>();
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
    public void checkTileTags(){
        Lagoon lagoonMap = new Lagoon();
        Structs.Mesh newMesh = lagoonMap.LagoonTerrain(globalMesh);
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
        Structs.Mesh newMesh = lagoonMap.LagoonTerrain(globalMesh);
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
}
