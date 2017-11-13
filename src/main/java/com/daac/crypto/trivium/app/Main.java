package com.daac.crypto.trivium.app;

import com.daac.crypto.trivium.cypher.TriviumCypher;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String [] args) throws Exception    {

        String initVectorString = RandomStringUtils.randomAlphanumeric(20);
        System.out.println("Init Vector String: \n" + initVectorString);
        System.out.println("");

        TriviumCypher triviumCypher = new TriviumCypher(initVectorString);

        String test = args[0];
        byte [] stringAsByteArray = test.getBytes(StandardCharsets.UTF_8);
        byte [] cypheredString = new byte[stringAsByteArray.length];

        for(int i = 0; i < stringAsByteArray.length; i++)   {
            cypheredString[i] = triviumCypher.cypherByte(stringAsByteArray[i]);
        }

        System.out.println("Ciphered String:" + new String(cypheredString, StandardCharsets.UTF_8));
        System.out.println("Ciphered HEX String:" + Hex.encodeHexString(cypheredString));


        triviumCypher = new TriviumCypher(initVectorString);
        byte [] decypheredString = new byte[cypheredString.length];
        for(int i = 0; i < cypheredString.length; i++)   {
            decypheredString[i] = triviumCypher.cypherByte(cypheredString[i]);
        }
        System.out.println("Deciphered Text: " + new String(decypheredString, StandardCharsets.UTF_8));
    }
}
