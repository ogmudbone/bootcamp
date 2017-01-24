import java.util.Map;

public class TestManager {

    private static final int CORRUPTION_TEST_THREADS_QUANTITY = 1000;
    private static final int CORRUPTION_TEST_KEYS_QUANTITY    = 10;
    private static final int CORRUPTION_TEST_ADD_NUMBER       = 10;

    private static final int RACE_CONDITION_TEST_THREADS_QUANTITY = 100;
    private static final int RACE_CONDITION_TEST_KEYS_QUANTITY    = 1000;

    public static void dataCorruptionTest(){

        System.out.println("TEST #1:");
        System.out.println("Every thread increase all hash map integer values by same number.");

        System.out.println();
        System.out.println("Threads quantity : " + CORRUPTION_TEST_THREADS_QUANTITY);
        System.out.println("Keys quantity    : " + CORRUPTION_TEST_KEYS_QUANTITY);
        System.out.println("Add number       : " + CORRUPTION_TEST_ADD_NUMBER);

        System.out.println();

        System.out.println("Values in hash map must be equal "
                + CORRUPTION_TEST_ADD_NUMBER * CORRUPTION_TEST_THREADS_QUANTITY + ".");

        System.out.println();
        System.out.println("Starting test...");

        Map<String, Integer> testMap =
                new DataCorruptionTest(
                        CORRUPTION_TEST_KEYS_QUANTITY,
                        CORRUPTION_TEST_THREADS_QUANTITY,
                        CORRUPTION_TEST_ADD_NUMBER
                ).run();

        System.out.println("...Done\n\nActual hash map values: ");
        System.out.println();
        printCorruptedMap(testMap);

    }

    public static void raceConditionTest(){

        System.out.println("TEST #2:");
        System.out.println("Threads try to put new entry in same hash map.");
        System.out.println("Hash map load factor set to very small,\n" +
                           "so hash map would resize more often.");

        System.out.println();
        System.out.println("Threads quantity : " + RACE_CONDITION_TEST_THREADS_QUANTITY);
        System.out.println("Every thread add " + RACE_CONDITION_TEST_KEYS_QUANTITY + " keys");

        System.out.println();
        System.out.println("Starting test...");

        new RaceConditionTest(RACE_CONDITION_TEST_THREADS_QUANTITY, RACE_CONDITION_TEST_KEYS_QUANTITY,
                new RaceConditionTestCallbackInterface() {
                    @Override
                    public void onStackOverflow(int threadId) {
                        System.out.println("Stack overflow on thread " + threadId + ".");
                    }

                    @Override
                    public void onClassCastException(int threadId) {
                        System.out.println("Class cast exception on thread " + threadId + ".");
                    }

                    @Override
                    public void onSuccess(RaceConditionTest test) {
                        System.out.println("No errors found due race condition test. Some times it happens");
                    }

                    @Override
                    public void onInfiniteLoop(int threadId, long noRespondingTime) {
                        System.out.println("Possible infinite loop on thread " + threadId +
                                ". " + (double)noRespondingTime / 1000 + " seconds pass since last put() method call.");
                    }

                }
        ).run();

    }

    /**
     *  _______________
     * | key  | value |
     * |##############|
     * | alf5 | 11000 |
     * | sac  | 5112  |
     *  ¯¯¯¯¯¯¯¯¯¯¯¯¯¯
     *
     * @param map map to print
     */
    private static void printCorruptedMap(Map<String, Integer> map){

        System.out.println(" ______________ ");
        System.out.println("| key  | value |");
        System.out.println("|##############|");

        for(Map.Entry<String, Integer> entry : map.entrySet()){

            StringBuilder row = new StringBuilder();

            String key   = entry.getKey();
            String value = entry.getValue().toString();
            row.append("| ");
            row.append(key);

            for(int i  = 0;i < 4 - key.length();i++)
                row.append(' ');

            row.append(" | ");
            row.append(value);

            for(int i = 0;i < 5 - value.length();i++)
                row.append(' ');

            row.append(" |");

            System.out.println(row);

        }

        System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯ ");

    }

}
