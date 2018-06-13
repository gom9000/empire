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


import java.util.*;


/**
 * The <code>StackException</code> class encapsulates a stack of
 * exceptions and methods to retrieve its elements.
 * 
 * @author  Alessandro Fraschetti
 * @version 1.1, 02/09/2007
 * @see     net.gos95.empire.lang.EmpireException
 */
public class StackException
extends EmpireException
{
	/* the class-version of this class */
	private static final long serialVersionUID = 110L;

    private Vector stack;
    private String message;
    private Object obj;


    /**
     * Creates a new empty <code>StackException</code>
     * with the given class-version params.
     * 
     * @param clazz    classname
     * @param lversion class-version
     */
    public StackException(Class clazz, long lversion)
    {
        super(clazz, lversion);
        stack = new Vector();
    }


    /**
     * Creates a new empty <code>StackException</code>.
     */
    public StackException()
    {
    	this(StackException.class, serialVersionUID);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given class-version params and detail message.
     *
     * @param clazz    classname
     * @param lversion class-version
     * @param message  the detail message
     */
    public StackException(Class clazz, long lversion, String message)
    {
    	super(clazz, lversion, message);
    	this.message = message;
        stack = new Vector();
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given detail message.
     *
     * @param  message the detail message
     */
    public StackException(String message)
    {
    	this(StackException.class, serialVersionUID, message);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given class-version params and attached object.
     *
     * @param clazz    classname
     * @param lversion class-version
     * @param obj the  attached object
     */
    public StackException(Class clazz, long lversion, Object obj)
    {
    	super(clazz, lversion);
    	stack = new Vector();
        this.obj = obj;
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given attached object.
     *
     * @param obj the attached object
     */
    public StackException(Object obj)
    {
    	this(StackException.class, serialVersionUID, obj);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given class-version params, detail message and object.
     *
     * @param clazz    classname
     * @param lversion class-version
     * @param message  the detail message
     * @param obj the  attached object
     */
    public StackException(Class clazz, long lversion, String message, Object obj)
    {
        super(clazz, lversion, message);
        stack = new Vector();
        this.message = message;
        this.obj = obj;
    }


    /**
     * Creates a new <code>StackException</code>
     * with the givens detail message and object.
     *
     * @param  message the detail message
     * @param  obj     the attached object
     */
    public StackException(String message, Object obj)
    {
    	this(StackException.class, serialVersionUID, message, obj);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given class-version params
     * and encapsulated exception.
     *
     * @param clazz     classname
     * @param lversion  class-version
     * @param exception encapsulated exception
     */
    public StackException(Class clazz, long lversion, Exception exception)
    {
    	super(clazz, lversion, exception.getMessage());
    	this.message = exception.getMessage();

        if (exception instanceof StackException)
            stack = ((StackException)exception).getStack();
        else
            stack = new Vector();

        stack.add(exception);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given encapsulated exception.
     *
     * @param  exception   encapsulated exception
     */
    public StackException(Exception exception)
    {
    	this(StackException.class, serialVersionUID, exception);
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given class-version params,
     * encapsulated exception and attached message.
     * 
     * @param clazz     classname
     * @param lversion  class-version
     * @param exception encapsulated exception
     * @param message   attached message
     */
    public StackException(Class clazz, long lversion, Exception exception, String message)
    {
    	this(clazz, lversion, exception);
    	this.message = message;
    }


    /**
     * Creates a new <code>StackException</code>
     * with the given encapsulated exception and attached message.
     * 
     * @param  exception encapsulated exception
     * @param  message   attached message
     */
    public StackException(Exception exception, String message)
    {
    	this(StackException.class, serialVersionUID, exception, message);
    }


    /**
     * Returns a vector that encapslues the stack exception.
     *
     * @return vector
     */
    public Vector getStack()
    {
        return stack;
    }


    /**
     * Sets attached message to this exception.
     *
     * @param  message   attached message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }


    /**
     * Returns attached message.
     *
     * @return attached message
     */
    public String getMessage()
    {
        return this.message;
    }


    /**
     * Sets attached object to this exception.
     *
     * @param  obj   attached object
     */
    public void setObject(Object obj)
    {
        this.obj = obj;
    }


    /**
     * Returns attached object.
     *
     * @return attached object
     */
    public Object getObject()
    {
        return obj;
    }


    /**
     * Returns the stack of detail messages.
     *
     * @return the stack exception detail messages
     */
    public String getStackMessages()
    {
        String s = "";

        for (int ii = 0; ii < stack.size(); ii++)
            s += ((Exception)stack.get(ii)).getMessage() + System.getProperty("line.separator");

        s += message;

        return s;
    }


    /**
     * Returns a string representation of this stack exception.
     *
     * @return the stack exception string
     */
    public String toStackString()
    {
        String s = "";

        for (int ii = 0; ii < stack.size(); ii++) {
        	s += ((Exception)stack.get(ii)).toString() + System.getProperty("line.separator");

        	StackTraceElement[] element = ((Exception)stack.get(ii)).getStackTrace();
			for (int jj=0; jj<element.length; jj++)
				s += "  " + element[jj].toString() + System.getProperty("line.separator");
        }

        s += toString();

        return s;
    }
}
