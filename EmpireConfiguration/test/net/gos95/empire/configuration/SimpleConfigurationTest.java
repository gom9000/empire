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


package net.gos95.empire.configuration;


import net.gos95.empire.EmpireConfigurationModuleTest;
import junit.framework.TestCase;


public class SimpleConfigurationTest
extends TestCase
{
	private SimpleConfiguration config;


    public SimpleConfigurationTest(String name)
	{
		super(name);
	}


	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testSimpleConfiguration()
	{
	    SimpleConfiguration.NOTIFICATIONS_FILE = EmpireConfigurationModuleTest.notificationsFile;   

	    try {
			config = new SimpleConfiguration();
		} catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config = new SimpleConfiguration("XXX");
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_properties_file_exeption_message));
		}

		try {
			config = new SimpleConfiguration(EmpireConfigurationModuleTest.testCorePropFile);
			assertEquals(4, config.getProperties().size());
			assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCorePropFile));
			assertEquals("value1", config.getProperties().getProperty("property11"));
			assertEquals("12 ", config.getProperties().getProperty("property12"));
			assertEquals("v1,v2, v3", config.getProperties().getProperty("property13"));
			assertEquals("true", config.getProperties().getProperty("property14"));
			assertNull(config.getProperties().getProperty("XXX"));

			config.load(EmpireConfigurationModuleTest.testCoreXmlFile);
			assertEquals(9, config.getProperties().size());
			assertTrue(config.getConfigurationFileList().contains(EmpireConfigurationModuleTest.testCoreXmlFile));
			assertEquals("value9", config.getProperties().getProperty("property91"));
			assertEquals("913 ", config.getProperties().getProperty("property92"));
			assertEquals("v931,v932, v933", config.getProperties().getProperty("property93"));
			assertEquals("true", config.getProperties().getProperty("property94"));
		} catch (ConfigurationException e) {
			fail(e.toString());
		}
	}


    public void testGetStringValue()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			assertEquals("value1", config.getStringValue("string1", false));
			assertEquals("value1", config.getStringValue("string1", true));
			assertEquals("value2", config.getStringValue("string2", false));
			assertEquals("value2", config.getStringValue("string2", true));
			assertEquals("v a l u  e   3", config.getStringValue("string3", false));
			assertEquals("v a l u  e   3", config.getStringValue("string3", true));
			assertEquals("  v a l u  \"   4 ", config.getStringValue("string4", false));
			assertEquals("  v a l u  \"   4 ", config.getStringValue("string4", true));
			assertNull(config.getStringValue("string5", false));
			assertNull(config.getStringValue("string6", false));
			assertEquals("               ", config.getStringValue("string7", false));
			assertEquals("               ", config.getStringValue("string7", true));
			assertNull(config.getStringValue("string8", false));
			assertNull(config.getStringValue("XXX", false));
		} catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			assertNull(config.getStringValue("string5", true));
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			assertNull(config.getStringValue("string6", true));
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			assertNull(config.getStringValue("string8", true));
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			assertNull(config.getStringValue("XXX", true));
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }


    public void testGetStringValues()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			String array1[] = config.getStringValues("array1", ",", false);
			assertEquals(5, array1.length);
			assertEquals("value1", array1[0]);
			assertEquals("value2", array1[1]);
			assertEquals("value3", array1[2]);
			assertEquals("value4", array1[3]);
			assertEquals("value5", array1[4]);
			array1 = config.getStringValues("array1", ",", true);
			assertEquals(5, array1.length);
			assertEquals("value1", array1[0]);
			assertEquals("value2", array1[1]);
			assertEquals("value3", array1[2]);
			assertEquals("value4", array1[3]);
			assertEquals("value5", array1[4]);
			
			String array2[] = config.getStringValues("array2", ",", false);
			assertEquals(1, array2.length);
			assertEquals("value1", array2[0]);
		    array2 = config.getStringValues("array2", ",", true);
			assertEquals(1, array2.length);
			assertEquals("value1", array2[0]);

			String array3[] = config.getStringValues("array3", ",", false);
			assertEquals(1, array3.length);
			assertEquals("value1", array3[0]);
			array3 = config.getStringValues("array3", ",", true);
			assertEquals(1, array3.length);
			assertEquals("value1", array3[0]);

			String array4[] = config.getStringValues("array4", ",", false);
			assertEquals(4, array4.length);
			assertEquals("value1", array4[0]);
			assertNull(array4[1]);
			assertNull(array4[2]);
			assertEquals("value4", array4[3]);
			array4 = config.getStringValues("array4", ",", true);
			assertEquals(4, array4.length);
			assertEquals("value1", array4[0]);
			assertNull(array4[1]);
			assertNull(array4[2]);
			assertEquals("value4", array4[3]);

			String array5[] = config.getStringValues("array5", ",", false);
			assertEquals(4, array5.length);
			assertEquals("value1", array5[0]);
			assertEquals("v a l u e  2", array5[1]);
			assertEquals("  v a l u e  3 ", array5[2]);
			assertEquals("  v a \"lu\" e 4 ", array5[3]);
			array5 = config.getStringValues("array5", ",", true);
			assertEquals(4, array5.length);
			assertEquals("value1", array5[0]);
			assertEquals("v a l u e  2", array5[1]);
			assertEquals("  v a l u e  3 ", array5[2]);
			assertEquals("  v a \"lu\" e 4 ", array5[3]);

			String array6[] = config.getStringValues("array6", ",", false);
			assertEquals(1, array6.length);
			assertEquals("value1", array6[0]);
		    array6 = config.getStringValues("array6", ",", true);
			assertEquals(1, array6.length);
			assertEquals("value1", array6[0]);

			String array7[] = config.getStringValues("array7", ",", false);
			assertNull(array7);

			String array8[] = config.getStringValues("array8", ",", false);
			assertNull(array8);

			String array9[] = config.getStringValues("array9", ",", false);
			assertNull(array9);

	    } catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getStringValues("array7", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getStringValues("array8", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getStringValues("array9", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }


    public void testGetIntegerValue()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			assertEquals(11, config.getIntegerValue("integer1", false).intValue());
			assertEquals(11, config.getIntegerValue("integer1", true).intValue());
			assertEquals(12, config.getIntegerValue("integer2", false).intValue());
			assertEquals(12, config.getIntegerValue("integer2", true).intValue());
			assertNull(config.getIntegerValue("integer7", false));
			assertNull(config.getIntegerValue("integer8", false));
		} catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getIntegerValue("integer3", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getIntegerValue("integer3", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValue("integer4", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getIntegerValue("integer4", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValue("integer5", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getIntegerValue("integer5", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValue("integer6", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getIntegerValue("integer6", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValue("integer7", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValue("integer8", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
    }


    public void testGetIntegerValues()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			Integer iarray1[] = config.getIntegerValues("iarray1", ",", false);
			assertEquals(5, iarray1.length);
			assertEquals(1, iarray1[0].intValue());
			assertEquals(2, iarray1[1].intValue());
			assertEquals(3, iarray1[2].intValue());
			assertEquals(4, iarray1[3].intValue());
			assertEquals(5, iarray1[4].intValue());
			iarray1 = config.getIntegerValues("iarray1", ",", true);
			assertEquals(5, iarray1.length);
			assertEquals(1, iarray1[0].intValue());
			assertEquals(2, iarray1[1].intValue());
			assertEquals(3, iarray1[2].intValue());
			assertEquals(4, iarray1[3].intValue());
			assertEquals(5, iarray1[4].intValue());

			Integer iarray2[] = config.getIntegerValues("iarray2", ",", false);
			assertEquals(1, iarray2.length);
			assertEquals(1, iarray2[0].intValue());
		    iarray2 = config.getIntegerValues("iarray2", ",", true);
			assertEquals(1, iarray2.length);
			assertEquals(1, iarray2[0].intValue());

			Integer iarray3[] = config.getIntegerValues("iarray3", ",", false);
			assertEquals(1, iarray3.length);
			assertEquals(1, iarray3[0].intValue());
			iarray3 = config.getIntegerValues("iarray3", ",", true);
			assertEquals(1, iarray3.length);
			assertEquals(1, iarray3[0].intValue());
			
			Integer iarray4[] = config.getIntegerValues("iarray4", ",", false);
			assertEquals(4, iarray4.length);
			assertEquals(1, iarray4[0].intValue());
			assertNull(iarray4[1]);
			assertNull(iarray4[2]);
			assertEquals(4, iarray4[3].intValue());
			iarray4 = config.getIntegerValues("iarray4", ",", true);
			assertEquals(4, iarray4.length);
			assertEquals(1, iarray4[0].intValue());
			assertNull(iarray4[1]);
			assertNull(iarray4[2]);
			assertEquals(4, iarray4[3].intValue());

			Integer iarray6[] = config.getIntegerValues("iarray6", ",", false);
			assertEquals(1, iarray6.length);
			assertEquals(1, iarray6[0].intValue());
		    iarray6 = config.getIntegerValues("iarray6", ",", true);
			assertEquals(1, iarray6.length);
			assertEquals(1, iarray6[0].intValue());

			Integer iarray7[] = config.getIntegerValues("iarray7", ",", false);
			assertNull(iarray7);

			Integer iarray8[] = config.getIntegerValues("iarray8", ",", false);
			assertNull(iarray8);

			Integer iarray9[] = config.getIntegerValues("iarray9", ",", false);
			assertNull(iarray9);
	    } catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getIntegerValues("iarray5", ",", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getIntegerValues("iarray7", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getStringValues("iarray8", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getStringValues("iarray9", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }


    public void testGetBooleanValue()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			assertTrue(config.getBooleanValue("bool1", false).booleanValue());
			assertTrue(config.getBooleanValue("bool1", true).booleanValue());
			assertFalse(config.getBooleanValue("bool2", false).booleanValue());
			assertFalse(config.getBooleanValue("bool2", true).booleanValue());

			assertTrue(config.getBooleanValue("bool3", false).booleanValue());
			assertTrue(config.getBooleanValue("bool3", true).booleanValue());
			assertFalse(config.getBooleanValue("bool4", false).booleanValue());
			assertFalse(config.getBooleanValue("bool4", true).booleanValue());

			assertTrue(config.getBooleanValue("bool5", false).booleanValue());
			assertTrue(config.getBooleanValue("bool5", true).booleanValue());
			assertFalse(config.getBooleanValue("bool6", false).booleanValue());
			assertFalse(config.getBooleanValue("bool6", true).booleanValue());

			assertTrue(config.getBooleanValue("bool7", false).booleanValue());
			assertTrue(config.getBooleanValue("bool7", true).booleanValue());
			assertFalse(config.getBooleanValue("bool8", false).booleanValue());
			assertFalse(config.getBooleanValue("bool8", true).booleanValue());

			assertFalse(config.getBooleanValue("bool10", false).booleanValue());
			assertFalse(config.getBooleanValue("bool11", false).booleanValue());
	    } catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getBooleanValue("bool9", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getBooleanValue("bool9", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getBooleanValue("bool10", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getBooleanValue("bool11", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }


    public void testGetBooleanValues()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			Boolean barray1[] = config.getBooleanValues("barray1", ",", false);
			assertEquals(5, barray1.length);
			assertTrue(barray1[0].booleanValue());
			assertFalse(barray1[1].booleanValue());
			assertTrue(barray1[2].booleanValue());
			assertFalse(barray1[3].booleanValue());
			assertTrue(barray1[4].booleanValue());
			barray1 = config.getBooleanValues("barray1", ",", true);
			assertTrue(barray1[0].booleanValue());
			assertFalse(barray1[1].booleanValue());
			assertTrue(barray1[2].booleanValue());
			assertFalse(barray1[3].booleanValue());
			assertTrue(barray1[4].booleanValue());

			Boolean barray2[] = config.getBooleanValues("barray2", ",", false);
			assertEquals(1, barray2.length);
			assertTrue(barray2[0].booleanValue());
		    barray2 = config.getBooleanValues("barray2", ",", true);
			assertEquals(1, barray2.length);
			assertTrue(barray2[0].booleanValue());

			Boolean barray3[] = config.getBooleanValues("barray3", ",", false);
			assertEquals(1, barray3.length);
			assertTrue(barray3[0].booleanValue());
			barray3 = config.getBooleanValues("barray3", ",", true);
			assertEquals(1, barray3.length);
			assertTrue(barray3[0].booleanValue());

			Boolean barray4[] = config.getBooleanValues("barray4", ",", false);
			assertEquals(4, barray4.length);
			assertTrue(barray4[0].booleanValue());
			assertNull(barray4[1]);
			assertNull(barray4[2]);
			assertTrue(barray4[3].booleanValue());
			barray4 = config.getBooleanValues("barray4", ",", true);
			assertTrue(barray4[0].booleanValue());
			assertNull(barray4[1]);
			assertNull(barray4[2]);
			assertTrue(barray4[3].booleanValue());

			Boolean barray6[] = config.getBooleanValues("barray6", ",", false);
			assertEquals(1, barray6.length);
			assertTrue(barray6[0].booleanValue());
		    barray6 = config.getBooleanValues("barray6", ",", true);
			assertEquals(1, barray6.length);
			assertTrue(barray6[0].booleanValue());

			Boolean barray7[] = config.getBooleanValues("barray7", ",", false);
			assertNull(barray7);

			Boolean barray8[] = config.getBooleanValues("barray8", ",", false);
			assertNull(barray8);

			Boolean barray9[] = config.getBooleanValues("barray9", ",", false);
			assertNull(barray9);
	    } catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getBooleanValues("barray5", ",", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getBooleanValues("barray7", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getBooleanValues("barray8", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getBooleanValues("barray9", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }


    public void testGetDoubleValue()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			assertEquals(11.055D, config.getDoubleValue("double1", false).doubleValue(), 11.055);
			assertEquals(12.3D, config.getDoubleValue("double1", true).doubleValue(), 12.3D);
			assertEquals(12.3D, config.getDoubleValue("double2", false).doubleValue(), 12.3D);
			assertEquals(12.3D, config.getDoubleValue("double2", true).doubleValue(), 12.3D);
			assertNull(config.getDoubleValue("double7", false));
			assertNull(config.getDoubleValue("double8", false));
		} catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getDoubleValue("double3", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getDoubleValue("double3", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getDoubleValue("double5", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getDoubleValue("double5", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getDoubleValue("double6", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
		try {
			config.getDoubleValue("double6", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getDoubleValue("double7", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getDoubleValue("double8", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}
    }


    public void testGetDoubleValues()
    {
	    try {
			config = new SimpleConfiguration();
			config.load(EmpireConfigurationModuleTest.testSimpleFile);

			Double iarray1[] = config.getDoubleValues("darray1", ",", false);
			assertEquals(5, iarray1.length);
			assertEquals(1.11, iarray1[0].doubleValue(), 1.11);
			assertEquals(2.22, iarray1[1].doubleValue(), 2.22);
			assertEquals(3.33, iarray1[2].doubleValue(), 3.33);
			assertEquals(4.44, iarray1[3].doubleValue(), 4.44);
			assertEquals(5.55, iarray1[4].doubleValue(), 5.55);
			iarray1 = config.getDoubleValues("darray1", ",", true);
			assertEquals(5, iarray1.length);
			assertEquals(1.11, iarray1[0].doubleValue(), 1.11);
			assertEquals(2.22, iarray1[1].doubleValue(), 2.22);
			assertEquals(3.33, iarray1[2].doubleValue(), 3.33);
			assertEquals(4.44, iarray1[3].doubleValue(), 4.44);
			assertEquals(5.55, iarray1[4].doubleValue(), 5.55);

			Double iarray2[] = config.getDoubleValues("darray2", ",", false);
			assertEquals(1, iarray2.length);
			assertEquals(1, iarray2[0].doubleValue(), 1);
		    iarray2 = config.getDoubleValues("darray2", ",", true);
			assertEquals(1, iarray2.length);
			assertEquals(1, iarray2[0].doubleValue(), 1);

			Double iarray3[] = config.getDoubleValues("darray3", ",", false);
			assertEquals(1, iarray3.length);
			assertEquals(1, iarray3[0].doubleValue(), 1);
			iarray3 = config.getDoubleValues("darray3", ",", true);
			assertEquals(1, iarray3.length);
			assertEquals(1, iarray3[0].doubleValue(), 1);
			
			Double iarray4[] = config.getDoubleValues("darray4", ",", false);
			assertEquals(4, iarray4.length);
			assertEquals(1, iarray4[0].doubleValue(), 1);
			assertNull(iarray4[1]);
			assertNull(iarray4[2]);
			assertEquals(4, iarray4[3].doubleValue(), 1);
			iarray4 = config.getDoubleValues("darray4", ",", true);
			assertEquals(4, iarray4.length);
			assertEquals(1, iarray4[0].doubleValue(), 1);
			assertNull(iarray4[1]);
			assertNull(iarray4[2]);
			assertEquals(4.72, iarray4[3].doubleValue(), 4.72);

			Double iarray6[] = config.getDoubleValues("darray6", ",", false);
			assertEquals(1, iarray6.length);
			assertEquals(1, iarray6[0].doubleValue(), 1);
		    iarray6 = config.getDoubleValues("darray6", ",", true);
			assertEquals(1, iarray6.length);
			assertEquals(1, iarray6[0].doubleValue(), 1);

			Double iarray7[] = config.getDoubleValues("darray7", ",", false);
			assertNull(iarray7);

			Double iarray8[] = config.getDoubleValues("darray8", ",", false);
			assertNull(iarray8);

			Double iarray9[] = config.getDoubleValues("darray9", ",", false);
			assertNull(iarray9);
	    } catch (ConfigurationException e) {
			fail(e.toString());
		}

		try {
			config.getDoubleValues("darray5", ",", false);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.invalid_property_value_exeption_message));
		}

		try {
			config.getDoubleValues("darray7", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getDoubleValues("darray8", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}

		try {
			config.getDoubleValues("darray9", ",", true);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(e.toString().contains(EmpireConfigurationModuleTest.missing_property_value_exeption_message));
		}
    }
}
