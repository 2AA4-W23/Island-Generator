import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
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

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String mode = cmd.getOptionValue("mode");
        if(mode == null || !(mode.equals("lagoon"))){
            System.out.println("Not lagoon mode");
        } else {
            Structs.Mesh aMesh = new MeshFactory().read(cmd.getOptionValue("i"));
            Lagoon lagoon = new Lagoon();
            Structs.Mesh myMesh =  lagoon.LagoonTerrain(aMesh);
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, cmd.getOptionValue("o"));
        }
    }

}