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
import net.gos95.empire.configuration.ConfigurationException;
import junit.framework.TestCase;


public class PropertiesLoaderTest
extends TestCase
{
	private PropertiesLoader config;


    public PropertiesLoaderTest(String name)
	{
		super(name);
	}


	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testCreatePropertiesLoader()
	{
		// Test con puntamento al file dei messaggi di notifica errato.
		try {
			config = new PropertiesLoader("XXX");
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(NotificationsTable.INVALID_NOTIFICATIONS_FILE));
		}

		// Test con puntamento al file dei messaggi di notifica corretto.
		try {
			config = new PropertiesLoader(EmpireConfigurationModuleTest.notificationsFile);
		} catch (ConfigurationException e) {
			fail(e.toString());
		}
	}


	public void testLoadFile()
	{
		this.testCreatePropertiesLoader();

		// Test con puntamento a file di properties errato.
		try {
			config.load("XXX");
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_properties_file_exeption_message));
		}

		// Test con puntamento a file di properties corretto.
		try {
			config.load(EmpireConfigurationModuleTest.testCorePropFile);
		} catch (ConfigurationException e) {
			fail(e.toString() + e.getInternalMessage());
		}
	}


	public void testLoadPropFile()
	{
		this.testCreatePropertiesLoader();

		// Test di caricamento .properties.
		try {
			config.load(EmpireConfigurationModuleTest.testCorePropFile);
		} catch (ConfigurationException e) {
			fail(e.toString() + e.getInternalMessage());
		}

		assertEquals(4, config.getProperties().size());
		assertEquals(1, config.getConfigurationFileList().size());
		assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCorePropFile));
		assertEquals("value1", config.getProperties().getProperty("property11"));
	}


	public void testLoadXMLFile()
	{
		this.testCreatePropertiesLoader();

		// Test di caricamento .xml.
		try {
			config.load(EmpireConfigurationModuleTest.testCoreXmlFile);
		} catch (ConfigurationException e) {
			fail(e.toString() + e.getInternalMessage());
		}

		assertEquals(5, config.getProperties().size());
		assertEquals(1, config.getConfigurationFileList().size());
		assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCoreXmlFile));
		assertEquals("value9", config.getProperties().getProperty("property91"));
	}


	public void testLoadMultipleFiles()
	{
		this.testCreatePropertiesLoader();

		// Test di caricamento files multipli.
		try {
			config.load(EmpireConfigurationModuleTest.testCorePropFile);
			config.load(EmpireConfigurationModuleTest.testCoreXmlFile);
		} catch (ConfigurationException e) {
			fail(e.toString() + e.getInternalMessage());
		}

		assertEquals(9, config.getProperties().size());
		assertEquals(2, config.getConfigurationFileList().size());
		assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCorePropFile));
		assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCoreXmlFile));
		assertEquals("value1", config.getProperties().getProperty("property11"));
		assertEquals("value9", config.getProperties().getProperty("property91"));
	}


    public void testLoadEmbeddedMultipleFile()
    {
		this.testCreatePropertiesLoader();

		// Test di caricamento embedded files .properties.
		try {
			config.load(EmpireConfigurationModuleTest.testCoreEmbeddedFile);
		} catch (ConfigurationException e) {
			fail(e.toString() + " - " + e.getInternalMessage());
		}

    	assertEquals(16, config.getProperties().size());
    	assertEquals(4, config.getConfigurationFileList().size());
    	assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCoreEmbeddedFile));
		assertTrue(config.getConfigurationFileList().contains("test/resources/configuration/core/coreembed2-test.properties"));
		assertTrue(config.getConfigurationFileList().contains("test/resources/configuration/core/coreembed2-test.xml"));
		assertEquals("value2", config.getProperties().getProperty("property21"));
		assertEquals("value8", config.getProperties().getProperty("property31"));
		assertEquals("true", config.getProperties().getProperty("property84"));
		assertEquals("value5", config.getProperties().getProperty("property51"));
    }


    public void testReload()
    {
    	this.testLoadEmbeddedMultipleFile();

    	try {
    		config.reload();
    	} catch (ConfigurationException e) {
			fail(e.toString() + e.getInternalMessage());
		}

    	assertEquals(16, config.getProperties().size());
    	assertEquals(4, config.getConfigurationFileList().size());
    	assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCoreEmbeddedFile));
		assertTrue(config.getConfigurationFileList().contains("test/resources/configuration/core/coreembed2-test.properties"));
		assertTrue(config.getConfigurationFileList().contains("test/resources/configuration/core/coreembed2-test.xml"));
		assertEquals("value2", config.getProperties().getProperty("property21"));
		assertEquals("value8", config.getProperties().getProperty("property31"));
		assertEquals("true", config.getProperties().getProperty("property84"));
		assertEquals("value5", config.getProperties().getProperty("property51"));
    }
}
