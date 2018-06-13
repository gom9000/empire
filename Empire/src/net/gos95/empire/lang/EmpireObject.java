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


package net.gos95.empire.lang;


/**
 * The <code>EmpireObject</code> class is the tag and hierarchy root class
 * for Empire library objects.<br>
 * All objects in the Empire library inherit methods from this class.<br>
 * <code>EmpireObject</code> provides general info about Empire library and
 * a class-version mechanism through implementation of the <code>Versionable</code>
 * interface.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/08/2007
 * @see     net.gos95.empire.lang.Versionable
 */
public class EmpireObject
implements Versionable
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	/* class-version params */
	private Class clazz;
	private long lversion;


	/** Empire library release version */
	final public static String version   = "@EMPIRE_VERSION@";

	/** Empire library vendor */
	final public static String vendor    = "@EMPIRE_VENDOR@"; 

	/** Empire library name */
	final public static String name      = "@EMPIRE_NAME@";

	/** Empire library release build time */
	final public static String buildtime = "@EMPIRE_BUILDTIME@";

	/** Empire library release authors */
	final public static String author    = "@EMPIRE_AUTHOR@";


    /**
     * Creates a new empty <code>EmpireObject</code>
     * with the given class-version params.
     * 
     * @param clazz classname
     * @param lversion class-version
     */
    public EmpireObject(Class clazz, long lversion)
    {
    	this.clazz = clazz;
    	this.lversion = lversion;
    }


	/**
     * Creates a new empty <code>EmpireObject</code>.
     */
    public EmpireObject()
    {
    	this(EmpireObject.class, serialVersionUID);
    }


    /**
	 * Returns class-version params and library version.
	 */
	public String version()
	{
		return "Empire" + EmpireObject.version + "/" + clazz.getName() + ((float)lversion/100);
	}
}
