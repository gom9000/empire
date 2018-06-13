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


import net.gos95.empire.dto.DtoTest;
import net.gos95.empire.lang.EmpireObjectTest;
import net.gos95.empire.lang.StackExceptionTest;
import net.gos95.empire.lang.ValueObjectTest;
import net.gos95.empire.util.CodeValidatorTest;
import net.gos95.empire.util.ConverterTest;
import net.gos95.empire.util.DOMValidatorTest;
import net.gos95.empire.util.EncoderTest;
import net.gos95.empire.util.ParametricStringTest;
import net.gos95.empire.util.TimeoutTimerTest;
import net.gos95.empire.util.VectorBufferTest;
import junit.framework.Test;
import junit.framework.TestSuite;


public class EmpireModuleTest
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Empire Module");

		//$JUnit-BEGIN$
		suite.addTestSuite(EmpireObjectTest.class);
		suite.addTestSuite(ValueObjectTest.class);
		suite.addTestSuite(DtoTest.class);
		suite.addTestSuite(StackExceptionTest.class);

		suite.addTestSuite(CodeValidatorTest.class);
		suite.addTestSuite(ConverterTest.class);
		suite.addTestSuite(DOMValidatorTest.class);
		suite.addTestSuite(EncoderTest.class);
		suite.addTestSuite(ParametricStringTest.class);
		suite.addTestSuite(TimeoutTimerTest.class);
		suite.addTestSuite(VectorBufferTest.class);
		//$JUnit-END$

		return suite;
	}
}
