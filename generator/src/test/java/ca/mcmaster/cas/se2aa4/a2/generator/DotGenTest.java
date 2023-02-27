package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DotGenTest {

    @Test
    public void meshIsNotNull() {
        IrregularMeshGenerator generator = new IrregularMeshGenerator();
        Structs.Mesh aMesh = generator.generate(10, 200);

        GridMeshGenerator generator1 = new GridMeshGenerator();
        Structs.Mesh bMesh = generator1.generate();
        assertNotNull(aMesh);
        assertNotNull(bMesh);
    }

}
