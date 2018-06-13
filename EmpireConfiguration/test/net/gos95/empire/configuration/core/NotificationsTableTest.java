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


import net.gos95.empire.EmpireConfigurationModuleTest;
import junit.framework.TestCase;


public class NotificationsTableTest
extends TestCase
{
	private NotificationsTable messages;


    public NotificationsTableTest(String name)
	{
		super(name);
	}


	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testNotificationsLoad()
	{
		// Test con puntamento al file dei messaggi di notifica errato.
		try {
			messages = new NotificationsTable("XXX");
			fail();
		} catch (Exception e) {}

		// Test con puntamento al file dei messaggi di notifica corretto.
		try {
			messages = new NotificationsTable(EmpireConfigurationModuleTest.notificationsFile);
			assertEquals(messages.INVALID_PROPERTIES_FILE_MESSAGE, EmpireConfigurationModuleTest.invalid_properties_file_exeption_message);
			assertEquals(messages.INVALID_DEFINITIONS_FILE_MESSAGE, EmpireConfigurationModuleTest.invalid_definitions_file_exeption_message);
			assertEquals(messages.INVALID_PROPERTY_DEF_MESSAGE, EmpireConfigurationModuleTest.invalid_property_def_exeption_message);
			assertEquals(messages.INVALID_PROPERTY_VALUE_MESSAGE, EmpireConfigurationModuleTest.invalid_property_value_exeption_message);
			assertEquals(messages.MISSING_PROPERTY_VALUE_MESSAGE, EmpireConfigurationModuleTest.missing_property_value_exeption_message);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
