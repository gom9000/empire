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


package net.gos95.empire.util;


import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import net.gos95.empire.lang.EmpireObject;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


/**
 * The class <code>DOMValidator</code> validates dom files.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 23/10/2010
 */
class DOMValidator
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	static final public int CLEAN      = 0;
	static final public int WARNING    = 1;
	static final public int ERROR      = 2;
	static final public int FATALERROR = 4;


	private DocumentBuilder builder;
	private OutputStream out;
	private String pathname;


	/**
	 * Instantiates a new <code>DOMValidator</code> object
	 * with the given dom file and error output stream.<br>
	 *
	 * @param   pathname  the dom file pathname to analize
	 * @param   out       the error output stream
	 */
	public DOMValidator(String pathname, OutputStream out)
	{
		super(DOMValidator.class, serialVersionUID);

		try {
			this.pathname = pathname;
			this.out = out;

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			try {
				out.write((e.toString() + System.getProperty("line.separator")).getBytes());
			} catch(Exception ignore) {}
		}
	}


	/**
	 * Validates the given dom file and reports relative error code.<br>
	 * The error code is one or more values combined in logic OR, within the following:<br>
	 * <ul>
	 *   <li>DOMValidator.CLEAN
	 *   <li>DOMValidator.WARNING
	 *   <li>DOMValidator.ERROR
	 *   <li>DOMValidator.FATALERROR
	 * </ul>
	 * 
	 * @return the error code
	 */
	public int validate()
	{
		try {
			ValidatorErrorHandler errorHandler = new ValidatorErrorHandler();
			errorHandler.setOutputStream(out);

			builder.setErrorHandler((ErrorHandler)errorHandler);
			builder.parse(pathname);

			return errorHandler.getErrorType();
		} catch (SAXException e) {System.out.println(e);
			return DOMValidator.FATALERROR;
		} catch (IOException e) {
			try {
			    out.write((e.toString() + System.getProperty("line.separator")).getBytes());
			} catch(Exception ignore) {}
			return -1;
		}
	}


	private static class ValidatorErrorHandler
	implements ErrorHandler
	{
		private OutputStream out;
		private int errortype = DOMValidator.CLEAN;

		public void setOutputStream(OutputStream out)
		{
			this.out = out;
		}

		public int getErrorType()
		{
			return errortype;
		}

		public void warning(SAXParseException e)
		{
			errortype |= DOMValidator.WARNING;
			try {
			    info(e, "Warning");
			} catch(Exception ignore) {}
		}

		public void error(SAXParseException e)
		{
			errortype |= DOMValidator.ERROR;
			try {
			    info(e, "Error");
			} catch(Exception ignore) {}
		}

		public void fatalError(SAXParseException e)
		{
			errortype |= DOMValidator.FATALERROR;
			try {
			    info(e, "Fatal Error");
			} catch(Exception ignore) {}
		}

		private void info(SAXParseException e, String messageType)
		throws IOException
		{
			out.write((messageType + ": " + "(" + e.getSystemId() + ":" + e.getLineNumber() + ")" + System.getProperty("line.separator")).getBytes());
			out.write(("  " + e.getMessage() + System.getProperty("line.separator")).getBytes());

		}
	}


	static public void main(String[] args)
	{
		DOMValidator domv = new DOMValidator(args[0], System.out);
		int errortype = domv.validate();
		
		System.out.print("Validation of \"" + args[0] + "\" ");
		if (errortype == DOMValidator.CLEAN) {
			System.out.print("done with no errors or warnings.");
		} else {
			System.out.print("failed with ");

			if ((errortype & DOMValidator.FATALERROR) == DOMValidator.FATALERROR) {
				System.out.print("fatal error");
			} else {
				if ((errortype & DOMValidator.ERROR) == DOMValidator.ERROR)
					System.out.print("errors");
				else
					System.out.print("no errors");
				if ((errortype & DOMValidator.WARNING) == DOMValidator.WARNING)
					System.out.print(" and warnings");
			}
			System.out.println(".");
		}
	}
}
