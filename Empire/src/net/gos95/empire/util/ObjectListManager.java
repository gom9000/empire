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

import net.gos95.empire.lang.EmpireException;
import net.gos95.empire.lang.EmpireObject;
import net.gos95.empire.lang.Reloadable;


/**
 * The <code>ObjectListManager</code> class implements
 * methods to manage list of objects.
 *
 * @author  Alessanro Fraschetti
 * @version 1.1, 07/12/2007
 * @see     net.gos95.empire.util.ListManager
 */
public class ObjectListManager
extends EmpireObject
implements ListManager
{
	/* the class-version of this class */
	private static final long serialVersionUID = 101L;

	private Hashtable hash;


    /**
     * Creates a new empty <code>ObjectListManager</code>
     * with the given class-version params.
     * 
     * @param clazz classname
     * @param lversion version
     */
    public ObjectListManager(Class clazz, long lversion)
    {
        super(clazz, lversion);
        hash = new Hashtable();
    }


    /**
     * Creates a new empty <code>ObjectListManager</code>.
     */
    public ObjectListManager()
    {
        this(ObjectListManager.class, serialVersionUID);
    }


    /**
     * Adds to current list the specified object, identified by the alias.
     *
     * @param  alias   the identifier of the object
     * @param  object  the object to add to list
     */
    public void add(String alias, Object object)
    {
        hash.put(alias, object);
    }


    /**
     * Retrieves from current list the object by its alias.
     *
     * @param  alias   the identifier of the object
     * @return         the object from list
     */
    public Object get(String alias)
    {
        return hash.get(alias);
    }


    /**
     * Removes from current list the object identified by the alias.
     *
     * @param  alias   the identifier of the object
     */
    public void remove(String alias)
    {
        hash.remove(alias);
    }


    /**
     * Removes all the objects in current list.
     */
    public void removeAll()
    {
        for (Enumeration e = hash.keys(); e.hasMoreElements(); )
            remove((String)e.nextElement());
    }


    /**
     * Forces current list to reload the object identified by the alias.<br>
     * Only objects instance of <code>Reloadable</code> interface are reloaded.
     *
     * @param  alias  the identifier of the object
     * @throws ListManagerException
     */
    public void reload(String alias)
    throws ListManagerException
    {
        if (hash.get(alias) instanceof Reloadable)
			try {
				((Reloadable)hash.get(alias)).reload();
			} catch (EmpireException e) {
				new ListManagerException(e.getMessage());
			}
    }


    /**
     * Forces current list to reload all the objects.<br>
     * Only objects instance of <code>Reloadable</code> interface are reloaded.
     *
     * @throws ListManagerException
     */
    public void reloadAll()
    throws ListManagerException
    {
        for (Enumeration e = hash.keys(); e.hasMoreElements(); )
            reload((String)e.nextElement());
    }


    /**
     * Returns a string representation of this object.
     *
     * @return rappresentazione stringa del list manager
     */
    public String toString()
    {
        return hash.toString();
    }
}
