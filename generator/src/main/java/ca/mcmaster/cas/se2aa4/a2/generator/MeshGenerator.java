package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.Random;

public interface MeshGenerator {
    int width = 500;
    int height = 500;
    int square_size = 20;
    Extractor pavgExtractor = new PropertyAverageExtractor();
    Extractor plavgExtractor = new ListPropertyAverageExtractor();
    Random bag = new Random();
    public Mesh generate();
    public Mesh generate(int num_iter, int numP);
}
