package com.anotheria.bootcamp.hash_map_II_synchronized;

import com.anotheria.bootcamp.DataCorruptionTest;
import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.TestManager;

public class SynchronizedTestManager extends TestManager{

    protected DataCorruptionTest getDataCorruptionTestObject(
            int keysQuantity, int threadsQuantity, int addNumber
    ){
        return new SynchronizedDataCorruptionTest(keysQuantity, threadsQuantity, addNumber);
    }

    protected RaceConditionTest getRaceConditionTestObject(
            int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks
    ){
        return new SynchronizedRaceConditionTest(threadsQuantity, keysQuantity, callbacks);
    }

}
