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


package net.gos95.empire.configuration.core;


import java.util.Hashtable;

import net.gos95.empire.EmpireConfigurationModuleTest;

import junit.framework.TestCase;


public class DefinitionsTableTest
extends TestCase
{
	private DefinitionsTable definitions;


    public DefinitionsTableTest(String name)
	{
		super(name);
	}


	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testDefnitionsLoad()
	{
		// Test con puntamento al file delle definizioni errato.
		try {
			definitions = new DefinitionsTable("XXX");
			fail();
		} catch (Exception ignore) {}

		// Test con puntamento al file delle definizioni corretto.
		try {
			definitions = new DefinitionsTable(EmpireConfigurationModuleTest.definitionsFile);
		} catch (Exception e) {
			fail(e.toString());
		}
	}


	public void testGetTable()
	{
		Hashtable table;

		try {
			definitions = new DefinitionsTable(EmpireConfigurationModuleTest.definitionsFile);
			table = definitions.getTable();

			ManagedProperty p1 = (ManagedProperty)table.get("PROPERTY_S1");
			assertEquals(p1.alias, "PROPERTY_S1");
			assertFalse(p1.checked);		
			assertNull(p1.defaultValue);
			assertFalse(p1.defaultValueUsed);
			assertTrue(p1.mandatory);
			assertEquals(p1.name, "property-s1");
			assertNull(p1.value);
			assertEquals(p1.datatype, "STRING");

			p1 = (ManagedProperty)table.get("PROPERTY_SA4");
			assertEquals(p1.alias, "PROPERTY_SA4");
			assertFalse(p1.checked);
			assertEquals(p1.defaultValue, "a,b,c,d");
			assertFalse(p1.defaultValueUsed);
			assertFalse(p1.mandatory);
			assertEquals(p1.name, "property-sa4");
			assertNull(p1.value);
			assertEquals(p1.datatype, "ASTRING");

			p1 = (ManagedProperty)table.get("PROPERTY_I2");
			assertEquals(p1.alias, "PROPERTY_I2");
			assertFalse(p1.checked);
			assertEquals(p1.defaultValue, "10000");
			assertFalse(p1.defaultValueUsed);
			assertTrue(p1.mandatory);
			assertEquals(p1.name, "property-i2");
			assertNull(p1.value);
			assertEquals(p1.datatype, "INTEGER");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
