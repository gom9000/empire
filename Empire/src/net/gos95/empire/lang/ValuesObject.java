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


import java.io.Serializable;
import java.lang.reflect.*;


/**
 * The <code>ValuesObject</code> class provides methods to return
 * a string rappresentation of the object attributes.<br>
 * This class implements <code>Serializable</code>, any
 * non-serializable attribute must be declared as <code>transient</code>.
 *
 * @author  Alessandro Fraschetti
 * @version 1.1, 07/08/2007
 * @see     java.io.Serializable
 * @see     net.gos95.empire.lang.EmpireObject
 * @see     net.gos95.empire.lang.Describable
 */
public abstract class ValuesObject
extends EmpireObject
implements Serializable, Describable
{
	/* the class-version of this class */
	private static final long serialVersionUID = 110L;

	/* the system carriage-return + line feed */
	private static final String CRLF = System.getProperty("line.separator");


    /**
     * Creates a new empty <code>ValuesObject</code>
     * with the given class-version params.
     * 
     * @param clazz classname
     * @param lversion class-version
     */
    public ValuesObject(Class clazz, long lversion)
    {
    	super(clazz, lversion);
    }


    /**
     * Creates a new empty <code>ValuesObject</code>.
     */
    public ValuesObject()
    {
    	this(ValuesObject.class, serialVersionUID);
    }


    /**
     * Returns a string list of attribute/value pairs for all
     * the properties of this object.
     * 
     * @return the string list of attribute/value pairs of this object
     */
    public String describe()
    {
        return describe(false);
    }


    /**
     * Returns a string list of attribute/value pairs for all
     * the properties of this object.<br>
     * If a property is instance of <code>ValuesObject</code> this
     * method returns recursively property attribute/value pairs.
     * 
     * @return the string list of attribute/value pairs of this object
     */
    public String describeRecursive()
    {
        return describe(true);
    }


    /**
     * Returns a string list of attribute/value pairs for all
     * the properties of this object.
     *
     * @param recursive  if true invoke method <code>describe()</code> of
     * properties that are instance of <code>ValuesObject</code> class.
     * @return the string list of attribute/value pairs of this object
     */
    public String describe(boolean recursive)
    {
        StringBuffer dump = new StringBuffer();
        String value;
        Field[] field = this.getClass().getDeclaredFields();

        dump.append(this.getClass().getName());
        dump.append(CRLF);
        for (int ii=0; ii<field.length; ii++) {

            field[ii].setAccessible(true);
            dump.append(field[ii].getName());
            dump.append(" = ");

            try {
                if (recursive && field[ii].get(this) instanceof net.gos95.empire.lang.ValuesObject)
                    value = field[ii].get(this).toString() + CRLF + "[" + CRLF + ((ValuesObject)(field[ii].get(this))).describeRecursive() + "]";
                else
                    value = field[ii].get(this).toString();
                dump.append(value);
            } catch(IllegalAccessException e1) {
                dump.append("not available (illegal access)");
            } catch(IllegalArgumentException e2) {
                dump.append("not available (illegal argument)");
            } catch(NullPointerException e4) {
                dump.append("not available (null pointer)");
            } catch(Exception e) {
                dump.append(e.getMessage());
            }

            dump.append(CRLF);

        }

        return dump.toString();
    }


    /**
     * Returns the hash code value for this object.
     *
     * @return the hash code value for this object
     */
    public int hashCode()
    {
        StringBuffer dump = new StringBuffer();
        String value1, value2;
        Field[] field = this.getClass().getDeclaredFields();

        dump.append(this.getClass().getName());

        for (int ii=0; ii<field.length; ii++) {
            field[ii].setAccessible(true);
            try {
                value1 = ""+field[ii];
                value2 = ""+field[ii].get(this);
                if (value2.indexOf("@") > 0) value2 = value2.substring(0, value2.indexOf("@"));
                dump.append(value1 + value2);
            } catch(Exception e) {
                dump.append("null");
            }
        }

        return dump.toString().hashCode();
    }


    /**
     * Compares the given object with this for equality.
     *
     * @param  obj object to compare
     * @return <code>true</code> if equals, <code>false</code> otherwise
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj != null && obj.getClass().getName().equals(this.getClass().getName()))
            if (this.hashCode() == obj.hashCode()) return true;

        return false;
    }
}
