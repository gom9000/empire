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


import net.gos95.empire.util.CodeValidator;
import junit.framework.*;


public class CodeValidatorTest
extends TestCase
{
	public CodeValidatorTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testValidate()
	{
		String codeToValidate = "prova123!";
		CodeValidator cv = new CodeValidator();
		cv.setMinLength(9);
		cv.setMaxLength(10);
		cv.setMinAlphaChars(3);
		cv.setMaxAlphaChars(5);
		cv.setMinDigitChars(3);
		cv.setMaxDigitChars(5);
		cv.setSpecialSet(".;$!-");
		cv.setMinSpecialChars(1);
		cv.setMaxSpecialChars(1);
		cv.setCharSet(cv.ALPHA_SET_FLAG+cv.DIGIT_SET_FLAG+cv.SPECIAL_SET_FLAG);
		cv.setMaxNearbyEqualChars(3);
		assertEquals(cv.VALID_CODE, cv.validate(codeToValidate));
		assertEquals(cv.FEW_CHARS, cv.validate("prov123!"));
		assertEquals(cv.FEW_ALPHA_CHARS, cv.validate("pr...123!"));
		assertEquals(cv.FEW_DIGIT_CHARS, cv.validate("prova...!"));
		assertEquals(cv.FEW_SPECIAL_CHARS, cv.validate("prova1234"));
		assertEquals(cv.TOO_MANY_SPECIAL_CHARS, cv.validate("prov.123!"));
		assertEquals(cv.NOT_ALLOWED_CHARS, cv.validate("prova123>"));
	}
}
