package com.anotheria.bootcamp;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Test tries to corrupt
 * data in map by adding number to map entry
 * in many threads.
 */
public class DataCorruptionTest {

    /**
     * Quantity of keys in map
     */
    private int keysQuantity;
    /**
     * Quantity of threads, witch
     * simultaneously adds number to
     * all map entries
     */
    private int threadsQuantity;
    /**
     * Number, witch
     * threads will add to map entry
     */
    private int addNumber;

    /**
     * Quantity of thread, that working now
     */
    private volatile int threadsLeft;

    /**
     * Decrease number of threads left.
     * Called in data corruption threads
     */
    private synchronized void decreaseThreadsLeft(){
        threadsLeft--;
    }



    public DataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        this.keysQuantity = keysQuantity;
        this.threadsQuantity = threadsQuantity;
        this.addNumber = addNumber;
    }

    /**
     * Returns instance of new map,
     * witch will be used in data
     * corruption test.
     * Makes possible to use different
     * maps in child classes
     * @return new Map
     */
    protected Map<String, Integer> instantiateMap(){
        return new HashMap<>();
    }

    /**
     * Adds number to map entry.
     * Makes possible to use thread locks
     * on map operations
     * @param map number adds in this map
     * @param key key of entry
     * @param amount addition number
     */
    protected void addToMapToMap(Map<String, Integer> map, String key, int amount){
        map.put(key,
                map.get(key) + amount
                );
    }

    /**
     * Starts data corruption test
     * @return map, witch passed through
     *  data corruption test
     */
    Map<String, Integer> run(){

        threadsLeft = threadsQuantity;
        Map<String, Integer> testMap = instantiateMap();

        String[] mapKeys = new String[keysQuantity];
        Random random = new Random();

        for(int i = 0;i < keysQuantity;i++) {
            mapKeys[i] = new BigInteger(16, random).toString(32);
            testMap.put(mapKeys[i], 0);
        }

        Runnable corruptionRunnable = () -> {

            // Adds number to every entry in map once
            for(String key : mapKeys)
                addToMapToMap(testMap, key, addNumber);

            // Signal, that this thread is finish working
            decreaseThreadsLeft();

        };

        for(int i = 0;i < threadsQuantity;i++)
            new Thread(corruptionRunnable).start();

        // wait, until all threads done
        while(threadsLeft > 0){
            Thread.yield();
        }

        return testMap;

    }

}
