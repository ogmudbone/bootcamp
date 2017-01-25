package com.anotheria.bootcamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Test tries to invoke
 * errors due map resize
 * by putting to it new entries.
 * Map loadFactor sets to very small
 * and special keys with similar hash
 * were used, it forces map
 * to rebuild more often.
 */
public class RaceConditionTest {

    /**
     * Quantity of threads,
     * trying to fill map
     */
    private int threadsQuantity;
    /**
     * Quantity of new entries,
     * witch every thread add
     */
    private int keysQuantity;
    /**
     * Hash, witch every SpecialMapKey object have
     */
    private int keysHash;

    /**
     * How many time (in milliseconds)
     * loop check thread must wait,
     * before calling loop callback
     */
    private final static long LOOP_DETECT_TIME = 10000;

    /**
     * Callbacks, witch calls
     * in case of errors.
     */
    private RaceConditionTestCallbackInterface callbacks;

     /**
     * Quantity of thread, that working now
     */
    private volatile int threadsLeft;
    /**
     * Does any error occurred in
     * time of test execution.
     * Success callback call
     * depends on this variable
     */
    private volatile boolean hasError = false;

    /**
     * Calling this method
     * will signal, that error occurred
     * due test execution.
     * Called by test threads
     */
    private void signalError(){
        hasError = true;
    }
    /**
     * Decrease number of threads left.
     * Called in data corruption threads
     */
    private synchronized void decreaseThreadsLeft(){
        threadsLeft--;
    }



    public RaceConditionTest(int threadsQuantity, int keysQuantity, RaceConditionTestCallbackInterface callbacks) {
        this.threadsQuantity = threadsQuantity;
        this.keysQuantity = keysQuantity;
        this.callbacks = callbacks;
    }

    /**
     * Returns instance of new map,
     * witch will be used in data
     * corruption test.
     * Makes possible to use different
     * maps in child classes
     * @return new Map
     */
    protected Map<SpecialMapKey, Integer> instantiateMap(int size, float loadFactor){
        return new HashMap<>(size, loadFactor);
    }

    /**
     * Adds new entry.
     * Makes possible to use
     * thread locks on put operation
     * @param map map to add entry
     * @param key key of entry
     * @param value value of entry
     */
    protected void putToMap(Map<SpecialMapKey, Integer> map, SpecialMapKey key, Integer value){
        map.put(key, value);
    }

    /**
     * Starts race condition test
     */
    void run(){

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

    /**
     * Thread of race condition test.
     * Puts new entries in map
     * when starts.
     * Catches various exceptions
     * and invokes proper callbacks
     */
    private class RaceConditionRunnable implements Runnable{

        /**
         * Uses to distinguish
         * different threads
         * in console output
         */
        private int id;
        /**
         * Map for race condition test
         */
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
                // Occurs in TreeNode
                // method find(),
                // when map is rebuilding
                signalError();
                callbacks.onStackOverflow(id);
            }
            catch (OutOfMemoryError e){
                // Rebuilding map very often
                // consume lot of memory
                signalError();
                callbacks.onOutOfMemory(id);
            }
            catch (ClassCastException e){
                // Occurs when map is rebuilding
                signalError();
                callbacks.onClassCastException(id);
            }
            finally {
                decreaseThreadsLeft();
                loopCheckThread.interrupt();
            }

        }

    }

    /**
     * Thread to check infinite loop in
     * race condition thread.
     * Counts time since last put()
     * method of map call.
     * Invokes onInfiniteLoop()
     * callback, when time, passed
     * since last put() call
     * is bigger than LOOP_DETECT_TIME constant of
     * RaceConditionTest class.
     * Invokes callback again,
     * when time pass, more then LOOP_DETECT_TIME
     * since last invoke.
     */
    private class InfiniteLoopCheckRunnable implements Runnable{

        /**
         * Id of race condition thread,
         * witch launched this thread
         */
        private int masterId;
        /**
         * time in milliseconds, when
         * put() method was called
         */
        private long beginTime;

        private int messageRetry = 1;

        private InfiniteLoopCheckRunnable(RaceConditionRunnable master) {
            this.masterId = master.id;
            this.beginTime = System.currentTimeMillis();
        }

        /**
         * Resets call time count
         */
        private void startCount(){
            beginTime = System.currentTimeMillis();
        }

        /**
         * Checks time, that passed
         * since last put() call.
         * If this time bigger than LOOP_DETECT_TIME
         * constant, onInfiniteLoop() callback will invoked
         */
        private void checkTime(){
            if(System.currentTimeMillis() - beginTime > LOOP_DETECT_TIME * messageRetry) {
                messageRetry++;
                signalError();
                callbacks.onInfiniteLoop(
                        masterId,
                        System.currentTimeMillis() - beginTime
                );
            }
        }

        @Override
        public void run() {

            // Race condition thread interrupts this thread, when
            // it adds all its keys, or if exception occurred in thread
            while(!Thread.currentThread().isInterrupted()){
                checkTime();
            }

        }

    }

}
