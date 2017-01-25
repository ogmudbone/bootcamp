package com.anotheria.bootcamp.hash_map_II_synchronized;

import com.anotheria.bootcamp.DataCorruptionTest;

import java.util.Map;

public class SynchronizedDataCorruptionTest extends DataCorruptionTest {

    public SynchronizedDataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        super(keysQuantity, threadsQuantity, addNumber);
    }

    protected synchronized void addToMapToMap(Map<String, Integer> map, String key, int amount){
        super.addToMapToMap(map, key, amount);
    }

}
