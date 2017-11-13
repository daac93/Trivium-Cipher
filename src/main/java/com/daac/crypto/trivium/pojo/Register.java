package com.daac.crypto.trivium.pojo;

import com.daac.crypto.trivium.exception.RegisterLoadException;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C) 2017  Daniel Arguedas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
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

    @Override
    public String toString()    {
        StringBuilder sb = new StringBuilder();

        for(FlipFlop current : flipFlops)   {
            sb.append(current.toString());
        }

        return sb.toString();
    }
}
