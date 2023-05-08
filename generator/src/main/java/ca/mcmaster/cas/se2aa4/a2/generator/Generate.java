package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class Generate {
    public static void runGenerator(String[] args) throws IOException, org.apache.commons.cli.ParseException{
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
        int numPolygons = bag.nextInt(1500, 3000);
        num_iterations = Integer.parseInt(cmd.getOptionValue("lr"));
        if(num_iterations <= 0){
            num_iterations = 1;
        }
        numPolygons = Integer.parseInt(cmd.getOptionValue("np"));
        System.out.println(numPolygons);
        Mesh myMesh;
        if(meshType == null || meshType.equals("grid")){
            MeshGenerator gridgen = new GridMeshGenerator();
            myMesh = gridgen.generate();
            System.out.println("Grid created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
        else if(meshType.equals("irregular")){
            MeshGenerator irregulargen = new IrregularMeshGenerator();
            myMesh = irregulargen.generate(num_iterations, numPolygons);
            System.out.println("Irregular Created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
            System.out.println(numPolygons);
        }
        else {
            System.out.println("Illegal Argument");
        }
    }
}
