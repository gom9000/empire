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


import java.util.Hashtable;

import net.gos95.empire.lang.EmpireObject;

// TODO XNEW Empire - conversion objects...
/**
 * The <code>Converter</code> class implements a set of static methods
 * for data representation and data type conversions.<br>
 * The static methods in this class tests and converts from string to
 * java native types or wrappers, and vice-versa.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.lang.EmpireObject
 */
public abstract class Converter
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


    // constants for alphabetic, numeric and special-chars default charset.

    /** alphabetic default charset */
    static public final String DEF_ALPHA_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    /** numeric default charset */
    static public final String DEF_DIGIT_SET = "0123456789";

    /** special chars default charset */
    static public final String DEF_SPECIAL_SET = ".,;:!?-=_*+£$%&()[]{}@#°§^\"\'";



    // constants for string representation of binaries value.

    /** "Y" / "N" string representation of binary state */
    static public final int Y_N        = 1;

    /** "S" / "N" it string representation of binary state */
    static public final int S_N        = 2;

    /** "YES" / "NO" string representation of binary state */
    static public final int YES_NO     = 3;

    /** "SI" / "NO" it string representation of binary state */
    static public final int SI_NO      = 4;

    /** "TRUE" / "FALSE" string representation of binary state */
    static public final int TRUE_FALSE = 5;

    /** "1" / "0" string representation of binary state */
    static public final int ONE_ZERO =   6;


    /* private constructor */
    private Converter()
    {
    	super(Converter.class, serialVersionUID);
    }


    /**
     * Returns the <code>boolean</code> value of the given string representation.<br>
     * If the string representation is null then returns the given default value.
     *
     * @param  str      string representation of the boolean value
     * @param  defValue default <code>boolean</code> value to return if the
     *                  string representation is invalid or null
     * @return          the boolean value or default value if string is null
     */
    static public boolean toBool(String str, boolean defValue)
    {
        if (str == null) return defValue;

        str = str.toUpperCase();
        if (str.equals("Y") || str.equals("S") || str.equals("YES")
                 || str.equals("SI") || str.equals("TRUE") || str.equals("1"))
            return true;

        return false;
    }


    /**
     * Returns the string representation of the given boolean value.<br>
     *
     * @param  bool   <code>boolean</code> value to convert
     * @param  format the format of the output string representation
     * @return        the string representation
     */
    static public String toBoolString(boolean bool, int format)
    {
        String strue, sfalse = "false";

        switch (format)
        {
            case Y_N:         strue = "Y";    sfalse = "N"; break;
            case S_N:         strue = "S";    sfalse = "N"; break;
            case YES_NO:      strue = "YES";  sfalse = "NO"; break;
            case SI_NO:       strue = "SI";   sfalse = "NO"; break;
            case TRUE_FALSE:  strue = "true"; sfalse = "false"; break;
            case ONE_ZERO:    strue = "1";    sfalse = "0"; break;
            default:          strue = "true"; sfalse = "false"; break;
        }

        if (bool) return strue;

        return sfalse;
    }


    /**
     * Returns the string representation of the given string value.
     * If the string representation is null then returns the given default value.
     *
     * @param  str      the string to convert
     * @param  defValue the default string value to return if string
     *                  parameter is blank or null
     * @return          the string representation
     */
    static public String toString(String str, String defValue)
    {
        if (str == null || str.equalsIgnoreCase("null") || str.equals(""))
            return defValue;
        return str;
    }


    /**
     * Returns the string representation of the given string value, filtered
     * by the given string charset.<br>
     *
     * @param  str      the string to convert throught charset
     * @param  defValue the default string value to return if string
     *                  parameter is blank or null
     * @param  charset  string character set
     * @return          the string representation
     */
    static public String toString(String str, String defValue, String charset)
    {
        StringBuffer sb;

        if (str == null || str.equalsIgnoreCase("null") || str.equals(""))
            return defValue;

        sb = new StringBuffer();
        for (int i=0; i<str.length(); i++)
            if (charset.indexOf(str.charAt(i)) != -1) sb.append(str.charAt(i));

        return toString(sb.toString(), defValue);
    }


    /**
     * Returns the <code>Integer</code> value of the given string value.<br>
     * If the string value is null then returns the given default value.
     *
     * @param  str      string representation of the Integer value
     * @param  defValue default <code>int</code> value to return if the
     *                  string value is invalid or null
     * @return          the Integer value or default value if string is null
     */
    static public Integer toInteger(String str, int defValue)
    {
        Integer i = null;

        try {
            i = new Integer(str.toString());
        } catch(NumberFormatException e) {
            i = new Integer(defValue);
        }

        return i;
    }


    /**
     * Returns the <code>Float</code> value of the given string value.<br>
     * If the string value is null then returns the given default value.
     *
     * @param  str      string representation of the Float value
     * @param  defValue default <code>float</code> value to return if the
     *                  string value is invalid or null
     * @return          the Float value or default value if string is null
     */
    static public Float toFloat(String str, float defValue)
    {
        Float i = null;

        try {
            i = new Float(str.toString());
        } catch(NumberFormatException e) {
            i = new Float(defValue);
        }

        return i;
    }


    /**
     * Returns the <code>int</code> value of the given string value.<br>
     * If the string value is null then returns the given default value.
     *
     * @param  str      string representation of the int value
     * @param  defValue default <code>int</code> value to return if the
     *                  string value is invalid or null
     * @return          the int value or default value if string is null
     */
    static public int toInt(String str, int defValue)
    {
        int i;

        try {
            i = toInteger(str, defValue).intValue();
        } catch(Exception e) {
            i = defValue;
        }

        return i;
    }


    /**
     * Returns the <code>float</code> value of the given string value.<br>
     * If the string value is null then returns the given default value.
     *
     * @param  str      string representation of the float value
     * @param  defValue default <code>float</code> value to return if the
     *                  string value is invalid or null
     * @return          the float value or default value if string is null
     */
    static public float toFloatValue(String str, float defValue)
    {
        float i;

        try {
            i = toFloat(str, defValue).floatValue();
        } catch(Exception e) {
            i = defValue;
        }

        return i;
    }


    /**
     * Tests if the given string value is a valid int.
     *
     * @param  str      string representation of the int to test
     * @return          the boolean result of the test
     */
    static public boolean isInt(String str)
    {
        try {
            new Integer(str.toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }


    /**
     * Tests if the given string value is a valid float.
     *
     * @param  str      string representation of the float to test
     * @return          the boolean result of the test
     */
    static public boolean isFloat(String str)
    {
        try {
            new Float(str.toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }


    /**
     * Returns the string representation of the given float value.
     *
     * @param  f         the <code>float</code> value to convert
     * @param  sep       decimal char separator
     * @param  precision number of decimal digit to convert
     * @return           the string representation
     */
    static public String toFloatString(float f, char sep, int precision)
    {
        String s = ""+f;
        int len = s.substring(s.indexOf('.')).length()-1;

        if (precision == 0)
            s = s.substring(0, s.length()-len-1);
        else if (len <= precision)
            for (int i=0; i<precision-len; i++) s += "0";
        else
            s = s.substring(0, s.length()-(len-precision));

        return s.replace('.', sep);
    }


    /**
     * Returns the <code>Hashtable</code> representation
     * of the given csv name-value pairs string.
     *
     * @param  s         csv name-value pairs string
     * @param  sep       pairs separator
     * @return           the hashtable
     */
    static public Hashtable toHashtable(String s, String sep)
    {
        Hashtable hash = new Hashtable();
        String[] pairs, spares;

        pairs = s.split(sep);
        for (int i=0; i<pairs.length; i++) {
            spares = pairs[i].split("=");
            hash.put(spares[0].trim(), spares[1].trim());
        }

        return hash;
    }


    /**
     * Returns the csv name-value pairs string of the given hashtable value.
     *
     * @param  hash      the hashtable to convert
     * @param  sep       pairs separator
     * @return           string representation
     */
    static public String toString(Hashtable hash, String sep)
    {
        String s = hash.toString();

        s = s.replace('{', ' ');
        s = s.replace('}', ' ');
        s = s.replaceAll(", ", sep);
        s = s.trim();

        return s;
    }
}
