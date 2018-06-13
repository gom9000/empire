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


package net.gos95.empire.waf.template.alpha;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gos95.empire.waf.servlet.EmpireServlet;
import net.gos95.empire.waf.util.messages.MessageBox;


/**
 * The <code>MessageController</code> servlet class is the tag and root class
 * of Empire WAF "alpha" template servlet message controller.
 * <br>
 * The <code>MessageController</code> manages application messages and error messages.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 * @version 1.1, 10/12/2010
 * @see     net.gos95.empire.waf.template.alpha.Servlet
 */
abstract public class MessageController
extends net.gos95.empire.waf.template.alpha.Servlet
{
	/* the class-version of this class */
	private static final long serialVersionUID = 110L;


    /** References to ControllerException that causes the exception to display */
    public ControllerException exception;


    /**
     * Calls parent init method.
     */
    public void init()
    {
        super.init();
    }


    /**
     * Alpha template implementation of Controller method doPost.
     * <br>
     * Call dispatcher with the message box that incapsulate the message
     * to display and actions to fire. 
     * 
     * @param  req  the servlet request object
     * @param  resp the servlet response object
     * @exception ServletException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException
    {
    	try {
    		exception = (ControllerException)req.getAttribute("exception");
    		MessageBox mbox = exception.getMessageBox();

    		if (mbox == null) throw new Exception(exception.toStackString());

    		dispatcher(mbox, req, resp);
    	} catch(Exception inner) {
    		// oh no!
    		inner.printStackTrace();
    		log.write("(MessageController)" + inner.toString(), "ewaf-errors");
    		StackTraceElement[] ste = inner.getStackTrace();
			for (int ii=0; ii<ste.length; ii++) log.write("    " + ste[ii].toString(), "ewaf-errors");
    		debug(req, resp, "(MessageController)" + getServletContext().getInitParameter(Constants.SERVLET_ERROR_MESSAGE) + "<br>" + inner.toString());
    	}
    }


    /**
     * The  Message Controller must implement this method.
     * <br>
     * Dispatches message elements to the competent method.
     *
     * @param mbox the message box to manage
     * @param req  the servlet request object
     * @param resp the servlet response object
     */
    abstract public void dispatcher(MessageBox mbox, HttpServletRequest req, HttpServletResponse resp)
    throws Exception;


    /**
	 * Returns class-version.
	 */
	public String version()
	{
		return "EmpireWAF" + EmpireServlet.version + "/" + getClass().getName() + ((float)serialVersionUID/100);
	}
}
