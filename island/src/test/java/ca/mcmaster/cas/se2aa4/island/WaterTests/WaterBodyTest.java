package ca.mcmaster.cas.se2aa4.island.WaterTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.TestSuite;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;

public class WaterBodyTest implements TestSuite{
    @Test
    public void checkCollisions(){
        Configuration config = new Configuration();
        config.shapeObj = new Irregular();
        config.inputMesh = TestSuite.globalMesh;
        config.num_lakes = 10;
        config.num_aquifers = 10;
        config.num_rivers = 10;
        config.biomeProfile = new BaseBiomeProfile();
        config.altProfile = new RandomAltitude();
        config.soilProfile = new WetSoilProfile();
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Polygon> plist = FinalMesh.getPolygonsList();
        String tileTag;
        String lakeNum;
        for(Structs.Polygon p: plist){
            tileTag = tileTagEx.extractValues(p.getPropertiesList());
            lakeNum = lakeTagEx.extractValues(p.getPropertiesList());
            if(tileTag.equals("lake")) {
                for(Integer i : p.getNeighborIdxsList()){
                    Structs.Polygon neighbor = plist.get(i);
                    String neighborTag = tileTagEx.extractValues(neighbor.getPropertiesList());
                    if(neighborTag.equals(tileTag)){
                        String neighborLNum = lakeTagEx.extractValues(neighbor.getPropertiesList());
                        assertEquals(Integer.parseInt(neighborLNum), Integer.parseInt(lakeNum));
                    }
                }
            }
        }
    }
    @Test
    public void checkThickness(){
        Configuration config = new Configuration();
        config.shapeObj = new Irregular();
        config.inputMesh = TestSuite.globalMesh;
        config.num_lakes = 0;
        config.num_aquifers = 0;
        config.num_rivers = 30;
        config.biomeProfile = new BaseBiomeProfile();
        config.altProfile = new RandomAltitude();
        config.soilProfile = new WetSoilProfile();
        Structs.Mesh FinalMesh = IslandGenerator.Generate(config);
        List<Structs.Segment> slist = FinalMesh.getSegmentsList();
        String tileTag;
        String thick;
        for(Structs.Segment s: slist){
            tileTag = edgeTagEx.extractValues(s.getPropertiesList());
            if(tileTag.equals("river")) {
                thick = thickEx.extractValues(s.getPropertiesList());
                assertNotEquals(thick, "null");
            }
        }
    }
}
