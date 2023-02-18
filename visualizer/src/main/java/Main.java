import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // Extracting command line parameters
        String input = args[0];
        String output = args[1];
        Boolean debug = false;
        Boolean thickSet = false;
        int thick = 0;
        Boolean alphaSet = false;
        int alpha = 0;
        for(int i = 2; i < args.length; i++){
            if(args[i].equals("-X")) debug = true;
            else if(args[i].equals("-T")) {
                try {
                    thick = Integer.parseInt(args[i+1]);
                    thickSet = true;
                } catch(NumberFormatException e) {
                    System.out.println("Invalid argument for -T");
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("No argument provided for -T");
                }
            }
            else if(args[i].equals("-A")) {
                try {
                    alpha = Integer.parseInt(args[i+1]) % 256;
                    alphaSet = true;
                } catch(NumberFormatException e) {
                    System.out.println("Invalid argument for -A");
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("No argument provided for -A");
                }
            }
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
        renderer.render(aMesh, canvas, debug, thickSet, thick, alphaSet, alpha);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
    }
}
