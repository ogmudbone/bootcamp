package com.anotheria.bootcamp;

/**
 * Class, where hash code
 * can be set manually.
 * Used in race condition test
 * as key for building one
 * long entry chain.
 */
public class SpecialMapKey {

    private int hashCode;

    SpecialMapKey(int hashCode) {
        this.hashCode = hashCode;
    }

    public int hashCode(){
        return hashCode;
    }

}