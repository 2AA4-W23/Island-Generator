package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.generator.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.Extractors.*;
import ca.mcmaster.cas.se2aa4.island.Shape.Circle;
import ca.mcmaster.cas.se2aa4.island.SoilAbsorption.WetSoilProfile;

public interface TestSuite {
    Structs.Mesh globalMesh = MeshCreator();
    Extractor tileTagEx = new TileTagExtractor();
    Extractor edgeTagEx = new EdgeTagExtractor();
    Extractor lakeTagEx = new LakeExtractor();
    Extractor aqTagEx = new AquiferNumExtractor();
    Extractor riverExtractor = new RiverExtractor();
    Extractor altTagEx = new AltitudeExtractor();
    Extractor biomeTagEx = new BiomeExtractor();
    Extractor rgbTagEx = new RGBExtractor();
    Extractor thickEx = new RiverThicknessExtractor();
    Configuration config = setConfig();
    static Structs.Mesh MeshCreator(){
        //Create Mesh
        MeshGenerator generator = new IrregularMeshGenerator();
        Structs.Mesh testMesh = generator.generate(3, 1000);
        return testMesh;
    }
    static Configuration setConfig(){
        Configuration c = new Configuration();
        c.shapeObj = new Circle();
        c.inputMesh = globalMesh;
        c.altProfile = new RandomAltitude();
        c.num_aquifers = 2;
        c.biomeProfile = new BaseBiomeProfile();
        c.soilProfile = new WetSoilProfile();
        return c;
    }
}
