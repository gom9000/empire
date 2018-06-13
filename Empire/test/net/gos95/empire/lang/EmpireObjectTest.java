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


import net.gos95.empire.lang.EmpireObject;
import junit.framework.TestCase;


public class EmpireObjectTest
extends TestCase
{
	private EmpireObject empireObject1;
	private EmpireObject empireObject2;

	public EmpireObjectTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testEmpireObject()
	{
		empireObject1 = new EmpireObject();
		empireObject2 = new EmpireObject(EmpireObjectTest.class, 101);
		empireObject1.version();
		empireObject2.version();
	}


	public void testEmpireLibraryConstants()
    {
    	System.out.println("Version: " + EmpireObject.version);
    	System.out.println("Vendor: " + EmpireObject.vendor);
    	System.out.println("Name: " + EmpireObject.name);
    	System.out.println("Build time: " + EmpireObject.buildtime);
    	System.out.println("Author: " + EmpireObject.author);
    }
}
