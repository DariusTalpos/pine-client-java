package org.example.utils;

import org.example.PineException;

import java.util.ArrayList;

public class u8 {
    private int value;

    public u8(int value) throws PineException {
        if(value<0 || value>255)
            throw new PineException("Value out of range for unsigned 8-bit value");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int[] toLEArray() {
        return new int[]{value};
    }

}
