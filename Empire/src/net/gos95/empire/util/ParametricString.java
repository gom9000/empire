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


import java.util.*;


/**
 * The <code>ParametricString</code> class encapsulates a parametric string
 * and manages the substitution of parameters with the values given within the
 * setter methods. 
 * The parameters name must start with colon ":".
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 */
public class ParametricString
{
	private Hashtable parameters;
	private String str;


	/**
	 * Creates a new empty parametric string.
	 */
	public ParametricString()
	{
		this(null);
	}


	/**
	 * Creates a new parametric string with the given parametric text.
	 * 
	 * @param str the parametric text
	 */
	public ParametricString(String str)
	{
		this.str = str;
		this.parameters = new Hashtable();
	}


	/**
	 * Sets the parametric text.
	 * 
	 * @param str the parametric text
	 */
	public void setText(String str)
	{
		this.str = str;
	}


	/**
	 * Sets the object value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, Object value)
	{
		try {
		    this.parameters.put(name, "'" + value.toString().replaceAll("'", "''") + "'");
		} catch(Exception e) {
			this.parameters.put(name, "''");
		}
	}


	/**
	 * Sets the boolean value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, boolean value)
	{
		setParameter(name, "" + value);
	}


	/**
	 * Sets the char value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, char value)
	{
		setParameter(name, "" + value);
	}


	/**
	 * Sets the int value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, int value)
	{
		this.parameters.put(name, "" + value);
	}


	/**
	 * Sets the long value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, long value)
	{
		this.parameters.put(name, "" + value);
	}


	/**
	 * Sets the float value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, float value)
	{
		this.parameters.put(name, "" + value);
	}


	/**
	 * Sets the double value of the parameter matching with the given name.
	 * 
	 * @param name  the parameter name
	 * @param value the parameter value
	 */
	public void setParameter(String name, double value)
	{
		this.parameters.put(name, "" + value);
	}


	/**
	 * Returns the string representation of the parameter text,
	 * with the substituted parameters value.
	 * 
	 * @return the string representation
	 */
	public String toString()
	throws IllegalArgumentException
    {
		String sqlquery = this.str;
		String key, value;

		for (Enumeration e = parameters.keys(); e.hasMoreElements(); )
		{
			key = (String)e.nextElement();
			value = (String)parameters.get(key);
			key = ":" + key;

			if (sqlquery.indexOf(key) != -1)
			{
				sqlquery = sqlquery.replaceAll(key, value);
			} else {
				throw new IllegalArgumentException("Invalid parameter name " + key);
			}
		}

		return sqlquery;
	}
}
