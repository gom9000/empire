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


package net.gos95.empire.waf.util.session;


import java.util.Date;

import javax.servlet.http.HttpSession;

import net.gos95.empire.dto.Dto;


/**
 * The class <code>SessionData</code> is a structure contains
 * infos about a user session.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 29/01/2006
 */
public class SessionData
extends Dto
{
	private static final long serialVersionUID = 100L;

    /** the unique identifier of the session data*/
    public String key;

    /** the username */
    public String username;

    /** the application alias */
    public String applicationAlias;

    /** the session object */
    public HttpSession session;

    /** the data creation date */
    public Date creationDate;

    /** the session request last date */
    public Date lastRequestDate;

    /** the remote client ip address */
    public String ip;

    /** generic object attached to the data */
    public Object obj;


    /**
     * Creates a new empty <code>SessionData</code> object.
     */
    public SessionData()
    {
 //       super(SessionData.class, serialVersionUID);
    }
}
