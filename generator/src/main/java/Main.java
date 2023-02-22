import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Options options = new Options();
        options.addOption("mt", true, "decide the mesh type. default is grid.");
        options.addOption("lr", true, "decide how many times llyod relaxation occurs. defaults to 10.");
        options.addOption("np", true, "decide how many polygons should be generated. defaults to randomize.");
        options.addOption("h","help", false, "print instructions for tool interaction");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Generator", options);
            System.exit(0);
        }

        String meshType = cmd.getOptionValue("mt");
        int num_iterations = 10;
        Random bag = new Random();
        int numPolygons = bag.nextInt(300, 800);
        try {
            num_iterations = Integer.parseInt(cmd.getOptionValue("lr"));
        } catch (Exception e) {
            System.out.println("Not an integer. Using Default value of 10");
        }
        try {
            numPolygons = Integer.parseInt(cmd.getOptionValue("np"));
        } catch (Exception e) {
            System.out.println("Not an integer. Using Random Value");
        }


        DotGen generator = new DotGen();
        Mesh myMesh;
        if(meshType == null || meshType.equals("grid")){
            myMesh = generator.generate();
            System.out.println(":grid created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
            System.out.println(numPolygons);
        }
        else if(meshType.equals("irregular")){
            myMesh = generator.generateIrregular(num_iterations, numPolygons);
            System.out.println("Irregular Created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
            System.out.println(numPolygons);
        }
        else {
            System.out.println("Illegal Argument");
        }
        System.out.println(numPolygons);
    }

}
