package com.daac.crypto.trivium.pojo;

/**
 * Created by daac on 11/4/17.
 */
public class FlipFlop {

    private byte value;

    public FlipFlop()   {
        value = 0;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public String toString()   {
        return value == 0x00 ? "0" : "1";
    }
}
