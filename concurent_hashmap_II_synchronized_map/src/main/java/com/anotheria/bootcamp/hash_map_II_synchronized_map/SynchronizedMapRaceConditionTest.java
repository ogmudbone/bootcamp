package com.anotheria.bootcamp.hash_map_II_synchronized_map;

import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.SpecialMapKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMapRaceConditionTest extends RaceConditionTest{

    public SynchronizedMapRaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        super(threadsQuantity, keysQuantity, callbacks);
    }

    protected Map<SpecialMapKey, Integer> instantiateMap(int size, float loadFactor){
        return Collections.synchronizedMap(new HashMap<SpecialMapKey, Integer>(size, loadFactor));
    }

}
