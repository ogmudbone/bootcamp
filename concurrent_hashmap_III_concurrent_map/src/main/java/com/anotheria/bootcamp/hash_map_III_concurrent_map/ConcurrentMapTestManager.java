package com.anotheria.bootcamp.hash_map_III_concurrent_map;

import com.anotheria.bootcamp.DataCorruptionTest;
import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.TestManager;

public class ConcurrentMapTestManager extends TestManager{

    protected DataCorruptionTest getDataCorruptionTestObject(
            int keysQuantity, int threadsQuantity, int addNumber
    ){
        return new ConcurrentMapDataCorruptionTest(keysQuantity, threadsQuantity, addNumber);
    }

    protected RaceConditionTest getRaceConditionTestObject(
            int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks
    ){
        return new ConcurrentMapRaceConditionTest(threadsQuantity, keysQuantity, callbacks);
    }

}
