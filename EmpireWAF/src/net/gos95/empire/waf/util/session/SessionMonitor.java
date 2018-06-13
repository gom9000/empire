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


import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;

import net.gos95.empire.lang.EmpireObject;

//TODO WAF - TestCase net.gos95.empire.waf.util.session
/**
 * The <code>SessionMonitor</code> class implements a user session monitor.
 * Stored user session info are:<PRE
 *  key              - the unique identifier
 *  username         - identify the connected user
 *  applicationAlias - the application alias
 *  session          - the session object
 *  creationDate     - the creation date of record in monitor
 *  lastRequestDate  - last session request date
 *  ip               - the remote client ip address
 *  obj              - a generic object attached
 * </PRE>
 * @author  Alessandro Fraschetti
 * @version 1.1, 29/01/2006
 * @see net.gos95.empire.waf.util.session.SessionData
 */
public class SessionMonitor
extends EmpireObject
{
	private static final long serialVersionUID = 101L;

	private Hashtable hash;
    private Hashtable hashSess;


    /**
     * Creates a new empty <code>SessionMonitor</code> object.
     */
    public SessionMonitor()
    {
    	super(SessionMonitor.class, serialVersionUID);
        hash = new Hashtable();
        hashSess = new Hashtable();
    }


    /**
     * Adds (or replaces if exists) a new session entry with the given info. 
     *
     * @param  key      the unique identifier
     * @param  username the username
     * @param  session  the session object
     * @param  ip       the remote client ip address
     * @param  obj      the attached object
     */
    public synchronized void addSession(String key, String username, HttpSession session, String ip, Object obj)
    {
        SessionData data = new SessionData();

        data.key             = key;
        data.username        = username;
        data.session         = session;
        data.ip              = ip;
        data.obj             = obj;
        data.creationDate    = new Date();
        data.lastRequestDate = new Date(session.getCreationTime());

        hash.put(key, data);
        hashSess.put(key, session);
    }


    /**
     * Adds (or replaces if exists) a new session entry with the specified info. 
     *
     * @param  key      the unique identifier
     * @param  username the username
     * @param  session  the session object
     * @param  ip       the remote client ip address
     */
    public synchronized void addSession(String key, String username, HttpSession session, String ip)
    {
        addSession(key, username, session, ip, null);
    }


    /**
     * Adds (or replaces if exists) a new session entry with the specified info. 
     *
     * @param  key      the unique identifier
     * @param  session  the session object
     * @param  ip       the remote client ip address
     */
    public synchronized void addSession(String key, HttpSession session, String ip)
    {
        addSession(key, null, session, ip, null);
    }


    /**
     * Removes from monitor session data of the given key.
     *
     * @param  key the id of the session data
     */
    public void removeSession(String key)
    {
        if (hash.containsKey(key))
        {
            hash.remove(key);
            hashSess.remove(key);
        }
    }


    /**
     * Tests if monitor contains the given key.
     *
     * @param  key the unique identifier
     * @return the boolean result of test
     */
    public boolean containsKey(String key)
    {
        return hash.containsKey(key);
    }


    /**
     * Tests if monitor contains the given session object.
     *
     * @param session the session object
     * @return the boolean result of test
     */
    public boolean containsSession(HttpSession session)
    {
        return hashSess.containsValue(session);
    }


    /**
     * Updates last session request date of the given key.
     *
     * @param key the unique identifier 
     */
    public synchronized void updateLastHttpRequestDate(String key)
    {
        SessionData data = (SessionData)hash.get(key);        
        data.lastRequestDate = new Date();
        hash.put(key, data);
    }


    /**
     * Updates last session request date of the given session object.
     *
     * @param session the session object
     */
    public synchronized void updateLastHttpRequestDate(HttpSession session)
    {
        updateLastHttpRequestDate(getKey(session));
    }


    /**
     * Returns the key matching the session object.
     *
     * @param  session the session object
     * @return the unique identifier
     */
    public String getKey(HttpSession session)
    {
        String key = null;

        for (Enumeration e=hash.keys(); e.hasMoreElements(); )
        {
            key = (String)e.nextElement();
            if (session.equals(((SessionData)hash.get(key)).session))
                break;
        }

        return key;
    }


    /**
     * Returns the username.
     *
     * @param  key the unique identifier
     * @return the username
     */
    public String getUsername(String key)
    {
        return ((SessionData)hash.get(key)).username;
    }


    /**
     * Returns the username matching the session object.
     *
     * @param  session the session object
     * @return the username
     */
    public String getUsername(HttpSession session)
    {
        String key, username = null;

        for (Enumeration e=hash.keys(); e.hasMoreElements(); )
        {
            key = (String)e.nextElement();
            if (session.equals(((SessionData)hash.get(key)).session))
            {
            	username = getUsername(key);
                break;
            }
        }

        return username;
    }


    /**
     * Returns the application alias name.
     *
     * @param  key the unique identifier
     * @return the application name
     */
    public String getApplication(String key)
    {
        return ((SessionData)hash.get(key)).applicationAlias;
    }


    /**
     * Returns the session object matching the given key.
     *
     * @param  key the unique identifier
     * @return the session object
     */
    public HttpSession getSession(String key)
    {
        return ((SessionData)hash.get(key)).session;
    }


    /**
     * Returns the session creation date.
     *
     * @param  key the unique identifier
     * @return the session creation date
     */
    public Date getSessionDate(String key)
    {
        return ((SessionData)hash.get(key)).creationDate;
    }


    /**
     * Returns the last session request date.
     *
     * @param  key the unique identifier
     * @return the last session request date
     */
    public Date getLastRequestDate(String key)
    {
        return ((SessionData)hash.get(key)).lastRequestDate;
    }


    /**
     * Returns the remote client ip address.
     *
     * @param  key the unique identifier
     * @return the remote client ip address
     */
    public String getIP(String key)
    {
        return ((SessionData)hash.get(key)).ip;
    }


    /**
     * Returns the attached object.
     *
     * @param  key the unique identifier
     * @return the attached object
     */
    public Object getObj(String key)
    {
        return ((SessionData)hash.get(key)).obj;
    }


    /**
     * Sets the remote client ip address.
     *
     * @param  key the unique identifier
     * @param  ip the remote client ip address
     */
    public synchronized void setIP(String key, String ip)
    {
        SessionData data = (SessionData)hash.get(key);
        data.ip = ip;
        hash.put(key, data);
    }


    /**
     * Returns a collection of managed session data.
     * 
     * @return the collection of session data
     */
    public Collection values()
    {
        return hash.values();
    }


    /**
     * Returns the session data object.
     *
     * @param  key the unique identifier
     * @return the session data object
     */
    public SessionData getSessionData(String key)
    {
        return (SessionData)hash.get(key);
    }


    /**
     * Returns a string representation of this object.
     * 
     * @return string representation of this object
     */
    public String toString()
    {
        return super.toString() + System.getProperty("line.separator") + hash.toString();
    }
}
