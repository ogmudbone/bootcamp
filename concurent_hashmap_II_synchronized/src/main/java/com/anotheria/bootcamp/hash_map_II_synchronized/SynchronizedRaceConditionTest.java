package com.anotheria.bootcamp.hash_map_II_synchronized;

import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.SpecialMapKey;

import java.util.Map;

public class SynchronizedRaceConditionTest extends RaceConditionTest{

    public SynchronizedRaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        super(threadsQuantity, keysQuantity, callbacks);
    }

    @Override
    protected synchronized void putToMap(Map<SpecialMapKey, Integer> map, SpecialMapKey key, Integer value) {
        super.putToMap(map, key, value);
    }

}
