package ca.mcmaster.cas.se2aa4.island.SoilAbsorption;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class WetSoilProfile implements SoilProfile {

    Random rng = RandomNumber.getRandomInstance();
    private static final int maxSaturation = 15;

    @Override
    public int getAbsorptionLevel() {
        return rng.nextInt(8,10);
    }
    @Override
    public int getMaxSaturation() {
        // TODO Auto-generated method stub
        return maxSaturation;
    }
}
