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


package net.gos95.empire.util;


import net.gos95.empire.util.support.TimeoutTimerExtends;
import junit.framework.Assert;
import junit.framework.TestCase;


public class TimeoutTimerTest
extends TestCase
{
	private TimeoutTimerExtends timer;
	private int timeout = 720;


	public TimeoutTimerTest(String name)
	{
		super(name);
	}

	protected void setUp()
	throws Exception
	{
		super.setUp();
	}


	public void testTimeout()
	{
		timer = new TimeoutTimerExtends(timeout); 
		timer.run();
		Assert.assertTrue(timer.actionPerformed);
		Assert.assertTrue(timer.isFired());
	}
}
