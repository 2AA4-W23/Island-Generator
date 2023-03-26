package ca.mcmaster.cas.se2aa4.island.SoilAbsorption;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class DrySoilProfile implements SoilProfile {

    Random rng = RandomNumber.getRandomInstance();
    @Override
    public int getAbsorptionLevel() {
        return rng.nextInt(2,4);
    }
    @Override
    public int getInitialHumidity(String tiletag) {
        if(tiletag.equals("lake"))return rng.nextInt(70,80);
        if(tiletag.equals("endor_lake"))return rng.nextInt(25,35);
        if(tiletag.equals("aquifer"))return rng.nextInt(50,60);
        if(tiletag.equals("land"))return rng.nextInt(10,15);
        return 0;
    }
    
}
