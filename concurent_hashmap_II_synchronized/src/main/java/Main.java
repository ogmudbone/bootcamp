import com.anotheria.bootcamp.TestManager;
import com.anotheria.bootcamp.hash_map_II_synchronized.SynchronizedTestManager;

public class Main {

    public static void main(String[] args){
        TestManager testManager = new SynchronizedTestManager();
        testManager.dataCorruptionTest();
        testManager.raceConditionTest();
    }

}
