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


import java.util.Vector;

import junit.framework.Assert;
import junit.framework.TestCase;


public class VectorBufferTest
extends TestCase
{
	MultiBuffer buffer;
	String buffer_alias1 = "buffer_alias1";
	String buffer_alias2 = "buffer_alias2";
	Vector buffer_value1;
	Vector buffer_value2;

	public VectorBufferTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testVectorBuffer()
	{
		buffer_value1 = new Vector();
		buffer_value1.add("value_1_1"); buffer_value1.add("value_1_2");
		buffer_value2 = new Vector();
		buffer_value2.add("value_2_1"); buffer_value2.add("value_2_2");

		buffer = new VectorBuffer(MultiBuffer.NORMAL_MODE);
		Assert.assertFalse(buffer.isValidBuffer(buffer_alias1));

		buffer.writeBuffer(buffer_alias1, buffer_value1);
		buffer.writeBuffer(buffer_alias2, buffer_value2);
		Assert.assertEquals(buffer_value1, buffer.readBuffer(buffer_alias1));
		Assert.assertTrue(buffer.isValidBuffer(buffer_alias1));
		Assert.assertEquals(buffer_value2, buffer.readBuffer(buffer_alias2));
		Assert.assertTrue(buffer.isValidBuffer(buffer_alias2));

		Vector buffer_value1b = (Vector)buffer_value1.clone();
		buffer_value1.add("value_1_3");
		Assert.assertEquals(buffer_value1b, buffer.readBuffer(buffer_alias1));

		Vector b = (Vector)buffer.cloneBuffer(buffer_alias1);
		b.add("value_XXX");
		Assert.assertEquals(buffer_value1b, buffer.readBuffer(buffer_alias1));

		Vector b2 = (Vector)buffer.readBuffer(buffer_alias2);
		b2.add("value_XXX");
		Assert.assertEquals(b2, buffer.readBuffer(buffer_alias2));		
	}
}
