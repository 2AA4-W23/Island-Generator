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

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String mode = cmd.getOptionValue("mode");
        if(mode == null || !(mode.equals("lagoon"))){
            Structs.Mesh aMesh = new MeshFactory().read(cmd.getOptionValue("i"));
            String shape = cmd.getOptionValue("shape");
            Structs.Mesh myMesh = IslandGenerator.Generate(aMesh, shape);
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, cmd.getOptionValue("o"));
        } else {
            Structs.Mesh aMesh = new MeshFactory().read(cmd.getOptionValue("i"));
            Lagoon lagoon = new Lagoon();
            Structs.Mesh myMesh =  lagoon.LagoonTerrain(aMesh);
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, cmd.getOptionValue("o"));
        }
    }

}