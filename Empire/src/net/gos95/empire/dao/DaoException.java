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


package net.gos95.empire.dao;


import net.gos95.empire.lang.StackException;


/**
 * The <code>DaoException</code> class is the tag class
 * for dao exception.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.lang.StackException
 */
public class DaoException
extends StackException
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


    /**
     * Creates a new empty <code>DaoException</code>.
     */
    public DaoException()
    {
        super(DaoException.class, serialVersionUID);
    }


    /**
     * Creates a new <code>DaoException</code> 
     * with the given encapsulated exception data.
     *
     * @param  e encapsulate exception data
     */
    public DaoException(Exception e)
    {
        super(DaoException.class, serialVersionUID, e);
    }


    /**
     * Creates a new <code>DaoException</code> 
     * with the given attached message.
     *
     * @param  msg attached message
     */
    public DaoException(String msg)
    {
        super(DaoException.class, serialVersionUID, msg);
    }


    /**
     * Creates a new <code>DaoException</code> 
     * with the given attached message and object.
     *
     * @param msg    attached message
     * @param object attached object
     */
    public DaoException(String msg, Object object)
    {
        super(DaoException.class, serialVersionUID, msg, object);
    }
}
