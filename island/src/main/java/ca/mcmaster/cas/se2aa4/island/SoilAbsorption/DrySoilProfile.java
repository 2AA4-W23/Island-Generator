package ca.mcmaster.cas.se2aa4.island.SoilAbsorption;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class DrySoilProfile implements SoilProfile {

    Random rng = RandomNumber.getRandomInstance();
    private static final int maxSaturation = 6;
    @Override
    public int getAbsorptionLevel() {
        return rng.nextInt(2,4);
    }
    @Override
    public int getMaxSaturation() {
        return maxSaturation;
    }
    
}
