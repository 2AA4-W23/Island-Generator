import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Options options = new Options();
        options.addOption("mt", true, "decide the mesh type. default is grid.");
        options.addOption("lr", true, "decide how many times llyod relaxation occurs");


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String meshType = cmd.getOptionValue("mt");
        int num_iterations = 10;
        try {
            num_iterations = Integer.parseInt(cmd.getOptionValue("lr"));
        } catch (Exception e) {
            System.out.println("Not an integer. Using Default value of 10");
        }

        DotGen generator = new DotGen();
        Mesh myMesh;
        if(meshType == null || meshType.equals("grid")){
            myMesh = generator.generate();
            System.out.println(":grid created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
        else if(meshType.equals("irregular")){
            myMesh = generator.generateIrregular(num_iterations);
            System.out.println("Irregular Created");
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
        else {
            System.out.println("Illegal Argument");
        }

    }

}
