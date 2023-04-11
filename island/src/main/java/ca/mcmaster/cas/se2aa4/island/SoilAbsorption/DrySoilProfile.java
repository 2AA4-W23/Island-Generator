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
        if(tiletag.equals("lake"))return rng.nextInt(40,50);
        if(tiletag.equals("endor_lake"))return rng.nextInt(10,20);
        if(tiletag.equals("aquifer"))return rng.nextInt(30,40);
        if(tiletag.equals("land"))return rng.nextInt(3,6);
        return 0;
    }
    
}
