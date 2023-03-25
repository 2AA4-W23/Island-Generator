package ca.mcmaster.cas.se2aa4.island.BiomeTests;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.*;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;
import ca.mcmaster.cas.se2aa4.island.TestSuite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiomeTests implements TestSuite {
    @Test
    public void IdentifyBaseBiome(){
        BiomeProfile bP = new BaseBiomeProfile();
        double [][] testVals = new double[][]{{0,0}, {-10, 100}, {30, 450}, {10, 200}};
        String answers[] = {"prairies", "tundra", "rainforest", "forest"};
        int count = 0;
        for(double[] d: testVals){
            String ans = bP.identifyBiome(d)[0];
            assertEquals(answers[count], ans);
            count ++;
        }
    }
    @Test
    public void IdentifyArcticBiome(){
        BiomeProfile bP = new ArcticBiomeProfile();
        double [][] testVals = new double[][]{{0,0}, {-10, 100}, {30, 450}, {-10,-10}, {-4, 150}};
        String answers[] = {"steppes", "tundra", "deciduous_forest", "tundra", "taiga"};
        int count = 0;
        for(double[] d: testVals){
            String ans = bP.identifyBiome(d)[0];
            assertEquals(answers[count], ans);
            count ++;
        }
    }
    @Test
    public void IdentifyTropicalBiome(){
        BiomeProfile bP = new TropicalBiomeProfile();
        double [][] testVals = new double[][]{{20,300}, {30, 10}, {30, 450}, {20,100}, {25, 200}};
        String answers[] = {"tropical_forest", "desert", "rainforest", "savanna", "mangrove"};
        int count = 0;
        for(double[] d: testVals){
            String ans = bP.identifyBiome(d)[0];
            assertEquals(answers[count], ans);
            count ++;
        }
    }
    @Test
    public void IdentifyTempBiome(){
        BiomeProfile bP = new TemperateBiomeProfile();
        double [][] testVals = new double[][]{{20,300}, {15, 10}, {5, 200}, {10,75}};
        String answers[] = {"temp_rainforest", "desert", "wetlands", "prairies"};
        int count = 0;
        for(double[] d: testVals){
            String ans = bP.identifyBiome(d)[0];
            assertEquals(answers[count], ans);
            count ++;
        }
    }
    @Test
    public void checkBiomeTags(){
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
        boolean checkTiles = true;
        String biome;
        String rgb;
        for(Structs.Polygon p: plist){
            biome = biomeTagEx.extractValues(p.getPropertiesList());
            rgb = rgbTagEx.extractValues(p.getPropertiesList());
            System.out.println(biome);
            if(tileTagEx.extractValues(p.getPropertiesList()).equals("land")) {
                System.out.println(biome);
                if(biome.equals("null")){
                    checkTiles = false;
                    break;
                } else if (rgb.equals("null")) {
                    checkTiles = false;
                    break;
                }
            }
        }
        assertEquals(true, checkTiles);
    }
}
