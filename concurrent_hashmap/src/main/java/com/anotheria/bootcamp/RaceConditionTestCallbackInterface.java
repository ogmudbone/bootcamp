package com.anotheria.bootcamp;

public interface RaceConditionTestCallbackInterface {

    void onStackOverflow(int threadId);
    void onClassCastException(int threadId);
    void onOutOfMemory(int threadId);
    void onSuccess(RaceConditionTest test);
    void onInfiniteLoop(int threadId, long noRespondingTime);

}
