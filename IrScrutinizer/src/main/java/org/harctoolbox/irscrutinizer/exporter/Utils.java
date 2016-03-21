/*
Copyright (C) 2016 Bengt Martensson.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program. If not, see http://www.gnu.org/licenses/.
*/

package org.harctoolbox.irscrutinizer.exporter;

/**
 * This class
 */
public class Utils {
    private Utils() {
    }

    public static String formatByte(int x) {
        return String.format("%02X", x & 0xFF);
    }

    public static String reverseByte(int x) {
        int rev = Integer.reverse(x & 0xFF);
        rev >>>= Integer.SIZE - Byte.SIZE;
        return formatByte(rev);
    }

    public static String complementByte(int x) {
        return formatByte(0xFF - (x & 0xFF));
    }

    public static String reverseComplementByte(int x) {
        return reverseByte(0xFF - (x & 0xFF));
    }

    public static String mkCIdentifier(String s) {
        String str = s.replaceAll("[^0-9A-Za-z_]", "_");
        return str.matches("^[0-9].*") ? ("_" + str) : str;
    }
}