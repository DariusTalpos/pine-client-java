package org.example.utils;

import org.example.PineException;

import java.util.ArrayList;

public class u32 {
    private long value;

    public u32(long value) throws PineException {
        if(value<0 || value>4294967295L)
            throw new PineException("Value out of range for unsigned 32-bit value");
        this.value = value;
    }

    public int[] toLEArray() {
        int[] array = new int[4];
        long valueCopy = value;
        ArrayList<Integer> digits = new ArrayList<>();
        while(valueCopy>0) {
            digits.addFirst( (int) (valueCopy%16));
            valueCopy/=16;
        }
        int digitsAmount = digits.size();
        if(digitsAmount<8)
            for(int i=0;i<8-digitsAmount;i++) {
                digits.addFirst(0);
            }
        for(int i=7;i>0;i-=2) {
            int firstDigit = digits.get(i-1);
            int secondDigit = digits.get(i);
            array[(7-i)/2] = 16*firstDigit+secondDigit;
        }
        return array;
    }

    public int[] toBEArray() {
        int[] array = new int[4];
        long valueCopy = value;
        ArrayList<Integer> digits = new ArrayList<>();
        while(valueCopy>0) {
            digits.addFirst((int) (valueCopy%16));
            valueCopy/=16;
        }
        int digitsAmount = digits.size();
        if(digitsAmount<8)
            for(int i=0;i<8-digitsAmount;i++) {
                digits.addFirst(0);
            }
        for(int i=7;i>0;i-=2) {
            int firstDigit = digits.get(i-1);
            int secondDigit = digits.get(i);
            array[i/2] = 16*firstDigit+secondDigit;
        }
        return array;
    }
}
