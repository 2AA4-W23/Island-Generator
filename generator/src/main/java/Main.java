import ca.mcmaster.cas.se2aa4.a2.generator.Generate;
import ca.mcmaster.cas.se2aa4.a2.generator.GridMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, ExecutionControl.NotImplementedException {
        Generate.runGenerator(args);
    }
}
