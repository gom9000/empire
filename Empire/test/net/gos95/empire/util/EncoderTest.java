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


import net.gos95.empire.util.Encoder;
import junit.framework.*;


public class EncoderTest
extends TestCase
{
	public EncoderTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	/**
     * Unit Test del metodo rot13().
     */
    public void testRot13() {
    	String in_code = "a4!sTrInGa Da rUoTaRe!?*é";
    	String ou_code = "n4!fGeVaTn Qn eHbGnEr!?*é";
    	Assert.assertEquals(ou_code, Encoder.rot13(in_code));
    }


	/**
     * Unit Test del metodo encrypt e isEqual().
     */
    public void testEncrypt() {
    	String in_code1 = "123prova456!?*7890";
    	String in_code2 = "123prova456!?*7890";
    	String in_code3 = "XXXprova456!?*7890";
    	String ou_code_md5 = "ffffffce765affffffba5757ffffff860c36ffffffbf6c33ffffffbaffffff9b0d2e";
    	String ou_code_sha = "ffffffb3ffffffc91847ffffffd94977ffffffac2418ffffffa646ffffffcdffffff8f0bffffff96ffffffcc355dffffffb1";
    	String ou_code1, ou_code2, ou_code3;
    	
    	try {
    		// MD5
			Assert.assertEquals(ou_code_md5, Encoder.hexcrypt(in_code1, Encoder.MD5));
			Assert.assertEquals(ou_code_md5, Encoder.hexcrypt(in_code1, Encoder.MD5));
			Assert.assertFalse(ou_code_md5.equals(Encoder.hexcrypt(in_code3, Encoder.MD5)));

			ou_code1 = Encoder.hexcrypt(in_code1, Encoder.MD5);
			ou_code2 = Encoder.hexcrypt(in_code2, Encoder.MD5);
			ou_code3 = Encoder.hexcrypt(in_code3, Encoder.MD5);
			Assert.assertTrue(Encoder.isEqual(ou_code1, ou_code_md5, Encoder.MD5));
			Assert.assertTrue(Encoder.isEqual(ou_code2, ou_code_md5, Encoder.MD5));
			Assert.assertFalse(Encoder.isEqual(ou_code3, ou_code_md5, Encoder.MD5));

            // SHA
			Assert.assertEquals(ou_code_sha, Encoder.hexcrypt(in_code1, Encoder.SHA));
			Assert.assertEquals(ou_code_sha, Encoder.hexcrypt(in_code1, Encoder.SHA));
			Assert.assertFalse(ou_code_sha.equals(Encoder.hexcrypt(in_code3, Encoder.SHA)));

			ou_code1 = Encoder.hexcrypt(in_code1, Encoder.SHA);
			ou_code2 = Encoder.hexcrypt(in_code2, Encoder.SHA);
			ou_code3 = Encoder.hexcrypt(in_code3, Encoder.SHA);
			Assert.assertTrue(Encoder.isEqual(ou_code1, ou_code_sha, Encoder.SHA));
			Assert.assertTrue(Encoder.isEqual(ou_code2, ou_code_sha, Encoder.SHA));
			Assert.assertFalse(Encoder.isEqual(ou_code3, ou_code_sha, Encoder.SHA));

    	} catch (EncoderException e) {
			Assert.fail(e.toString());
		}
    }
}
