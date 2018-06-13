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


package net.gos95.empire.waf.util.logger;


import java.text.SimpleDateFormat;
import java.util.zip.*;
import java.util.*;
import java.io.*;

import net.gos95.empire.lang.EmpireObject;
import net.gos95.empire.lang.Reloadable;


/**
 * The class <code>Logger</code> manages pool of log files.<br>
 * Each log file is identified by string alias, defined in a property file like:</br>
 * <pre>
 *    alias1 = pathname1, kbMaxSize1, backupPath1
 *    alias2 = pathname2, kbMaxSize2, backupPath2
 *    ..............................
 * </pre>
 * <code>alias</code> is the identifier of the handle to log file;<br>
 * <code>pathname</code> is the directory and file name;<br>
 * <code>kbMaxSize</code> is the allowed max size of log file;<br>
 * <code>backupPath</code> is the path to move backup log file when max size is reached.
 * </br>
 * When log file size reach max size, then the file will zipped and moved.
 *
 * @author  Alessandro Fraschetti
 * @version 1.01, 01/02/2006
 * @see     net.gos95.empire.waf.util.logger.LoggerException
 * @see     net.gos95.empire.waf.util.logger.LoggerData
 */
public class Logger
extends EmpireObject
implements Reloadable
{
	private static final long serialVersionUID = 101L;

	private String           logFile;
	private String           propFile;
	private FileOutputStream fos;
	private Hashtable        hash;
	//private int              nlogs;
	private boolean          forceClose = false;


	private Logger()
	{
		super(Logger.class, serialVersionUID);
	}


	/**
	 * Instantiates a new <code>Logger</code> object
	 * with the given property file and class-log path name.
	 *
	 * @param   propFile  the property file
	 * @param   logFile   the class-log file name
	 */
	public Logger(String propFile, String logFile)
	throws LoggerException
	{
		this();

		this.propFile = propFile;
		this.logFile  = logFile;

		try {
			fos = new FileOutputStream(this.logFile, true);
			loadProperties();
			log("logger started");
		} catch(IOException e) {
			throw new LoggerException("Error starting logger:" + e);
		}
	}


	/**
	 * Instantiates a new <code>Logger</code> object with the specified property file,
	 * class-log path name and a flag to force open/close of streams at each log write.
	 *
	 * @param   propFile   the property file
	 * @param   logFile    the class-log file name
	 * @param   forceClose if true force open/close of stream at each log write
	 */
	public Logger(String propFile, String logFile, boolean forceClose)
	throws LoggerException
	{
		this(propFile, logFile);
		this.forceClose = forceClose;
	}


	/**
	 * Performs backup of the log file identified by given alias.<br> 
	 * Log file is zipped and moved to property specified pathname.<br>
	 * Backupped file is named as the original file plus ddmmyyyy date string.
	 *
	 * @param   alias   the alias to log file
	 */
	public void backup(String alias)
	{
		String format = "_yyyy-MM-dd_HHmm";
		LoggerData table  = (LoggerData)hash.get(alias);
		ZipOutputStream  zos;
		FileInputStream  fis;
		FileOutputStream fos;
		int ch;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String today = sdf.format(Calendar.getInstance().getTime());
		String arcPathname = table.getArcPath() + System.getProperty("file.separator") + alias + today + ".zip";

		try {

			close(alias);

			// cerca l'archivio ArcPath/alias.zip, se non lo trova lo crea vuoto
			fis = new FileInputStream(table.getPathname());
			fos = new FileOutputStream(arcPathname, true);
			zos = new ZipOutputStream(fos);

			// add all'archivio col nome name.logDMYYYY
			ZipEntry ze = new ZipEntry((new File(table.getPathname())).getName()+"_"+today);
			zos.putNextEntry(ze);

			while (true) {
				if ((ch = fis.read()) == -1) break;
				zos.write(ch);
			}

			zos.close();
			fos.close();

			// riazzera il log file
			fos = new FileOutputStream(table.getPathname());

			log("backup of alias " + alias + ": " + table.getPathname() + " -> " + arcPathname);

		} catch(IOException ioe) {
			log("Error on backup of alias " + alias + ": " + ioe);
		}
	}


	/**
	 * Tests the size of log file identified by given alias.
	 *
	 * @param    alias   the alias to log file
	 */
	public boolean isLogFull(String alias)
	{
		LoggerData table = (LoggerData)hash.get(alias);
		long curSize   = (new File((String)table.getPathname())).length();
		long kbMaxSize = (long)table.getKbMaxSize();

		if (curSize >= kbMaxSize)
			return true;
		else
			return false;
	}


	/**
	 * Forces to reload property  file.
	 */
	public void reload()
	{
		closeAll();
		loadProperties();
		log("reload of logger table");
	}


	/**
	 * Returns a string representation of the object.
	 *
	 * @return string representation of the object
	 */
	public String toString()
	{
		String alias, ret;

		ret  = super.toString() + System.getProperty("line.separator");
		ret += "##Property File = " + propFile + "##" + System.getProperty("line.separator");

		for (Enumeration e = hash.keys(); e.hasMoreElements(); ) {
			alias = (String)e.nextElement();
			ret += "##"+alias + " = " + ((LoggerData)hash.get(alias)).getPathname() + ",";
			ret += ((LoggerData)hash.get(alias)).getKbMaxSize() + "  ";
			ret += (((LoggerData)hash.get(alias)).getStream()==null)? "[close]" : "[open]";
			ret += "##" + System.getProperty("line.separator");;
		}
		return ret;
	}


	/**
	 * Writes a message to the stream identified by the specified alias.<br> 
	 *
	 * @param  message  the message to log
	 * @param  alias   the alias to log file
	 */
	synchronized public void write(String message, String alias)
	{
		LoggerData      table;
		FileOutputStream log;

		if (!hash.containsKey(alias)) {
			log("error writing on alias " + alias + ": alias not found in property file:" + propFile);
			return;
		}

		table = (LoggerData)hash.get(alias);

		if ((FileOutputStream)table.getStream() == null)
			open(alias);

		log = (FileOutputStream)table.getStream();

		try {

			log.write((new Date() + " " + message + System.getProperty("line.separator")).getBytes());

			if (isLogFull(alias))
				backup(alias);

		} catch(IOException e) {
			log("error writing log message on alias " + alias + ": " + e);
		}

		if (forceClose == true) close(alias);
	}


	// Closes the stream identified by specified alias.
	private void close(String alias)
	{
		LoggerData table;

		if (!hash.containsKey(alias)) {
			log("error closing alias " +alias+ ": alias not found in property file:" + propFile);
			return;
		}

		table = (LoggerData)hash.get(alias);

		if (table.getStream() != null) {
			try {
				((FileOutputStream)table.getStream()).close();
				table.setStream(null);
			} catch(IOException e) {}

		}
		table.setStream(null);
	}


	// Closes all opened stream.
	private void closeAll()
	{
		for (Enumeration e = hash.keys(); e.hasMoreElements(); )
			close((String)e.nextElement());
	}



	// load property file
	private void loadProperties()
	{
		Properties  prop  = new Properties();
		LoggerData table;
		String tmp, tmp2;

		try {
			prop.load(new FileInputStream(propFile));
		} catch(IOException e) { e.printStackTrace(); }

		//nlogs = prop.size();
		hash = new Hashtable();

		for (Enumeration e = prop.propertyNames(); e.hasMoreElements(); ) {
			try {
				table = new LoggerData();
				table.setAlias((String)e.nextElement());
				tmp = prop.getProperty(table.getAlias());

				table.setPathname(tmp.substring(0, tmp.indexOf(',')));
				tmp2 = tmp.substring(tmp.indexOf(',')+1).trim();
				table.setKbMaxSize(Integer.parseInt(tmp2.substring(0, tmp2.indexOf(',')).trim())*1024);
				table.setStream(null);
				table.setArcPath(tmp2.substring(tmp2.indexOf(',')+1).trim());

				hash.put(table.getAlias(), table);
			} catch(Exception nfe) {
				log("error loading properties: bad property file - " + nfe);
			}
		}
	}


	// write log on output stream
	synchronized private void log(String str)
	{
		try {
			fos.write((new Date() + " " + str + System.getProperty("line.separator")).getBytes());
		} catch(IOException e) { e.printStackTrace(); }
	}



	// open the specified stream
	private void open(String alias)
	{
		LoggerData table;

		if (!hash.containsKey(alias)) {
			log("error on opening alias " + alias + ": alias not found in property file: " + propFile);
			return;
		}

		table = (LoggerData)hash.get(alias);

		if (table.getStream() == null)
			try {
				table.setStream(new FileOutputStream(table.getPathname(), true));
			} catch(IOException e) {
				log("error opening file " + table.getPathname() + ": file not found.");
				return;
			}
	}
}
