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


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gos95.empire.util.ObjectListManager;
import net.gos95.empire.waf.servlet.EmpireServlet;
import net.gos95.empire.waf.util.messages.Message;
import net.gos95.empire.waf.util.messages.MessageBox;
import net.gos95.empire.waf.util.navigator.Navigator;


/**
 * The <code>Controller</code> servlet class is the tag and root class
 * of Empire WAF "alpha" template servlet controller.
 * <br>
 * All Controllers in the alpha template must implement the dispatcher
 * method to dispatch routes to the compentent business logics.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 * @see     net.gos95.empire.waf.template.alpha.Servlet
 */
abstract public class Controller
extends net.gos95.empire.waf.template.alpha.Servlet
{
	/* the class-version of this class */
	private static final long serialVersionUID = 101L;


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
     * Calls <code>dispatcher</code> method with user given route.
     * If an exception fires then calls the <code>MessageController</code> object.
     *
     * @param  req  the servlet request object
     * @param  resp the servlet response object
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException
    {
        // call dispatcher method
        try {
            sessionOLM = (ObjectListManager)req.getSession().getAttribute("sessionOLM");
            navigator = (Navigator)sessionOLM.get("navigator");
            dispatcher(navigator.getCode(), navigator.getStep(), req, resp);
        } catch(ControllerException ctrle) {
            try {
            	// the dispatch method failed or it has a message to display,
            	// in any case call MessageController.
                RequestDispatcher rd = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.MESSAGE_CONTROLLER_URL));
                req.setAttribute("exception", ctrle);
                rd.forward(req, resp);
            } catch(Exception inner) {
            	// oh no!
        		inner.printStackTrace();
        		log.write("(Controller)" + inner.toString(), "ewaf-errors");
        		StackTraceElement[] ste = inner.getStackTrace();
    			for (int ii=0; ii<ste.length; ii++) log.write("    " + ste[ii].toString(), "ewaf-errors");
        		debug(req, resp, "(Controller)" + getServletContext().getInitParameter(Constants.SERVLET_ERROR_MESSAGE) + "<br>" + inner.toString());
        	}
        } catch(Exception e) {
            try {
                // unhandled exception has occurred inside dispatcher method. Try to convert it to ControllerException.
                RequestDispatcher rd = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.MESSAGE_CONTROLLER_URL));

                MessageBox mb = new MessageBox();
                mb.setMessage(Message.ANONYM_CODE, getServletContext().getInitParameter(Constants.SESSION_ERROR_MESSAGE)+ " - " + e.toString(), Message.ERROR);
                mb.addAction("Ok", "logout");
                ControllerException ctrle = new ControllerException(mb, e);

                req.setAttribute("exception", ctrle);
                rd.forward(req, resp);
            } catch(Exception inner) {
            	// oh no!
        		inner.printStackTrace();
        		log.write("(Controller)" + inner.toString(), "ewaf-errors");
        		StackTraceElement[] ste = inner.getStackTrace();
    			for (int ii=0; ii<ste.length; ii++) log.write("    " + ste[ii].toString(), "ewaf-errors");
        		debug(req, resp, "(Controller)" + getServletContext().getInitParameter(Constants.SERVLET_ERROR_MESSAGE) + "<br>" + inner.toString());
            }
        }
    }


    /**
     * Free the allocated session objects.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	protected void free(HttpServletRequest req, HttpServletResponse resp)
	throws Exception
	{
		if (monitor.containsSession(req.getSession()))
		{
			String key = monitor.getKey(req.getSession());

			monitor.getSession(key).invalidate();
			monitor.removeSession(key);
			navigator.reset();
		}
		sessionOLM = null;
		req.getSession().invalidate();
	}


    /**
     * All Controllers in the alpha template must implement this method
     * to dispatch routes.
     *
     * @param code selected route code
     * @param step current step of route
     * @param req  the servlet request object
     * @param resp the servlet response object
     */
    abstract public void dispatcher(String code, String step, HttpServletRequest req, HttpServletResponse resp)
    throws Exception;


    /**
	 * Returns class-version.
	 */
	public String version()
	{
		return "EmpireWAF" + EmpireServlet.version + "/" + getClass().getName() + ((float)serialVersionUID/100);
	}
}
