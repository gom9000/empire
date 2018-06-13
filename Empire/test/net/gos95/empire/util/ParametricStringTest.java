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


import junit.framework.TestCase;


public class ParametricStringTest
extends TestCase
{
	private ParametricString str;


	public ParametricStringTest(String name)
	{
		super(name);
	}


	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testSetObjectParameter()
	{
		// set Object parameter
		str = new ParametricString();
		str.setText("select * from table where col = :str");
		str.setParameter("str", "strvalue");
		assertEquals("select * from table where col = 'strvalue'", str.toString());

		// set multi equals Object parameter
		str = new ParametricString();
		str.setText("select * from table where col1 = :str and col2 = :str");
		str.setParameter("str", "strvalue");
		assertEquals("select * from table where col1 = 'strvalue' and col2 = 'strvalue'", str.toString());

		// set multi Object parameter
		str = new ParametricString();
		str.setText("select * from table where col0 = :str col1 = :str1 and col2 = :str2 and col = :str");
		str.setParameter("str", "strvalue");
		str.setParameter("str1", "strvalue1");
		str.setParameter("str2", "strvalue2");
		assertEquals("select * from table where col0 = 'strvalue' col1 = 'strvalue1' and col2 = 'strvalue2' and col = 'strvalue'", str.toString());

		// set Object parameter with single quote
		str = new ParametricString();
		str.setText("select * from table where col = :str");
		str.setParameter("str", "strvaluewith'singlequote'");
		assertEquals("select * from table where col = 'strvaluewith''singlequote'''", str.toString());

		// set Object parameter with double quote
		str = new ParametricString();
		str.setText("select * from table where col = :str");
		str.setParameter("str", "strvaluewith\"doublequote\"");
		assertEquals("select * from table where col = 'strvaluewith\"doublequote\"'", str.toString());

		// set Object parameter with colon
		str = new ParametricString();
		str.setText("select * from table where col = :str");
		str.setParameter("str", "str:value");
		assertEquals("select * from table where col = 'str:value'", str.toString());
	}


	public void testSetParameter()
	{
		// set boolean parameter
		str = new ParametricString();
		str.setText("select * from table where col = :bool");
		str.setParameter("bool", true);
		assertEquals("select * from table where col = 'true'", str.toString());

		// set char parameter
		str = new ParametricString();
		str.setText("select * from table where col = :char");
		str.setParameter("char", 'c');
		assertEquals("select * from table where col = 'c'", str.toString());

		// set int parameter
		str = new ParametricString();
		str.setText("select * from table where col = :int");
		str.setParameter("int", -101);
		assertEquals("select * from table where col = -101", str.toString());

		// set long parameter
		str = new ParametricString();
		str.setText("select * from table where col = :long");
		str.setParameter("long", 123456789L);
		assertEquals("select * from table where col = 123456789", str.toString());

		// set float parameter
		str = new ParametricString();
		str.setText("select * from table where col = :float");
		str.setParameter("float", 1234.567F);
		assertEquals("select * from table where col = 1234.567", str.toString());

		// set double parameter
		str = new ParametricString();
		str.setText("select * from table where col = :double");
		str.setParameter("double", 12.3456789D);
		assertEquals("select * from table where col = 12.3456789", str.toString());

		// set mixed parameter
		str = new ParametricString();
		str.setText("select * from table where col1 = :bool and col2 = :char and col3 = :int and col4 = :long and col5 = :int");
		str.setParameter("bool", true);
		str.setParameter("char", 'c');
		str.setParameter("int", -101);
		str.setParameter("long", 123456789L);
		assertEquals("select * from table where col1 = 'true' and col2 = 'c' and col3 = -101 and col4 = 123456789 and col5 = -101", str.toString());
	}
}
