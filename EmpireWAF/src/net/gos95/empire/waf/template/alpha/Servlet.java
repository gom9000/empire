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


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gos95.empire.util.ListManager;
import net.gos95.empire.util.ObjectListManager;
import net.gos95.empire.waf.servlet.EmpireServlet;
import net.gos95.empire.waf.util.logger.Logger;
import net.gos95.empire.waf.util.messages.MessageManager;
import net.gos95.empire.waf.util.navigator.Navigator;
import net.gos95.empire.waf.util.session.SessionMonitor;


/**
 * The <code>Servlet</code> class is the tag and root servlet class
 * of Empire WAF "alpha" template.
 * <br>
 * This class defines some implicit objects:
 * <ul>
 *   <li>navigator - reference to the Navigator object that contains all the user session available routes.
 *   <li>monitor - reference to the session monitor object that contains all session data.
 *   <li>messages - reference to the message manager object.
 *   <li>log - reference to the logger object.
 * </ul>
 * The monitor, messages and log objects are also contained in the applicationOLM object.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see net.gos95.empire.waf.servlet.EmpireServlet
 * @see net.gos95.empire.waf.util.messages.MessageManager
 * @see net.gos95.empire.waf.util.navigator.Navigator
 * @see net.gos95.empire.waf.util.session.SessionMonitor
 */
public abstract class Servlet
extends EmpireServlet
{
	private static final long serialVersionUID = 100L;


    /** Implicit reference to the navigation object */
    public Navigator navigator;

    /** Implicit reference to the session monitor */
    public SessionMonitor monitor;

    /** Implicit reference to the message manager */
    public MessageManager messages;

    /** Implicit reference to the logger */
    public Logger log;


    /**
     * Initializes servlet implicit objects.
     */
    public void init()
    {
        ServletContext ctx = getServletContext();

        // inits (only 1 time) and popolates the applicationOLM
        if (ctx.getAttribute("applicationOLMInitialized") == null)
            initialize();

        // retrieves implicit objects from application OLM
        applicationOLM = (ListManager)ctx.getAttribute("applicationOLM");
        monitor = (SessionMonitor)applicationOLM.get("monitor");
        messages = (MessageManager)applicationOLM.get("messages");
        log = (Logger)applicationOLM.get("logger");
    }


    // init "application" objects
    private void initialize()
    {
        ServletContext ctx = getServletContext();

        // init the session monitor
        monitor = new SessionMonitor();

        // init the message manager
        messages = new MessageManager();

        // init the logger
        try {
            log = new Logger(ctx.getInitParameter("loggerConfigFile"), ctx.getInitParameter("loggerLogFile"));
        } catch(Exception e) {e.printStackTrace(System.out);}

        // init the application OLM, and populates it
        ObjectListManager olm = new ObjectListManager();
        olm.add("monitor", monitor);
        olm.add("messages", messages);
        olm.add("logger", log);

        // store the OLM in the application context
        ctx.setAttribute("applicationOLM", olm);
        ctx.setAttribute("applicationOLMInitialized", new Boolean(true));
    }


    /**
	 * Returns class-version.
	 */
	public String version()
	{
		return "EmpireWAF" + EmpireServlet.version + "/" + getClass().getName() + ((float)serialVersionUID/100);
	}


    /**
     * All servlets in the alpha template must implement this method.
     * 
     * @param  request  the servlet request object
     * @param  response the servlet response object
     * @exception ServletException
     */
    public abstract void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException;
}
