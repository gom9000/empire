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


package net.gos95.empire.lang;


import net.gos95.empire.lang.support.ValueObjectExtends;
import junit.framework.TestCase;


public class ValueObjectTest
extends TestCase
{
    public ValueObjectExtends vo1, vo2, vo3;


	public ValueObjectTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
		vo1 = new ValueObjectExtends();
		vo2 = new ValueObjectExtends();
		vo3 = new ValueObjectExtends();
        vo3.setPrivateFloatAttribute1(11.34F);
	}


    public void testHashCode()
    {
    	assertEquals(vo1.hashCode(), vo1.hashCode());
    	assertEquals(vo1.hashCode(), vo2.hashCode());
        assertFalse(vo1.hashCode() == vo3.hashCode());
    }

    public void testEquals()
    {
        assertFalse(vo1.equals(new Object()));
        assertFalse(vo1.equals(vo3));
        assertTrue(vo1.equals(vo1));
        assertEquals(vo1, vo2);
    }
}
