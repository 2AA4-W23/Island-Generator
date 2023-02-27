package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.Random;

public interface MeshGenerator {
    public int width = 500;
    public int height = 500;
    public int square_size = 20;

    Random bag = new Random();
    public void SetInitialValues(int num_iter, int numP);
    public Mesh generate();
}
