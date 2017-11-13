package com.daac.crypto.trivium.utils;

import java.nio.charset.StandardCharsets;

public class ByteHelper {

    public byte[] getBytesFromString(String input)  {
        byte [] stringBytes = input.getBytes(StandardCharsets.UTF_8);
        byte [] outputBytes = new byte[stringBytes.length * 4];
        int byteAsInt;
        int bytesCount = stringBytes.length;
        int outputBytesSize = outputBytes.length - 1;

        for(int i = 0; i < bytesCount; i++) {
            byteAsInt = Byte.toUnsignedInt(stringBytes[i]);
            outputBytes[outputBytesSize - (i * 2)] = outputBytes[i * 2] =  (byte) ((byteAsInt / 10) % 2);
            outputBytes[outputBytesSize - (i * 2) - 1] = outputBytes[(i * 2) + 1] =  (byte) (byteAsInt % 2);
        }

        return outputBytes;
    }
}
