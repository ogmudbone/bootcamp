import com.anotheria.bootcamp.TestManager;
import com.anotheria.bootcamp.hash_map_III_concurrent_map.ConcurrentMapTestManager;

public class Main {

    public static void main(String[] args){
        TestManager testManager = new ConcurrentMapTestManager();
        testManager.dataCorruptionTest();
        testManager.raceConditionTest();
    }

}
