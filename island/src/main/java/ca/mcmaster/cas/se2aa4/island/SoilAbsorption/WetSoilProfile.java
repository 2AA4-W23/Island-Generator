package ca.mcmaster.cas.se2aa4.island.SoilAbsorption;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class WetSoilProfile implements SoilProfile {

    Random rng = RandomNumber.getRandomInstance();

    @Override
    public int getAbsorptionLevel() {
        return rng.nextInt(8,10);
    }
    @Override
    public int getInitialHumidity(String tiletag) {
        if(tiletag.equals("lake"))return rng.nextInt(90,100);
        if(tiletag.equals("endor_lake"))return rng.nextInt(40,50);
        if(tiletag.equals("aquifer"))return rng.nextInt(70,80);
        if(tiletag.equals("land"))return rng.nextInt(20,25);
        return 0;
    }
}
