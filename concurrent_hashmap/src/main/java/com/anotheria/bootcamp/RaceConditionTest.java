package com.anotheria.bootcamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RaceConditionTest {

    private int threadsQuantity;
    private int keysQuantity;
    private int keysHash;

    private final static long LOOP_DETECT_TIME = 5000;

    private RaceConditionTestCallbackInterface callbacks;

    private volatile int threadsLeft = 0;
    private volatile boolean hasError = false;

    private void signalError(){
        hasError = true;
    }
    private synchronized void decreaseThreadsLeft(){
        threadsLeft--;
    }

    public RaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        this.threadsQuantity = threadsQuantity;
        this.keysQuantity = keysQuantity;
        this.callbacks = callbacks;
    }

    protected Map<SpecialMapKey, Integer> instantiateMap(int size, float loadFactor){
        return new HashMap<>(size, loadFactor);
    }

    protected void putToMap(Map<SpecialMapKey, Integer> map, SpecialMapKey key, Integer value){
        map.put(key, value);
    }

    public void run(){

        threadsLeft = threadsQuantity;
        Map<SpecialMapKey, Integer> testMap = instantiateMap(16, 0.0005f);
        keysHash = new Random().nextInt();

        testMap.clear();

        for(int i = 0;i < threadsQuantity;i++){
            new Thread(new RaceConditionRunnable(i, testMap)).start();
        }

        while(threadsLeft > 0){
            Thread.yield();
        }

        if(!hasError)
            callbacks.onSuccess(this);

    }

    private class RaceConditionRunnable implements Runnable{

        private int id;
        private Map<SpecialMapKey, Integer> testMap;

        private RaceConditionRunnable(int id, Map<SpecialMapKey, Integer> testMap) {
            this.id = id;
            this.testMap = testMap;
        }

        @Override
        public void run() {

            InfiniteLoopCheckRunnable loopCheckRunnable =  new InfiniteLoopCheckRunnable(this);
            Thread loopCheckThread = new Thread(loopCheckRunnable);
            loopCheckThread.start();

            try {
                for (int i = 0; i < keysQuantity; i++) {
                    loopCheckRunnable.startCount();
                    putToMap(testMap, new SpecialMapKey(keysHash), 0);
                }

            }catch (StackOverflowError e){
                signalError();
                callbacks.onStackOverflow(id);
            }
            catch (OutOfMemoryError e){
                signalError();
                callbacks.onOutOfMemory(id);
            }
            catch (ClassCastException e){
                signalError();
                callbacks.onClassCastException(id);
            }
            finally {
                decreaseThreadsLeft();
                loopCheckThread.interrupt();
            }

        }

    }

    private class InfiniteLoopCheckRunnable implements Runnable{

        private RaceConditionRunnable master;
        private long beginTime;
        private int messageRetry = 1;

        private InfiniteLoopCheckRunnable(RaceConditionRunnable master) {
            this.master = master;
            this.beginTime = System.currentTimeMillis();
        }

        private void startCount(){
            beginTime = System.currentTimeMillis();
        }

        private void checkTime(){
            if(System.currentTimeMillis() - beginTime > LOOP_DETECT_TIME * messageRetry) {
                messageRetry++;
                signalError();
                callbacks.onInfiniteLoop(
                        master.id,
                        System.currentTimeMillis() - beginTime
                );
            }
        }

        @Override
        public void run() {

            while(!Thread.currentThread().isInterrupted()){
                checkTime();
            }

        }

    }

}
