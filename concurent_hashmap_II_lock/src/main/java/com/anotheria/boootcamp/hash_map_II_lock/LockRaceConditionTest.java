package com.anotheria.boootcamp.hash_map_II_lock;

import com.anotheria.bootcamp.RaceConditionTest;
import com.anotheria.bootcamp.RaceConditionTestCallbackInterface;
import com.anotheria.bootcamp.SpecialMapKey;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockRaceConditionTest extends RaceConditionTest{

    private volatile static Lock lock = new ReentrantLock();

    public LockRaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        super(threadsQuantity, keysQuantity, callbacks);
    }

    @Override
    protected void putToMap(Map<SpecialMapKey, Integer> map, SpecialMapKey key, Integer value) {
        lock.lock();
            map.put(key, value);
        lock.unlock();
    }

}
