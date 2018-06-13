/*
 * E  M  P  I  R  E    W A F   Library
 *   ________        _________________.________
 *  /  _____/  ____ /   _____/   __   \   ____/
 * /   \  ___ /  _ \\_____  \\____    /____  \
 * \    \_\  (  <_> )        \  /    //       \
 *  \______  /\____/_______  / /____//______  /
 *         \/              \/               \/
 * Copyright (c) 2007 2009 2010 by
 * Alessandro Fraschetti (gos95@gommagomma.net)
 * 
 * This file is part of the Empire WAF library.
 * For more information about Empire WAF visit:
 *     http://gommagomma.net/gos95/empire
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License as 
 *  published by the Free Software Foundation; either version 2 of the
 *  License, or (at your option) any later version. 
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses/>.
 */


package net.gos95.empire.waf.util.navigator;


import net.gos95.empire.lang.EmpireException;


/**
 * The <code>NavigatorException</code> class is the <code>Navigator</code>
 * class exception.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 */
public class NavigatorException
extends EmpireException
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	/** the http-param containing the fall route */
    private String httpParameter;


    /**
     * Creates a new empty <code>NavigatorException</code>.
     */
    public NavigatorException()
    {
        super(NavigatorException.class, serialVersionUID);
    }


    /**
     * Creates a <code>NavigatorException</code>
     * with the specified detail message.
     *
     * @param   message   the detail message
     */
    public NavigatorException(String message)
    {
        super(NavigatorException.class, serialVersionUID, message);
    }


    /**
     * Creates a <code>NavigatorException</code>
     * with the specified detail message and http param containing
     * the fall route.
     *
     * @param  message        the detail message
     * @param  httpParameter  http param with the fall route.
     */
    public NavigatorException(String message, String httpParameter)
    {
        this(message);
        this.httpParameter = httpParameter;
    }


    /**
     * Returns the http-param with the fall route.
     *
     * @return the string http-param
     */
    public String getHttpParameter()
    {
        return httpParameter;
    }
}
