package org.example.utils;

import org.example.PineException;

import java.util.ArrayList;

public class u16 {
    private int value;

    public u16(int value) throws PineException {
        if(value<0 || value>65535)
            throw new PineException("Value out of range for unsigned 16-bit value");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int[] toLEArray() {
        int[] array = new int[2];
        int valueCopy = value;
        ArrayList<Integer> digits = new ArrayList<>();
        while(valueCopy>0) {
            digits.addFirst(valueCopy%16);
            valueCopy/=16;
        }
        int digitsAmount = digits.size();
        if(digitsAmount<4)
            for(int i=0;i<4-digitsAmount;i++) {
                digits.addFirst(0);
            }
        for(int i=3;i>0;i-=2) {
            int firstDigit = digits.get(i-1);
            int secondDigit = digits.get(i);
            array[(3-i)/2] = 16*firstDigit+secondDigit;
        }
        return array;
    }
}
