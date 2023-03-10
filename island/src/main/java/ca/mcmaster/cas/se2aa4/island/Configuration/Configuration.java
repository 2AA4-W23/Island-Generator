package ca.mcmaster.cas.se2aa4.island.Configuration;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Shape.Circle;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Configuration {
    public Shape shapeObj;
    public Structs.Mesh inputMesh;
    public void generateConfig(String args[], Options options) throws ParseException, FileNotFoundException {
        ArrayList classObj = new ArrayList<>();


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        String inputM = cmd.getOptionValue("i");
        String shape = cmd.getOptionValue("shape");

//        String inputMeshPath = "../";
//        inputM = inputMeshPath + inputM;


        String shapePath = "ca.mcmaster.cas.se2aa4.island.Shape.";

        shape = shape.toLowerCase();
        shape = shapePath + shape.substring(0, 1).toUpperCase() + shape.substring(1);
        try {
            this.shapeObj = (Shape) Class.forName(shape).getDeclaredConstructor().newInstance();
            System.out.println("Set");
        } catch (Exception e) {
            this.shapeObj = new Irregular();
        }
        try {
            this.inputMesh = new MeshFactory().read(inputM);
            System.out.println(inputMesh.getClass());
        } catch (Exception e) {
            throw new FileNotFoundException();
        }

    }
}

