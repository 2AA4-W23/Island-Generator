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
        return maxSaturation;
    }
    @Override
    public int getInitialHumidity(String tiletag) {
        if(tiletag.equals("lake"))return rng.nextInt(90,100);
        if(tiletag.equals("aquifer"))return rng.nextInt(70,80);
        if(tiletag.equals("river"))return rng.nextInt(5,10);
        return 0;
    }
}
