package ca.mcmaster.cas.se2aa4.island.Shape;

import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

import java.util.Random;

public interface Shape{
    public boolean contains(double x, double y);
    public void create();
    Random rng = RandomNumber.getRandomInstance();
}
