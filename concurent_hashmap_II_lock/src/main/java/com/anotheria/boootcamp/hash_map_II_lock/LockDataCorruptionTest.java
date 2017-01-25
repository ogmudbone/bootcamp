package com.anotheria.boootcamp.hash_map_II_lock;

import com.anotheria.bootcamp.DataCorruptionTest;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDataCorruptionTest extends DataCorruptionTest {

    private static Lock lock = new ReentrantLock();

    LockDataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        super(keysQuantity, threadsQuantity, addNumber);
    }

    @Override
    protected void addToMapToMap(Map<String, Integer> map, String key, int amount){
        lock.lock();
            super.addToMapToMap(map, key, amount);
        lock.unlock();
    }

}
