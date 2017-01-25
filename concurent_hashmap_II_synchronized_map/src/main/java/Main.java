import com.anotheria.bootcamp.TestManager;
import com.anotheria.bootcamp.hash_map_II_synchronized_map.SynchronizedMapTestManager;

public class Main {

    public static void main(String[] args){
        TestManager testManager = new SynchronizedMapTestManager();
        testManager.dataCorruptionTest();
        testManager.raceConditionTest();
    }

}
