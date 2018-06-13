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


package net.gos95.empire.logger;


import net.gos95.empire.EmpireLoggerModuleTest;
import net.gos95.empire.logger.Logger;
import junit.framework.TestCase;


public class LoggerTest
extends TestCase
{
	private Logger logger;
	private String alias1 = "logfile1";
	private String alias2 = "logfile2";

	public LoggerTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
		logger = new Logger(EmpireLoggerModuleTest.testPropLogFile, EmpireLoggerModuleTest.testLogLogFile);
	}


	public void testLogger()
	{
		logger.write("this is a message!!!", alias1);
		//assertFalse(logger.isLogFull(alias2));
		//while (!logger.isLogFull(alias2))
		for (int ii=0; ii<2000; ii++) {
		    logger.write("01234567890123456789012345678901234567890123456789", alias2);
		    try {
				Thread.sleep(10);
			} catch (InterruptedException ignore) {}
		}
	}
}
