import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.island.Lagoon;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException{
        System.out.println("Hello world!");
        Options options = new Options();
        options.addOption("m", "mode", true, "Decide whether it is lagoon or normal generation");
        options.addOption("i", true, "Input Mesh");
        options.addOption("o", true, "Output Mesh");
        options.addOption("s", "shape", true, "Decide Shape of the mesh");
        options.addOption("l", "lakes", true, "Number of lakes to be generated");
        options.addOption("aq", "aquifers", true, "Number of aquifers to be generated");
        options.addOption("a", "altitude", true, "Decide altitude type (flat, volcanic, canyon, randomized");
        options.addOption("h", "help", false, "Show instructions for control variables");
        options.addOption("r", "rivers", true, "Number of rivers to be generated");
        options.addOption("b", "biomes", true, "Decide Biomes type selected (Whittaker Diagram)");
        options.addOption("se", "seed", true, "Input island seed for recreation of island.");
        options.addOption("sa", "soil", true, "Soil Absorption profile (wet, dry)");
        options.addOption("c", "cities", true, "Number of cities to spawn");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Generator", options);
            System.exit(0);
        }

        String seed = cmd.getOptionValue("seed");
        if(seed == null){
            RandomNumber.getRandomInstance();
            RandomNumber.setSeed();
        } else {
            try{
                RandomNumber.getRandomInstance();
                System.out.println(seed);
                System.out.println(seed.getClass());
                RandomNumber.setSeed(Long.parseLong(seed));
            } catch (NumberFormatException e){
                System.out.println("Invalid Seed. Proceeding with Random seed.");
                RandomNumber.getRandomInstance();
                RandomNumber.setSeed();
            }
        }
        String mode = cmd.getOptionValue("mode");
        Structs.Mesh inputMesh = new MeshFactory().read(cmd.getOptionValue("i"));
        Structs.Mesh outputMesh;

        Configuration config = new Configuration();
        config.generateConfig(args, options);

        if(mode == null || !(mode.equals("lagoon"))){
            outputMesh = IslandGenerator.Generate(config);
        } else {
            Lagoon lagoon = new Lagoon();
            outputMesh =  lagoon.LagoonTerrain(inputMesh);
        }
        MeshFactory factory = new MeshFactory();
        factory.write(outputMesh, cmd.getOptionValue("o"));
    }

}