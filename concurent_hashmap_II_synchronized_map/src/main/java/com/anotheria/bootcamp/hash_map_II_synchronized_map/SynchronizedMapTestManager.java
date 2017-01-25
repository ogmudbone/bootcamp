package com.anotheria.bootcamp.hash_map_II_synchronized_map;

import com.anotheria.bootcamp.DataCorruptionTest;
import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.TestManager;

public class SynchronizedMapTestManager extends TestManager{

    protected DataCorruptionTest getDataCorruptionTestObject(
            int keysQuantity, int threadsQuantity, int addNumber
    ){
        return new SynchronizedMapDataCorruptionTest(keysQuantity, threadsQuantity, addNumber);
    }

    protected RaceConditionTest getRaceConditionTestObject(
            int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks
    ){
        return new SynchronizedMapRaceConditionTest(threadsQuantity, keysQuantity, callbacks);
    }

}
