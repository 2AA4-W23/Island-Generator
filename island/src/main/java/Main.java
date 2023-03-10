import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Lagoon;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
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
        Random rng = new Random();

        String lakeArg = cmd.getOptionValue("lakes");
        int numLakes;
        if(lakeArg != null) numLakes = Integer.parseInt(lakeArg);
        else numLakes = rng.nextInt(10);

        Configuration config = new Configuration();
        config.generateConfig(args, options);

        if(mode == null || !(mode.equals("lagoon"))){
            String shape = cmd.getOptionValue("shape");
            outputMesh = IslandGenerator.Generate(config);
        } else {
            Lagoon lagoon = new Lagoon();
            outputMesh =  lagoon.LagoonTerrain(inputMesh);
        }
        MeshFactory factory = new MeshFactory();
        factory.write(outputMesh, cmd.getOptionValue("o"));

    }

}