package com.anotheria.bootcamp.file_transfer;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;

public class Token implements Serializable{

    private byte[] value;

    public static Token newRandomToken(int size){

        byte[] value = new byte[size];
        new SecureRandom().nextBytes(value);
        return new Token(value);

    }

    private Token(byte[] value) {
        this.value = value;
    }

    public int hashCode(){
        return Arrays.hashCode(value);
    }

    public boolean equals(Object other){

        if(other == null || !(other instanceof Token) || ((Token) other).value.length != this.value.length)
            return false;

        for(int i = 0;i < this.value.length; i++)
            if(this.value[i] != ((Token) other).value[i])
                return false;

        return true;

    }

}
