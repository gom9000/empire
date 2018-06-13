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


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * The <code>DataSourceDao</code> class is the tag class for Data Access Object
 * that connecting to a data source by <code>DataSource</code> object.
 * 
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.dao.Dao
 * @see     net.gos95.empire.dao.DaoException
 */
public abstract class DataSourceDao
extends Dao
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	protected DataSource datasource;
    protected String url;
    protected String name;


	/**
	 * Creates a new <code>DataSourceDao</code> with the
	 * given data source.
	 * 
	 * @param datasource the data source object
	 */
	public DataSourceDao(DataSource datasource)
	throws DaoException
	{
		super(DataSourceDao.class, serialVersionUID);
		this.datasource = datasource;
	}


    /**
	 * Creates a new DAO with the given context properties.
	 * 
	 * @param hash the hashtable containing the context properties
	 * @param name the data source name
	 */
	public DataSourceDao(Hashtable hash, String name)
	throws DaoException
	{
		super();
		initialize(hash, name);
	}


    private void initialize(Hashtable hash, String name)
    throws DaoException
    {
        Context context;

        try {
            context = new InitialContext(hash);
            datasource = (DataSource)context.lookup(name);
        } catch (NamingException e) {
        	datasource = null;
        	throw new DaoException(e.toString());
        }
    }


	/**
	 * Returns a connection to the data source.
     * 
     * @return the connection to the data source
     * @throws DaoException
     */
	protected Connection getConnection()
	throws DaoException
	{
        Connection c = null;
        try {
            c = datasource.getConnection();
            return c;
        }catch(Exception e) {
            c = null;
            throw new DaoException(e.toString());
        }
	}


    /**
     * Tests the connection to the data source.
     * 
     * @return <code>true</code> if connection is not null, <code>false</code> otherwise
     */
    public boolean testConnection()
    {
    	Connection c = null;
    	boolean test = false;
    	try {
    		c = getConnection();
    		if (c != null) test = true;
    	} catch(Exception e) {
    		return false;
    	} finally {
    		closeConnection(c);
    	}
    	return test;
    }


    /**
     * Closes the given connection object.
	 * 
	 * @param c the connection to close
	 */
	protected void closeConnection(Connection c)
	{
        try {
            if(c != null) {c.close(); c = null; };
        } catch(Exception ignore) {
        } finally {
            if( c != null ) try { c.close(); c = null; } catch(Exception ignore) { c = null; }
        }
	}


    /**
     * Close the given result set object.
     * 
     * @param the result set to close
     */
	protected void closeResultSet(ResultSet r)
    {
        try {
            if(r != null) { r.close(); r = null; }
        } catch(Exception ignore) {
        } finally {
            if( r != null ) try { r.close(); r = null; } catch(Exception ignore) { r = null; }
        }
    }


    /**
     * Closes the given statement object.
     * 
     * @param the statement to close
     */
	protected void closeStatement(Statement s)
    {
        try {
            if(s != null) { s.close(); s = null; }
        } catch(Exception ignore) {
        } finally {
            if( s != null ) try { s.close(); s = null; } catch(Exception ignore) { s = null; }
        }
    }
}
