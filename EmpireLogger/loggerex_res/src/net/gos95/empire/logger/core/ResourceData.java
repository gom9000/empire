/*
 * E  M  P  I  R  E   Library
 *   ________        _________________.________
 *  /  _____/  ____ /   _____/   __   \   ____/
 * /   \  ___ /  _ \\_____  \\____    /____  \
 * \    \_\  (  <_> )        \  /    //       \
 *  \______  /\____/_______  / /____//______  /
 *         \/              \/               \/
 * Copyright (c) 2007 2009 by
 * Alessandro Fraschetti (gos95@gommagomma.net)
 * 
 * This file is part of the Empire library.
 * For more information about Empire visit:
 *     http://gommagomma.net/gos95/Empire
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


package net.gos95.empire.logger.core;


import java.io.OutputStream;

import net.gos95.empire.dto.Dto;


/**
 * The class <code>ResourceData</code> is the structure contains
 * all the info of a log resource.
 *
 * @author  Alessandro Fraschetti
 * @version 1.01, 01/02/2006
 * @see     net.gos95.empire.logger.LoggerEx
 * @since   1.4
 */
public class ResourceData
extends Dto
{
	private static final long serialVersionUID = 100L;


	static final public String LEVEL_INFO    = "INFO";
	static final public String LEVEL_WARNING = "WARNING";
	static final public String LEVEL_ERROR   = "ERROR";


	/** the unique alias of the resource */
	public String  alias;

	/** the resource log-level between values: INFO, WARNING, ERROR */
	public int     level;

	/** the kb max size of the resource file before rotation */
	public long    maxsize;

	/** the pathname of the resource */
	public String  pathname;

	/** if true, log is appended to the same resource pathname,
	    otherwise pathname will be rotate */
	public boolean flagAppend;

	/** if true forces open/close of the resource at each log write */
	public boolean flagWriteAndClose;

	/** the pathname where to backup resource */
	public String  backupPathname;

	/** if true, enable the zip compression of the backup resource */
	public boolean flagBackupZip;

	/** the resource context area */
	public String  area;

	/** the resource output stream */
	public OutputStream stream;


    /**
     * Creates a new empty <code>ResourceData</code> structure.
     */
    public ResourceData()
    {
    	super(ResourceData.class, serialVersionUID);
    }

}
