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


package net.gos95.empire.logger;


import java.io.FileOutputStream;

import net.gos95.empire.dto.Dto;


/**
 * The class <code>LoggerData</code> is the structure contains
 * all the info of a log file.
 *
 * @author  Alessandro Fraschetti
 * @version 1.01, 01/02/2006
 * @see     net.gos95.empire.logger.Logger
 */
public class LoggerData
extends Dto
{
	private static final long serialVersionUID = 101L;

	private String alias;
    private String pathname;
    private String arcPath;
    private FileOutputStream stream;
    private long kbMaxSize;


    /**
     * Creates a new empty <code>LoggerData</code> structure.
     */
    public LoggerData()
    {
    	super(LoggerData.class, serialVersionUID);
        setKbMaxSize(1024);
        setArcPath("./");
    }


    /**
     * Creates a new <code>LoggerData</code> structure
     * with specified log file pathname and output stream.
     *
     * @param   pathname the log file pathname
     * @param   stream   the log file <code>FileOutputStream</code>
     */
    public LoggerData(String pathname, FileOutputStream stream)
    {
    	this();
        setPathname(pathname);
        setStream(stream);
    }


    /**
     * Creates a new <code>LoggerData</code> structure with
     * all data info specified.
     * 
     * @param   alias    the alias to log file 
     * @param   pathname the log file pathname
     * @param   stream   the log file <code>FileOutputStream</code>
     * @param   kbMaxSize the log file max size
     * @param   arcPath   the archived log file path
     */
    public LoggerData(String alias, String pathname, FileOutputStream stream, int kbMaxSize, String arcPath)
    {
    	this();
        setAlias(alias);
        setPathname(pathname);
        setStream(stream);
        setKbMaxSize(kbMaxSize);
        setArcPath(arcPath);
    }


    /**
     * Returns this log alias.
     *
     * @return alias the alias to log file 
     */
    public String getAlias()
    {
        return (alias);
    }


    /**
     * Returns path to store archived log file.
     *
     * @return  archived log file path
     */
    public String getArcPath()
    {
        return (arcPath);
    }


    /**
     * Returns the log file max size.
     *
     * @return  the log file max size
     */
    public long getKbMaxSize()
    {
        return (kbMaxSize);
    }


    /**
     * Returns the log file pathname.
     *
     * @return  the pathname
     */
    public String getPathname()
    {
        return (pathname);
    }


    /**
     * Returns the log file output stream.
     *
     * @return  the log file output stream
     */
    public FileOutputStream getStream()
    {
        if (stream == null) return null;
        return stream;
    }


    /**
     * Sets the log file string identifier.
     *
     * @param   alias the log file identifier
     */
    public void setAlias(String alias)
    {
        this.alias = alias;
    }


    /**
     * Sets the path of the archived log file.
     *
     * @param   arcPath path of the archived log file
     */
    public void setArcPath(String arcPath)
    {
        this.arcPath = arcPath;
    }


    /**
     * Sets log file max size.
     *
     * @param kbMaxSize  the max size of log file
     */
    public void setKbMaxSize(long kbMaxSize)
    {
        this.kbMaxSize = kbMaxSize;
    }


    /**
     * Sets log file pathname.
     *
     * @param  pathname the pathname of log file
     */
    public void setPathname(String pathname)
    {
        this.pathname = pathname;
    }


    /**
     * Sets the log file output stream.
     *
     * @param stream the log file ouptput stream
     */
    public void setStream(FileOutputStream stream)
    {
        this.stream = stream;
    }
}
