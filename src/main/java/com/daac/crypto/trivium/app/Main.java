package com.daac.crypto.trivium.app;

import com.daac.crypto.trivium.cypher.TriviumCypher;
import com.daac.crypto.trivium.exception.RegisterLoadException;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String [] args) throws RegisterLoadException {

        String initVectorString = RandomStringUtils.randomAlphanumeric(20);
        System.out.println("Init Vector String: " + initVectorString);

        TriviumCypher triviumCypher = new TriviumCypher(initVectorString);

        String test = "esta es una prueba del cifrado trivium";
        byte [] stringAsByteArray = test.getBytes(StandardCharsets.UTF_8);
        byte [] cypheredString = new byte[stringAsByteArray.length];

        for(int i = 0; i < stringAsByteArray.length; i++)   {
            cypheredString[i] = triviumCypher.cypherByte(stringAsByteArray[i]);
        }
        System.out.println(new String(cypheredString, StandardCharsets.UTF_8));

        /*initVectorString = "vanohaV1YOHHsRxF1LO4";
        String cyphered = "F�=";
        cypheredString = cyphered.getBytes(StandardCharsets.UTF_8);
        */
        triviumCypher = new TriviumCypher(initVectorString);
        byte [] decypheredString = new byte[cypheredString.length];
        for(int i = 0; i < cypheredString.length; i++)   {
            decypheredString[i] = triviumCypher.cypherByte(cypheredString[i]);
        }
        System.out.println(new String(decypheredString, StandardCharsets.UTF_8));
    }
}
