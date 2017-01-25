import com.anotheria.bootcamp.TestManager;

public class Main {

    public static void main(String[] args){
        TestManager testManager = new TestManager();
        testManager.dataCorruptionTest();
        testManager.raceConditionTest();
    }

}
