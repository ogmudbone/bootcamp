package com.anotheria.bootcamp.hash_map_III_concurrent_map;

import com.anotheria.bootcamp.DataCorruptionTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapDataCorruptionTest extends DataCorruptionTest {

    public ConcurrentMapDataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        super(keysQuantity, threadsQuantity, addNumber);
    }

    protected Map<String, Integer> instantiateMap(){
        return new ConcurrentHashMap<>();
    }

}
