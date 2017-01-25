package com.anotheria.boootcamp.hash_map_II_lock;

import com.anotheria.bootcamp.DataCorruptionTest;
import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.TestManager;

public class LockTestManager extends TestManager{

    protected DataCorruptionTest getDataCorruptionTestObject(
            int keysQuantity, int threadsQuantity, int addNumber
    ){
        return new LockDataCorruptionTest(keysQuantity, threadsQuantity, addNumber);
    }

    protected RaceConditionTest getRaceConditionTestObject(
            int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks
    ){
        return new LockRaceConditionTest(threadsQuantity, keysQuantity, callbacks);
    }

}
