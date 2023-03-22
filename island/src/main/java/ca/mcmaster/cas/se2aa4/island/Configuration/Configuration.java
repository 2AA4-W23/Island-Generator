package ca.mcmaster.cas.se2aa4.island.Configuration;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Altitude.AltitudeProfile;
import ca.mcmaster.cas.se2aa4.island.Altitude.RandomAltitude;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Biomes.BiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Shape.Irregular;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Configuration {
    public Shape shapeObj;
    public Structs.Mesh inputMesh;
    public int num_lakes;
    public AltitudeProfile altProfile;
    public BiomeProfile biomeProfile;
    private Random rng = new Random();
    public int num_aquifers;
    public int num_rivers;

    public void generateConfig(String args[], Options options) throws ParseException, FileNotFoundException {
        ArrayList classObj = new ArrayList<>();


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        String inputM = cmd.getOptionValue("i");
        String shape = cmd.getOptionValue("shape");
        String lakes = cmd.getOptionValue("lakes");
        String aquifers = cmd.getOptionValue("aquifers");
        String rivers = cmd.getOptionValue("rivers");

        String shapePath = "ca.mcmaster.cas.se2aa4.island.Shape.";

        if(shape!= null) {
            shape = shape.toLowerCase();
            shape = shapePath + shape.substring(0, 1).toUpperCase() + shape.substring(1);
        }
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

        try{
            this.num_lakes = Math.min(Integer.parseInt(lakes), 10);
            System.out.println("lakes set");
        } catch (Exception e) {
            this.num_lakes = rng.nextInt(2, 10);
        }

        try{
            this.num_aquifers = Math.min(Integer.parseInt(aquifers), 10);
            System.out.println("aquifers set");
        } catch (Exception e) {
            this.num_aquifers = rng.nextInt(2,10);
        }

        try{
            this.num_rivers = Math.min(Integer.parseInt(rivers), 30);
            System.out.println("rivers set");
        } catch (Exception e) {
            this.num_rivers = rng.nextInt(2,30);
        }

        String altPath = "ca.mcmaster.cas.se2aa4.island.Altitude.";
        String alt = cmd.getOptionValue("a");

        if(alt != null) {
            alt = alt.toLowerCase();
            alt = altPath + alt.substring(0, 1).toUpperCase() + alt.substring(1) + "Altitude";
        }
        try {
            this.altProfile = (AltitudeProfile) Class.forName(alt).getDeclaredConstructor().newInstance();
            System.out.println("SetAlt");
        } catch (Exception e){
            this.altProfile = new RandomAltitude();
            System.out.println("Catchalt");
        }

        String biomesPath = "ca.mcmaster.cas.se2aa4.island.Biomes.";
        String biomes = cmd.getOptionValue("biomes");
        if(biomes != null) {
            biomes = biomes.toLowerCase();
            biomes = biomesPath + biomes.substring(0, 1).toUpperCase() + biomes.substring(1) + "BiomeProfile";
        }
        try {
            this.biomeProfile = (BiomeProfile) Class.forName(biomes).getDeclaredConstructor().newInstance();
            System.out.println("SetBiomes");
        } catch (Exception e){
            this.biomeProfile = new BaseBiomeProfile();
            System.out.println("Catchbiomes");
        }
        System.out.println("cont");
    }
}

