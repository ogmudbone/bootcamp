import com.anotheria.boootcamp.hash_map_II_lock.LockTestManager;
import com.anotheria.bootcamp.TestManager;

public class Main {

    public static void main(String[] args){
        TestManager testManager = new LockTestManager();
        testManager.dataCorruptionTest();
        testManager.raceConditionTest();
    }

}
