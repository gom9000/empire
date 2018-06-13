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


package net.gos95.empire.dto;

import net.gos95.empire.lang.ValuesObject;


/**
 * The <code>Dto</code> class is the tag class for Data Transfer Object.</br>
 * A Data Transfer Object is a lightweight object that encapsulates
 * exchange informations between business objects.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/08/2007
 * @see     net.gos95.empire.lang.ValuesObject
 */
public abstract class Dto
extends ValuesObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

    
    /**
     * Creates a new empty <code>Dto</code>
     * with the given class-version params.
     * 
     * @param clazz    classname
     * @param lversion version
     */
    public Dto(Class clazz, long lversion)
    {
    	super(clazz, lversion);
    }


    /**
     * Creates a new empty <code>Dto</code>.
     */
    public Dto()
    {
        this(Dto.class, serialVersionUID);
    }
}
