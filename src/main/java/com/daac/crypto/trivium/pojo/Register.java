package com.daac.crypto.trivium.pojo;

import com.daac.crypto.trivium.exception.RegisterLoadException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by daac on 11/4/17.
 */
public class Register {

    private List<FlipFlop> flipFlops;
    private int size;
    private int feedBackBitIndex;
    private int feedForwardBitIndex;
    private int andFirstBitIndex;
    private int andSecondBitIndex;

    public Register(int size, int feedBackBitIndex, int feedForwardBitIndex, int andFirstBitIndex, int andSecondBitIndex) {
        this.size = size;
        this.feedBackBitIndex = feedBackBitIndex;
        this.feedForwardBitIndex = feedForwardBitIndex;
        this.andFirstBitIndex = andFirstBitIndex;
        this.andSecondBitIndex = andSecondBitIndex;

        flipFlops = new LinkedList<>();

        for(int i = 0; i < size; i++)   {
            flipFlops.add(new FlipFlop());
        }
    }

    public void loadRegister(int startIndex, int endIndex, byte [] valuesToLoad) throws RegisterLoadException    {
        if(endIndex - startIndex <= 0 || endIndex - startIndex != valuesToLoad.length - 1)    {
            throw new RegisterLoadException("Incorrect register indexes.");
        }   else    {
            FlipFlop currentFlipFlop;
            for(int i = 0; i < valuesToLoad.length; i++)    {
                currentFlipFlop = getFlipFlopAt(startIndex - 1 + i);
                currentFlipFlop.setValue(valuesToLoad[i]);
            }
        }
    }

    public Byte shift(byte xorByte) {
        Byte outgoingByte;
        outgoingByte = getOutput();

        FlipFlop helper = flipFlops.remove(size - 1);
        helper.setValue((byte) (getByteAt(feedBackBitIndex) ^ xorByte));
        flipFlops.add(0, helper);

        return outgoingByte;
    }

    public Byte getOutput() {
        byte output;
        output = (byte) (getByteAt(andFirstBitIndex - 1) & getByteAt(andSecondBitIndex - 1));
        output = (byte) (output ^ (getByteAt(size - 1) ^ getByteAt(feedForwardBitIndex - 1)));
        return output;
    }

    private FlipFlop getFlipFlopAt(int index)    {
        return flipFlops.get(index);
    }

    private Byte getByteAt(int index)   {
        return getFlipFlopAt(index).getValue();
    }

    public int getSize() {
        return size;
    }

    public List<FlipFlop> getFlipFlops() {
        return flipFlops;
    }
}
