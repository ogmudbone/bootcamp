package com.anotheria.bootcamp;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataCorruptionTest {

    private int keysQuantity;
    private int threadsQuantity;
    private int addNumber;

    private volatile int threadsLeft;
    private synchronized void decreaseThreadsLeft(){
        threadsLeft--;
    }

    public DataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        this.keysQuantity = keysQuantity;
        this.threadsQuantity = threadsQuantity;
        this.addNumber = addNumber;
    }

    protected Map<String, Integer> instantiateMap(){
        return new HashMap<>();
    }

    protected void addToMapToMap(Map<String, Integer> map, String key, int amount){
        map.put(key,
                map.get(key) + amount
                );
    }

    public Map<String, Integer> run(){

        threadsLeft = threadsQuantity;
        Map<String, Integer> testMap = instantiateMap();

        String[] mapKeys = new String[keysQuantity];
        Random random = new Random();

        for(int i = 0;i < keysQuantity;i++) {
            mapKeys[i] = new BigInteger(16, random).toString(32);
            testMap.put(mapKeys[i], 0);
        }

        Runnable corruptionRunnable = () -> {

            for(String key : mapKeys)
                addToMapToMap(testMap, key, addNumber);

            decreaseThreadsLeft();

        };

        for(int i = 0;i < threadsQuantity;i++)
            new Thread(corruptionRunnable).start();

        while(threadsLeft > 0){
            Thread.yield();
        }

        return testMap;

    }

}
