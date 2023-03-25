package ca.mcmaster.cas.se2aa4.island.LakeGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

import java.util.List;
import java.util.Random;

public class AquiferAdder extends WaterBody{
    static Random rng = RandomNumber.getRandomInstance();

    public static List<Structs.Polygon> addAquifers(List<Structs.Polygon> newList, int numAqs){
        for(int i = 0; i < numAqs; i++){
            List<Structs.Polygon> tilesInAq = addBody(newList, rng);
            newList = addProperties(newList, tilesInAq, i + i, "aquifer", "aq_num");
        }
        return newList;
    }
}