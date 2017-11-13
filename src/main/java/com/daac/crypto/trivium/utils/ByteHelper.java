package com.daac.crypto.trivium.utils;

import java.nio.charset.StandardCharsets;

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
