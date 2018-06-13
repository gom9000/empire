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


package net.gos95.empire;


import net.gos95.empire.configuration.SimpleConfigurationTest;
import net.gos95.empire.configuration.core.DefinitionsTableTest;
import net.gos95.empire.configuration.core.NotificationsTableTest;
import net.gos95.empire.configuration.core.PropertiesLoaderTest;

import junit.framework.Test;
import junit.framework.TestSuite;


public class EmpireConfigurationModuleTest
{
	// configuration test-files...
	static public String testCorePropFile = "test/resources/configuration/core/coreconf-test.properties";
	static public String testCoreXmlFile = "test/resources/configuration/core/coreconf-test.xml";
	static public String testCoreEmbeddedFile = "test/resources/configuration/core/coreembed-test.properties";
	static public String testSimpleFile = "test/resources/configuration/simpleconf-test.properties";
	static public String notificationsFile = "test/resources/configuration/core/notifications-test.properties";
	static public String definitionsFile = "test/resources/configuration/core/defs-test.properties";

	// notification messages...
	static public String invalid_properties_file_exeption_message = "Impossibile aprire il file di configurazione specificato";
	static public String invalid_definitions_file_exeption_message = "Impossibile aprire il file delle definizioni";
	static public String invalid_property_def_exeption_message = "Definizione di dato non valido per la proprietà di configurazione specificata";
	static public String invalid_property_value_exeption_message = "Formato di dato non valido per la proprietà di configurazione specificata";
	static public String missing_property_value_exeption_message = "Valore non presente per la proprietà di configurazione specificata";


	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Configuration Module");

        suite.addTestSuite(NotificationsTableTest.class);
        suite.addTestSuite(PropertiesLoaderTest.class);
        suite.addTestSuite(SimpleConfigurationTest.class);
        suite.addTestSuite(DefinitionsTableTest.class);

		return suite;
	}
}
