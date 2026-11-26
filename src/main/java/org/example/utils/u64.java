package org.example.utils;

import org.example.PineException;

import java.util.ArrayList;

public class u64 {
    private long value;

    public u64(String value) throws PineException {
        try {
            this.value = Long.parseUnsignedLong(value);
        } catch (NumberFormatException e) {
            throw new PineException("Incorrect value for unsigned 64-bit value");
        }
    }

    public int[] toLEArray() {
        int[] array = new int[8];
        long valueCopy = value;
        ArrayList<Integer> digits = new ArrayList<>();
        while(valueCopy!=0) {
            digits.addFirst((int)(valueCopy%16));
            valueCopy/=16;
        }
        int digitsAmount = digits.size();
        System.out.println("Digits: "+digitsAmount);
        if(digitsAmount<16)
            for(int i=0;i<16-digitsAmount;i++) {
                digits.addFirst(0);
            }
        for(int i=15;i>0;i-=2) {
            int firstDigit = digits.get(i-1);
            int secondDigit = digits.get(i);
            array[(15-i)/2] = (16*firstDigit+secondDigit) & 0xFF;
        }
        return array;
    }
}
