import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Lagoon;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("Hello world!");
        Options options = new Options();
        options.addOption("m", "mode", true, "Decide whether it is lagoon or normal generation");
        options.addOption("i", true, "Input Mesh");
        options.addOption("o", true, "Output Mesh");
        options.addOption("s", "shape", true, "Decide Shape of the mesh");
        options.addOption("l", "lakes", true, "Number of lakes to be generated");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String mode = cmd.getOptionValue("mode");
        Structs.Mesh inputMesh = new MeshFactory().read(cmd.getOptionValue("i"));
        Structs.Mesh outputMesh;
        int lakes = Integer.parseInt(cmd.getOptionValue("lakes"));
        if(mode == null || !(mode.equals("lagoon"))){
            String shape = cmd.getOptionValue("shape");
            outputMesh = IslandGenerator.Generate(inputMesh, shape);
        } else {
            Lagoon lagoon = new Lagoon();
            outputMesh =  lagoon.LagoonTerrain(inputMesh);
        }
        MeshFactory factory = new MeshFactory();
        factory.write(outputMesh, cmd.getOptionValue("o"));
    }

}