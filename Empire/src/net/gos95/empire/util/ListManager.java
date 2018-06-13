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


/**
 * The <code>ListManager</code> interface offers
 * methods for manage list of objects.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 25/01/2006
 * @see     net.gos95.empire.util.ListManagerException
 */
public interface ListManager
{
    /**
     * Adds to current list the specified object, identified by the alias.
     *
     * @param  alias   the identifier of the object
     * @param  object  the object to add to list
     */
    public void add(String alias, Object object);


    /**
     * Retrieves from current list the object by its alias.
     *
     * @param  alias   the identifier of the object
     * @return         the object from list
     */
    public Object get(String alias);


    /**
     * Removes from current list the object identified by the alias.
     *
     * @param  alias   the identifier of the object
     */
    public void remove(String alias);


    /**
     * Removes all the objects in current list.
     */
    public void removeAll();


    /**
     * Forces current list to reload the object identified by the alias.
     *
     * @param  alias  the identifier of the object
     * @throws ListManagerException
     */
    public void reload(String alias)
    throws ListManagerException;


    /**
     * Forces current list to reload all the objects.
     *
     * @throws ListManagerException
     */
    public void reloadAll()
    throws ListManagerException;
}
