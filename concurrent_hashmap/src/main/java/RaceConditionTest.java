import java.util.HashMap;
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

    public void run(){

        threadsLeft = threadsQuantity;
        HashMap<SpecialMapKey, Integer> testMap = new HashMap<>(1, 0.000001f);
        keysHash = new Random().nextInt();

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
        private HashMap<SpecialMapKey, Integer> testMap;

        private RaceConditionRunnable(int id, HashMap<SpecialMapKey, Integer> testMap) {
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
                    testMap.put(new SpecialMapKey(keysHash), 0);
                }

            }catch (StackOverflowError e){
                signalError();
                callbacks.onStackOverflow(id);
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
