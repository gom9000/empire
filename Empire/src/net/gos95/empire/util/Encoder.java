/*
 * E  M  P  I  R  E   Library
 *   ________        _________________.________
 *  /  _____/  ____ /   _____/   __   \   ____/
 * /   \  ___ /  _ \\_____  \\____    /____  \
 * \    \_\  (  <_> )        \  /    //       \
 *  \______  /\____/_______  / /____//______  /
 *         \/              \/               \/
 * Copyright (c) 2007 2009 2010 by
 * Alessandro Fraschetti (gos95@gommagomma.net)
 * 
 * This file is part of the Empire library.
 * For more information about Empire visit:
 *     http://gommagomma.net/gos95/empire
 *
 * Empire library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. 
 *
 * Empire library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */


package net.gos95.empire.util;


import java.security.*;

import net.gos95.empire.lang.EmpireObject;


/**
 * The <code>Encoder</code> class implements static methods
 * to encode string. 
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.lang.EmpireObject
 */
public class Encoder
extends EmpireObject
{
	private static final long serialVersionUID = 101L;

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";


    private Encoder() {}


    /**
     * Encode input string using rot13 algorithm.<br>
     * Rot13 works by replacing each upper and lower case letters
     * with the letter 13 positions ahead or behind it in the alphabet.
     * The encryption algorithm is symmetric.
     *
     * @param  str string to encode
     * @return rot13 the encoded string
     */
    public static String rot13(String str)
    {
        String s = "";

        for (int i=0; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'A' && c <= 'Z') c -= 13;
            s += c;
        }
        return s;
    }


    /**
     * Encode input string using specified java-implemented algorithm
     * and returns encoded formatted string as 2-char hex.
     *
     * @param   str string to encode
     * @param   alg the encode algorithm
     * @throws  EncoderException
     * @see     net.gos95.empire.util.EncoderException
     * @see     java.security.MessageDigest
     */
    public static String hexcrypt(String str, String alg)
    throws EncoderException
    {
        MessageDigest md;
        byte[] bytes;
        String hex = "";

        try {
            md = MessageDigest.getInstance(alg);
        } catch(NoSuchAlgorithmException e) {
            throw new EncoderException("Algorithm not supported: " + alg);
        }

        md.reset();
        md.update(str.getBytes());
        bytes = md.digest();

        String tmp;
        for (int i=0; i<bytes.length; i++) {
            tmp = Integer.toHexString((new Byte(bytes[i])).intValue());
            if (tmp.length() == 1) tmp = "0" + tmp;
            hex += tmp;
        }

        return hex;
    }


    /**
     * Tests equality of encoded strings by specified algorithm.
     *
     * @param  str1  encoded string 1
     * @param  str2  encoded string 2
     * @param  alg   encoded algorithm
     * @return the test result
     * @throws EncoderException
     */
    public static boolean isEqual(String str1, String str2, String alg)
    throws EncoderException
    {
        try {
            MessageDigest.getInstance(alg);
        } catch(NoSuchAlgorithmException e) {
            throw new EncoderException("Algorithm not supported: " + alg);
        }

        return (MessageDigest.isEqual(str1.getBytes(), str2.getBytes()));
    }
}
