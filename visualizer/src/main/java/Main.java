import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.OBJBuilder;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;
import org.apache.commons.cli.*;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        // Extracting command line parameters
        Options options = new Options();
        options.addOption("X", false, "Debug Mode Parameter");
        options.addOption("Alt", false, "Debug Mode Parameter to see altitude heatmap");
        options.addOption("Humid", false, "Enables Humidity mode for heatmap.");
        options.addOption("T", true, "Thickness of Segments. If empty then random.");
        options.addOption("A", true, "Transparencies of parts of the mesh. If empty then random.");
        options.addOption("h","help", false, "print instructions for tool interaction");
        options.addOption("O","obj", false, "creates an additional obj file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String input = args[0];
        String output = args[1];
        Boolean debug = false;
        Boolean thickSet = false;
        int thick = 0;
        Boolean alphaSet = false;
        int alpha = 0;

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Generator", options);
            System.exit(0);
        }

        if(cmd.hasOption("X")){
            debug = true;
        }
        boolean alt;
        boolean humid;
        if(cmd.hasOption("Alt")){
            alt = true;
        } else {
            alt = false;
        }
        if(cmd.hasOption("Humid")){
            humid = true;
        } else {
            humid = false;
        }
        try {
            alpha = Integer.parseInt(cmd.getOptionValue("A"));
            if(alpha <= 255 || alpha >= 0){
                alphaSet = true;
            } else {
                System.out.println("Alpha Value not in range");
            }
        } catch (Exception e) {
            System.out.println("Alpha value not valid. Proceeding with Default");
        }
        try {
            thick = Integer.parseInt(cmd.getOptionValue("T"));
            if(alpha <= 7 || alpha >= 2){
                thickSet = true;
            } else {
                System.out.println("Thickness Value not in expected range");
            }
        } catch (Exception e) {
            System.out.println("Thick value not valid. Proceeding with default.");
        }

        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvas.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));
        GraphicRenderer renderer = new GraphicRenderer();
        // Painting the mesh on the canvas
        renderer.render(aMesh, canvas, debug, thickSet, thick, alphaSet, alpha, alt, humid);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
        if(cmd.hasOption("O")){
            OBJBuilder ob = new OBJBuilder();
            ob.generateOBJ(aMesh);
        }
    }
}
