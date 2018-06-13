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

import net.gos95.empire.lang.EmpireObject;


/**
 * The <code>MultiBuffer</code> class manages
 * synchronized multiple buffers.
 *
 * @author  Alessanro Fraschetti
 * @version 1.0, 07/12/2007
 */
abstract public class MultiBuffer
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


    /* buffer table access mode = invalidate buffer */
    private final int INVALIDATE_MODE = 0;

    /* buffer table access mode = read */
    private final int READ_MODE       = 1;

    /* buffer table access mode = write */
    private final int WRITE_MODE      = 2;

    /* buffer table access mode = clone */
    private final int CLONE_MODE      = 3;


    /** operative mode = debug */
    public static final boolean DEBUG_MODE  = true;

    /** operative mode = normal */
    public static final boolean NORMAL_MODE = false;


    private Hashtable buffersTable;
    private boolean debugMode;


    /**
     * Creates a new <code>MultiBuffer</code>.
     */
    public MultiBuffer()
    {
        this(MultiBuffer.class, serialVersionUID, NORMAL_MODE);
    }


    /**
     * Creates a new <code>MultiBuffer</code>
     * with the given class-version params.
     * 
     * @param clazz    classname
     * @param lversion version
     */
    public MultiBuffer(Class clazz, long lversion)
    {
        this(clazz, lversion, NORMAL_MODE);
    }


    /**
     * Creates a new <code>MultiBuffer</code> with the given
     * operative mode and class-version params.<br>
     * Valid modes are: <code>NORMAL_MODE</code> and <code>DEBUG_MODE</code>.
     *
     * @param clazz classname
     * @param lversion version
     * @param mode the operative mode
     */
    public MultiBuffer(Class clazz, long lversion, boolean mode)
    {
    	super(clazz, lversion);
        this.debugMode = mode;
        buffersTable = new Hashtable();
    }


    /**
     * Clones specified object.
     *
     * @param  obj the object to clone
     * return the cloned object
     */
    abstract protected Object cloneObject(Object obj);


    // synchronized access to buffers
    private synchronized Object bufferTableManager(String alias, Object o, int mode)
    {
        Object buffo = null;

        if (debugMode == DEBUG_MODE) return null;

        switch (mode) {
        case INVALIDATE_MODE:
            buffersTable.remove(alias);
            break;
        case READ_MODE:
            buffo = buffersTable.get(alias);
            break;
        case WRITE_MODE:
            buffersTable.put(alias, cloneObject(o));
            break;
        case CLONE_MODE:
            buffo = cloneObject(buffersTable.get(alias));
            break;
        }

        return buffo;
    }


    /**
     * Invalidates the buffer identified by the alias.
     *
     * @param  alias the identifier of the buffer
     */
    public void invalidateBuffer(String alias)
    {
        bufferTableManager(alias, null, INVALIDATE_MODE);
    }


    /**
     * Writes specified object into the buffer identified by alias.<br>
     * If not exist a buffer indetified by alias creates it. 
     *
     * @param  alias the identifier of the buffer
     * @param  obj   the object to buffering
     */
    public void writeBuffer(String alias, Object obj)
    {
        if (obj != null)
            bufferTableManager(alias, obj, WRITE_MODE);
    }


    /**
     * Reads the buffered object identified by alias.
     *
     * @param  alias the identifier of the buffer
     * @return the buffered object
     */
    public Object readBuffer(String alias)
    {
        return bufferTableManager(alias, null, READ_MODE);
    }


    /**
     * Returns a clone of the buffer identified by alias.
     *
     * @param  alias the identifier of the buffer
     * @return the cloned buffered object
     */
    public Object cloneBuffer(String alias)
    {
        return bufferTableManager(alias, null, CLONE_MODE);
    }


    /**
     * Tests the validity of the buffer identified by alias.
     *
     * @param  alias the identifier of the buffer
     */
    public boolean isValidBuffer(String alias)
    {
        return buffersTable.containsKey(alias);
    }


    /**
     * Returns a string representation of the object.
     *
     * @return string representation
     */
    public String toString()
    {
        return buffersTable.toString();
    }
}
