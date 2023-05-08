package ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator;

import java.util.Random;

public class RandomNumber {
    private static Random randomInstance = null;
    public static long seed;

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
            long seed1 = System.currentTimeMillis();
            randomInstance.setSeed(seed1);
            System.out.println("Random Seed is: "+ seed1);
            seed = seed1;
        }
    }
    public static void setSeed(long seed1){
        if(randomInstance == null){
            throw new RuntimeException("Not possible Instantiate instance first.");
        } else{
            randomInstance.setSeed(seed1);
            System.out.println("Seed is: "+ seed1);
        }
        seed = seed1;
    }
}
