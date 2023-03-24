package ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator;

import java.util.Random;

public class RandomNumber {
    private static Random randomInstance = null;
    public static Random getRandomInstance(){
        if(randomInstance == null) {
            randomInstance = new Random();
        }
        return randomInstance;
    }
    public static void setSeed(){
        if(randomInstance == null){
            throw new RuntimeException("Not possible Instantiate instance first.");
        } else{
            long seed = System.currentTimeMillis();
            randomInstance.setSeed(seed);
            System.out.println("Random Seed is: "+ seed);
        }
    }
    public static void setSeed(int seed){
        if(randomInstance == null){
            throw new RuntimeException("Not possible Instantiate instance first.");
        } else{
            randomInstance.setSeed(seed);
            System.out.println("Random Seed is: "+ seed);
        }
    }
}
