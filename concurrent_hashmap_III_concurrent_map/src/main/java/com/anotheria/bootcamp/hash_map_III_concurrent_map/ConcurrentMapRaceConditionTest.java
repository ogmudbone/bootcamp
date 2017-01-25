package com.anotheria.bootcamp.hash_map_III_concurrent_map;

import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.SpecialMapKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapRaceConditionTest extends RaceConditionTest{

    public ConcurrentMapRaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        super(threadsQuantity, keysQuantity, callbacks);
    }

    protected Map<SpecialMapKey, Integer> instantiateMap(int size, float loadFactor){
        return new ConcurrentHashMap<>(size, loadFactor);
    }

}
