/*
 * E  M  P  I  R  E  Library
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
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License as 
 *  published by the Free Software Foundation; either version 2 of the
 *  License, or (at your option) any later version. 
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses/>.
 */


package net.gos95.empire.util;


import java.util.Hashtable;

import net.gos95.empire.util.Converter;
import junit.framework.*;


public class ConverterTest
extends TestCase
{
	public ConverterTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	/**
     * Unit Test del metodo .toBool().
     */
    public void testToBool() {
        boolean ret;

        ret = Converter.toBool("TRUE", false);
        Assert.assertTrue("Test 1: toBool(\"TRUE\"): ", ret);

        ret = Converter.toBool("S", false);
        Assert.assertTrue("Test 2: toBool(\"S\"): ", ret);

        ret = Converter.toBool("YES", false);
        Assert.assertTrue("Test 3: toBool(\"YES\"): ", ret);

        ret = Converter.toBool("SI", false);
        Assert.assertTrue("Test 4: toBool(\"SI\"): ", ret);

        ret = Converter.toBool("S", false);
        Assert.assertTrue("Test 5: toBool(\"S\"): ", ret);

        ret = Converter.toBool("1", false);
        Assert.assertTrue("Test 6: toBool(\"1\"): ", ret);

        ret = Converter.toBool("valorequalunque", true);
        Assert.assertTrue("Test 7: toBool(\"valorequalunque\"): ", !ret);

        ret = Converter.toBool("", true);
        Assert.assertTrue("Test 8: toBool(\"\"): ", !ret);

        ret = Converter.toBool(null, true);
        Assert.assertTrue("Test 9: toBool(null)): ", ret);
    }


    /**
     * Unit Test del metodo .toBoolString().
     */
    public void testToBoolString() {
        String ret;

        ret = Converter.toBoolString(true, Converter.TRUE_FALSE);
        Assert.assertTrue("Test 1: toBoolString(true, Converter.TRUE_FALSE): ", ret.equals("true"));

        ret = Converter.toBoolString(false, Converter.TRUE_FALSE);
        Assert.assertTrue("Test 2: toBoolString(false, Converter.TRUE_FALSE): ", ret.equals("false"));

        ret = Converter.toBoolString(true, Converter.ONE_ZERO);
        Assert.assertTrue("Test 3: toBoolString(true, Converter.ONE_ZERO): ", ret.equals("1"));

        ret = Converter.toBoolString(false, Converter.ONE_ZERO);
        Assert.assertTrue("Test 4: toBoolString(false, Converter.ONE_ZERO): ", ret.equals("0"));

        ret = Converter.toBoolString(true, Converter.S_N);
        Assert.assertTrue("Test 5: toBoolString(true, Converter.S_N): ", ret.equals("S"));

        ret = Converter.toBoolString(false, Converter.S_N);
        Assert.assertTrue("Test 6: toBoolString(false, Converter.S_N): ", ret.equals("N"));

        ret = Converter.toBoolString(true, Converter.SI_NO);
        Assert.assertTrue("Test 7: toBoolString(true, Converter.SI_NO): ", ret.equals("SI"));

        ret = Converter.toBoolString(false, Converter.SI_NO);
        Assert.assertTrue("Test 8: toBoolString(false, Converter.SI_NO): ", ret.equals("NO"));

        ret = Converter.toBoolString(true, Converter.Y_N);
        Assert.assertTrue("Test 9: toBoolString(true, Converter.Y_N): ", ret.equals("Y"));

        ret = Converter.toBoolString(false, Converter.Y_N);
        Assert.assertTrue("Test 10: toBoolString(false, Converter.Y_N): ", ret.equals("N"));

        ret = Converter.toBoolString(true, Converter.YES_NO);
        Assert.assertTrue("Test 11: toBoolString(true, Converter.YES_NO): ", ret.equals("YES"));

        ret = Converter.toBoolString(false, Converter.YES_NO);
        Assert.assertTrue("Test 12: toBoolString(false, Converter.YES_NO): ", ret.equals("NO"));

        ret = Converter.toBoolString(false, -1);
        Assert.assertTrue("Test 12: toBoolString(false, -1): ", ret.equals("false"));
    }


    /**
     * Unit Test del metodo .toString().
     */
    public void testToString() {
        String ret;

        ret = Converter.toString("unastringa", "value");
        Assert.assertTrue("Test 1: toString(\"unastringa\", \"value\"): ", ret.equals("unastringa"));

        ret = Converter.toString("", "value");
        Assert.assertTrue("Test 2: toString(\"\", \"value\"): ", ret.equals("value"));

        ret = Converter.toString("null", "value");
        Assert.assertTrue("Test 3: toString(\"null\", \"value\"): ", ret.equals("value"));

        ret = Converter.toString((String)null, "value");
        Assert.assertTrue("Test 4: toString(null, \"value\"): ", ret.equals("value"));

        ret = Converter.toString("abcdefg", "value", "abcd");
        Assert.assertTrue("Test 5: toString(\"abcdefg\", \"value\"): ", ret.equals("abcd"));

        ret = Converter.toString("abX Yc def g", "value", "abcdefg ");
        Assert.assertTrue("Test 5: toString(\"abcdefg\", \"value\"): ", ret.equals("ab c def g"));
    }


    /**
     * Unit Test del metodo .toInteger().
     */
    public void testToInteger() {
        Integer ret;

        ret = Converter.toInteger("-101", 55);
        Assert.assertTrue("Test 1: toInteger(\"-101\",55): ", ret.equals(new Integer(-101)));

        ret = Converter.toInteger("-1K01", 55);
        Assert.assertTrue("Test 2: toInteger(\"-1K01\",55): ", ret.equals(new Integer(55)));

        ret = Converter.toInteger("-1.01", 55);
        Assert.assertTrue("Test 3: toInteger(\"-1.01\",55): ", ret.equals(new Integer(55)));
    }


    /**
     * Unit Test del metodo .toFloat().
     */
    public void testToFloat() {
        Float ret;

        ret = Converter.toFloat("-101,50", 55.0F);
        Assert.assertTrue("Test 1: toFloat(\"-101,50\",55.0F): ", ret.equals(new Float(55.0F)));

        ret = Converter.toFloat("-1K01", 55.0F);
        Assert.assertTrue("Test 2: toFloat(\"-1K01\",55.0F): ", ret.equals(new Float(55.0F)));

        ret = Converter.toFloat("-1.01", 55.0F);
        Assert.assertTrue("Test 3: toFloat(\"-1.01\",55.0F): ", ret.equals(new Float(-1.01F)));

        ret = Converter.toFloat("-1.", 55.0F);
        Assert.assertTrue("Test 4: toFloat(\"-1.\",55.0F): ", ret.equals(new Float(-1.0F)));

        ret = Converter.toFloat(".0", 55.0F);
        Assert.assertTrue("Test 5: toFloat(\".0\",55.0F): ", ret.equals(new Float(0.0F)));
    }


    /**
     * Unit Test del metodo .toInt().
     */
    public void testToInt() {
        int ret;

        ret = Converter.toInt("-101", 55);
        Assert.assertTrue("Test 1: toInt(\"-101\",55): ", ret == -101);

        ret = Converter.toInt("-1K01", 55);
        Assert.assertTrue("Test 2: toInt(\"-1K01\",55): ", ret == 55);

        ret = Converter.toInt("-1.01", 55);
        Assert.assertTrue("Test 3: toInt(\"-1.01\",55): ", ret == 55);
    }


    /**
     * Unit Test del metodo .toFloatValue().
     */
    public void testToFloatValue() {
        float ret;

        ret = Converter.toFloatValue("-101,50", 55.0F);
        Assert.assertTrue("Test 1: toFloatValue(\"-101,50\",55.0F): ", ret == 55.0F);

        ret = Converter.toFloatValue("-1K01", 55.0F);
        Assert.assertTrue("Test 2: toFloatValue(\"-1K01\",55.0F): ", ret == 55.0F);

        ret = Converter.toFloatValue("-1.01", 55.0F);
        Assert.assertTrue("Test 3: toFloatValue(\"-1.01\",55.0F): ", ret == -1.01F);

        ret = Converter.toFloatValue("-1.", 55.0F);
        Assert.assertTrue("Test 4: toFloatValue(\"-1.\",55.0F): ", ret == -1.0F);

        ret = Converter.toFloatValue(".0", 55.0F);
        Assert.assertTrue("Test 5: toFloatValue(\".0\",55.0F): ", ret == 0.0F);
    }


    /**
     * Unit Test del metodo .isInt().
     */
    public void testIsInt() {
        boolean ret;

        ret = Converter.isInt("-101");
        Assert.assertTrue("Test 1: isInt(\"-101\"): ", ret == true);

        ret = Converter.isInt("-1K01");
        Assert.assertTrue("Test 2: isInt(\"-1K01\"): ", ret == false);

        ret = Converter.isInt("-1.01");
        Assert.assertTrue("Test 3: isInt(\"-1.01\"): ", ret == false);
    }


    /**
     * Unit Test del metodo .isFloat().
     */
    public void testIsFloat() {
        boolean ret;

        ret = Converter.isFloat("-101,50");
        Assert.assertTrue("Test 1: toFloatValue(\"-101,50\"): ", ret == false);

        ret = Converter.isFloat("-1K01");
        Assert.assertTrue("Test 2: toFloatValue(\"-1K01\"): ", ret == false);

        ret = Converter.isFloat("-1.01");
        Assert.assertTrue("Test 3: toFloatValue(\"-1.01\"): ", ret == true);

        ret = Converter.isFloat("-1.");
        Assert.assertTrue("Test 4: toFloatValue(\"-1.\"): ", ret == true);

        ret = Converter.isFloat(".0");
        Assert.assertTrue("Test 5: toFloatValue(\".0\"): ", ret == true);
    }


    /**
     * Unit Test del metodo .toFloatString().
     */
    public void testToFloatString() {
        String ret;

        ret = Converter.toFloatString(-101.50F,'.',2);
        Assert.assertTrue("Test 1: toFloatString(\"-101,50F\",'.',2): ", ret.equals("-101.50"));

        ret = Converter.toFloatString(-101.50F,'.',1);
        Assert.assertTrue("Test 2: toFloatString(\"-101,50F\",'.',1): ", ret.equals("-101.5"));

        ret = Converter.toFloatString(-101F,'.',2);
        Assert.assertTrue("Test 3: toFloatString(\"-101F\",'.',2): ", ret.equals("-101.00"));

        ret = Converter.toFloatString(-101F,',',2);
        Assert.assertTrue("Test 4: toFloatString(\"-101F\",',',2): ", ret.equals("-101,00"));

        ret = Converter.toFloatString(-101F,'D',2);
        Assert.assertTrue("Test 5: toFloatString(\"-101F\",'D',2): ", ret.equals("-101D00"));

        ret = Converter.toFloatString(-101.112F,'.',0);
        Assert.assertTrue("Test 6: toFloatString(\"-101.112F\",'.',0): ", ret.equals("-101"));

        ret = Converter.toFloatString(-101F,'.',0);
        Assert.assertTrue("Test 7: toFloatString(\"-101F\",'.',0): ", ret.equals("-101"));
    }


    public void testHashtable() {
    	Hashtable hash = new Hashtable();
    	String shash1 = "name1=value1;name2=value2;name3=value3;name4=value4";
    	hash.put("name1", "value1");
    	hash.put("name2", "value2");
    	hash.put("name3", "value3");
    	hash.put("name4", "value4");
    	assertEquals(Converter.toString(Converter.toHashtable(shash1, ";"), ";"), Converter.toString(hash, ";"));
    }
}