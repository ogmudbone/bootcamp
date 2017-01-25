package com.anotheria.bootcamp.hash_map_II_synchronized_map;

import com.anotheria.bootcamp.DataCorruptionTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMapDataCorruptionTest extends DataCorruptionTest {

    public SynchronizedMapDataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        super(keysQuantity, threadsQuantity, addNumber);
    }

    protected Map<String, Integer> instantiateMap(){
        return Collections.synchronizedMap(new HashMap<String, Integer>());
    }

}
