package ca.mcmaster.cas.se2aa4.island.AltitudeTests;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Altitude.VolcanoAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;
import ca.mcmaster.cas.se2aa4.island.TestSuite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AltitudeTests implements TestSuite {
    @Test
    public void checkAltTagsVolcano(){
        config.altProfile = new VolcanoAltitude();
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Polygon> plist = FinalMesh.getPolygonsList();
        boolean checkTiles = true;
        String alt;
        String tiles;
        for(Structs.Polygon p: plist){
            alt = altTagEx.extractValues(p.getPropertiesList());
            tiles = tileTagEx.extractValues(p.getPropertiesList());
            if(alt.equals("null")){
                checkTiles = false;
                break;
            } else if (tiles.equals("ocean")) {
                if(!alt.equals("0")){
                    checkTiles = false;
                    break;
                }
            }
        }
        assertEquals(checkTiles, true);
    }
    @Test
    public void checkAltTags(){
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Polygon> plist = FinalMesh.getPolygonsList();
        boolean checkTiles = true;
        String alt;
        String tiles;
        for(Structs.Polygon p: plist){
            alt = altTagEx.extractValues(p.getPropertiesList());
            tiles = tileTagEx.extractValues(p.getPropertiesList());
            if(alt.equals("null")){
                checkTiles = false;
                break;
            } else if (tiles.equals("ocean")) {
                if(!alt.equals("0")){
                    checkTiles = false;
                    break;
                }
            }
        }
        assertEquals(checkTiles, true);
    }
}
