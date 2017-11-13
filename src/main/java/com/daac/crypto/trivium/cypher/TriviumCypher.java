package com.daac.crypto.trivium.cypher;

import com.daac.crypto.trivium.exception.RegisterLoadException;
import com.daac.crypto.trivium.pojo.Register;
import com.daac.crypto.trivium.utils.ByteHelper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class TriviumCypher {

    private List<Register> registers;

    private Register registerOne;
    private Register registerTwo;
    private Register registerThree;

    private String secretKeyString;

    public TriviumCypher(String initVectorString) throws RegisterLoadException {
        registers = new ArrayList();
        this.secretKeyString = "Test Key for Trivium";

        registerOne = new Register(93, 69, 66, 91, 92);
        registerTwo = new Register(84, 78, 69, 82,83);
        registerThree = new Register(111, 87, 66,109, 110);

        ByteHelper byteHelper = new ByteHelper();

        //Load 80 bytes
        byte [] initVector = byteHelper.getBytesFromString(initVectorString);
        registerOne.loadRegister(1, 80, initVector);

        byte [] secretKey = byteHelper.getBytesFromString(secretKeyString);
        registerTwo.loadRegister(1, 80, secretKey);

        byte [] registerThreeInit = {1, 1 ,1};
        registerThree.loadRegister(109, 111, registerThreeInit);

        registers.add(registerOne);
        registers.add(registerTwo);
        registers.add(registerThree);

        warmUp(4 * (registerOne.getSize() + registerTwo.getSize() + registerThree.getSize()));
    }

    public Map encrypt(String stringToCypher)   {
        Map<String, String> result = new HashMap();
        byte [] stringAsByteArray = stringToCypher.getBytes(StandardCharsets.UTF_8);
        byte [] cypheredString = new byte[stringAsByteArray.length];

        for(int i = 0; i < stringAsByteArray.length; i++)   {
            cypheredString[i] = cypherByte(stringAsByteArray[i]);
        }

        result.put("result", new String(cypheredString, StandardCharsets.UTF_8));
        //result.put("secretKey",);

        return result;
    }

    private void warmUp(int cycles)  {
        for(int i = 0; i < cycles; i++) {
            generateNextBit();
        }
    }

    private byte generateNextBit()    {
        byte registerOneOutput, registerTwoOutput, registerThreeOutput;

        registerOneOutput = registerOne.getOutput();
        registerTwoOutput = registerTwo.getOutput();
        registerThreeOutput = registerThree.getOutput();

        registerOne.shift(registerThreeOutput);
        registerTwo.shift(registerOneOutput);
        registerThree.shift(registerTwoOutput);

        byte result = (byte) (registerOneOutput ^ registerTwoOutput ^ registerThreeOutput);
        printRegisters();
        return result;
    }

    private byte generateNextByteAndUpdateUI(int sleep) {
        byte nextByte = 0x00;
        byte helper;

        for(int i = 0; i < 8; i++) {
            helper = generateNextBit();

            try {
                Thread.sleep(sleep);
            }   catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            nextByte |= (helper << i);
        }

        return nextByte;
    }

    private byte generateNextByte() {
        byte nextByte = 0x00;
        byte helper;

        for(int i = 0; i < 8; i++) {
            helper = generateNextBit();
            //nextByte |= (byte) (generateNextBit() << i);
            nextByte |= (helper << i);
        }

        return nextByte;
    }

    public byte cypherByte(byte byteToCypher)  {
        byte cypheringByte = generateNextByte();
        return (byte) (byteToCypher ^ cypheringByte);
    }

    public byte cypherByte(byte byteToCypher, int sleep)  {
        byte cypheringByte = generateNextByteAndUpdateUI(sleep);
        return (byte) (byteToCypher ^ cypheringByte);
    }

    public List<Register> getRegisters() {
        return registers;
    }

    private void printRegisters()   {
        int i = 1;
        StringBuilder sb = new StringBuilder();

        for(Register register : registers)  {
            sb.append("Register ");
            sb.append(i);
            sb.append(": \n");
            sb.append(register.toString());
            sb.append("\n\n");
            i++;
        }
        sb.append("\n\n\n\n\n");

        System.out.println(sb.toString());
    }
}
