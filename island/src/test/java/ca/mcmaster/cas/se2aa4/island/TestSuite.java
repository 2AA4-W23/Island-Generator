package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.generator.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.*;

public interface TestSuite {
    Structs.Mesh globalMesh = MeshCreator();
    Extractor tileTagEx = new TileTagExtractor();
    Extractor lakeTagEx = new LakeExtractor();
    Extractor altTagEx = new AltitudeExtractor();
    Extractor biomeTagEx = new BiomeExtractor();
    Extractor rgbTagEx = new RGBExtractor();
    static Structs.Mesh MeshCreator(){
        //Create Mesh
        MeshGenerator generator = new IrregularMeshGenerator();
        Structs.Mesh testMesh = generator.generate(3, 1000);
        return testMesh;
    }
}
