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


package net.gos95.empire.util;


import net.gos95.empire.lang.EmpireObject;
import net.gos95.empire.lang.Versionable;


/**
 * The class <code>TimeoutTimer</code> implements a time-out
 * counter to fire action.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 28/05/2007
 * @see     net.gos95.empire.lang.Versionable
 */
abstract public class TimeoutTimer
extends Thread
implements Versionable
{
	/* the class-version of this class */
	private static final long serialVersionUID = 101L;

    private int timeout, elapsed, rate = 100;
    private boolean started = false;
    private boolean timeoutflag;


	/**
     * Override this with action code!!!.
     */
	abstract public void action();


	/**
     * Creates a new <code>TimeoutTimer</code> that fires at given timeout.
     *
     * @param timeout  ms timeout intervall
     */
    public TimeoutTimer(int timeout)
    {
		this.timeout = timeout;
		elapsed = 0;
		timeoutflag = false;
	}


	/**
	 * Sets timer clock rate.
     *
     * @param rate  ms clock rate
     */
	public synchronized void setRate(int rate)
	{
		this.rate = rate;
	}


	/**
     * Stops timer.
     */
	public synchronized void cancel()
	{
        started = false;
	}


	/**
     * Tests if action is fired or not.
     * 
     * return the boolean test result
     */
	public synchronized boolean isFired()
	{
        return timeoutflag;
	}


	/**
     * Starts timer.
     */
	public void run()
	{
    	started = true;
    	elapsed = 0;
    	timeoutflag = false;

		for (; started; )
		{
			try	{
				Thread.sleep(rate);
			} catch (InterruptedException ioe) {
				continue;
			}

			synchronized (this)
			{
				elapsed += rate;
				if (elapsed > timeout)
				{
    				started = false;
    				timeoutflag = true;
                    action();
                }
			}
		}
	}


    /**
	 * Returns the class-object and library version.<br>
	 */
	public String version()
	{
		return "Empire" + EmpireObject.version + "/" + TimeoutTimer.class + ((float)serialVersionUID/100);
	}
}
