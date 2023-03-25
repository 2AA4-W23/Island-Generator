package ca.mcmaster.cas.se2aa4.island.SoilAbsorption;

public interface SoilProfile {
    public int getAbsorptionLevel();
    public int getMaxSaturation();
    public int getInitialHumidity(String tiletag);
}
