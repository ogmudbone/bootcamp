import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class DataCorruptionTest {

    private int keysQuantity;
    private int threadsQuantity;
    private int addNumber;

    private volatile int threadsLeft;
    private synchronized void decreaseThreadsLeft(){
        threadsLeft--;
    }

    public DataCorruptionTest(int keysQuantity, int threadsQuantity, int addNumber) {
        this.keysQuantity = keysQuantity;
        this.threadsQuantity = threadsQuantity;
        this.addNumber = addNumber;
    }

    public HashMap<String, Integer> run(){

        threadsLeft = threadsQuantity;
        HashMap<String, Integer> testMap = new HashMap<>();

        String[] mapKeys = new String[keysQuantity];
        Random random = new Random();

        for(int i = 0;i < keysQuantity;i++) {
            mapKeys[i] = new BigInteger(16, random).toString(32);
            testMap.put(mapKeys[i], 0);
        }

        Runnable corruptionRunnable = () -> {

            for(String key : mapKeys)
                testMap.put(key, testMap.get(key) + addNumber);

            decreaseThreadsLeft();

        };

        for(int i = 0;i < threadsQuantity;i++)
            new Thread(corruptionRunnable).start();

        while(threadsLeft > 0){
            Thread.yield();
        }

        return testMap;

    }

}
