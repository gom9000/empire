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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * The <code>DriverManagerDao</code> class is the tag class for Data Access Object
 * that connecting to a data source by <code>DriverManager</code> object.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.dao.Dao
 */
public abstract class DriverManagerDao
extends Dao
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

    private String driver;
    private String url;
    private String user;
    private String passwd;


	/**
	 * Creates a new <code>DriverManagerDao</code> with
	 * given connection data.
	 * 
	 * @param driver the driver to connect to the data source
	 * @param url the url of the data source
	 * @param user the username to access to the data source
	 * @param passwd the password to access to the data source
	 */
	public DriverManagerDao(String driver, String url, String user, String passwd)
	throws DaoException
	{
		super(DriverManagerDao.class, serialVersionUID);
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.passwd = passwd;

		initialize();
	}


	// inits the object
	private void initialize()
	throws DaoException 
	{
		try {
			Class.forName(driver);
		} catch (Exception e) {
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
            c = DriverManager.getConnection(url, user, passwd);
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
