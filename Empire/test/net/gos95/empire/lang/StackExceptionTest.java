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


import net.gos95.empire.lang.StackException;
import junit.framework.TestCase;


public class StackExceptionTest
extends TestCase
{
	private StackException stackException, stackException1;


	public StackExceptionTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testStackException()
    {
    	stackException = new StackException();
    	assertEquals("null", stackException.getStackMessages());
    	assertNull(stackException.getMessage());
    	assertNull(stackException.getObject());
    	assertEquals(StackException.class.getName(), stackException.toStackString());
    	assertEquals(StackException.class.getName(), stackException.toString());
    	assertEquals(0, stackException.getStack().size());
    }


	public void testStackException1()
    {
		String message = "a message for this exception";
    	stackException = new StackException(message);
    	assertEquals(message, stackException.getStackMessages());
    	assertEquals(message, stackException.getMessage());
    	assertNull(stackException.getObject());
    	assertEquals(StackException.class.getName()+": "+message, stackException.toStackString());
    	assertEquals(StackException.class.getName()+": "+message, stackException.toString());
    	assertEquals(0, stackException.getStack().size());    	
    }


	public void testStackException2()
    {
		Object obj = new Integer(37);
    	stackException = new StackException(obj);
    	assertEquals("null", stackException.getStackMessages());
    	assertNull(stackException.getMessage());
    	assertEquals(obj, stackException.getObject());
    	assertEquals(StackException.class.getName(), stackException.toStackString());
    	assertEquals(StackException.class.getName(), stackException.toString());
    	assertEquals(0, stackException.getStack().size());    	
    }


	public void testStackException3()
    {
		String message = "a message for this exception";
		Object obj = new Integer(37);
    	stackException = new StackException(message, obj);
    	assertEquals(message, stackException.getStackMessages());
    	assertEquals(message, stackException.getMessage());
    	assertEquals(obj, stackException.getObject());
    	assertEquals(StackException.class.getName()+": "+message, stackException.toStackString());
    	assertEquals(StackException.class.getName()+": "+message, stackException.toString());
    	assertEquals(obj, stackException.getObject());
    	assertEquals(0, stackException.getStack().size());    	
    }

	public void testStackException4()
	{
		String message = "a message for this exception";
		Exception exception = new Exception(message);
		stackException = new StackException(exception);
		assertEquals(message+System.getProperty("line.separator")+message, stackException.getStackMessages());
		assertEquals(message, stackException.getMessage());
		assertNull(stackException.getObject());
//		assertEquals(exception.getClass().getName() + ": " + message + System.getProperty("line.separator")
//				   + StackException.class.getName() + ": " + message, stackException.toStackString());
		assertEquals(StackException.class.getName()+": "+message, stackException.toString());
		assertEquals(1, stackException.getStack().size());
	}


	public void testStackException5()
	{
		String message = "a message for this exception";
		String message1 = "another message for this exception";
		Exception exception = new Exception(message);
		stackException = new StackException(exception, message1);
		assertEquals(message+System.getProperty("line.separator")+message1, stackException.getStackMessages());
		assertEquals(message1, stackException.getMessage());
		assertNull(stackException.getObject());
//		assertEquals(exception.getClass().getName() + ": " + message + System.getProperty("line.separator")
//				   + StackException.class.getName() + ": " + message1, stackException.toStackString());
		assertEquals(StackException.class.getName()+": "+message1, stackException.toString());
		assertEquals(1, stackException.getStack().size());
	}


	public void testStackException6()
	{
		String message = "a message for this exception";
		String message1 = "yet another message for this exception";
		String message2 = "another message for this exception";
		Exception exception = new Exception(message);
		stackException1 = new StackException(exception, message2);
		stackException = new StackException(stackException1, message1);
		assertEquals(message+System.getProperty("line.separator")+message2+System.getProperty("line.separator")+message1, stackException.getStackMessages());
		assertEquals(message1, stackException.getMessage());
		assertNull(stackException.getObject());
//		assertEquals(exception.getClass().getName() + ": " + message + System.getProperty("line.separator")
//				   + StackException.class.getName() + ": " + message2 + System.getProperty("line.separator")
//				   + StackException.class.getName() + ": " + message1, stackException.toStackString());
		assertEquals(StackException.class.getName()+": "+message1, stackException.toString());
		assertEquals(2, stackException.getStack().size());
	}
}
