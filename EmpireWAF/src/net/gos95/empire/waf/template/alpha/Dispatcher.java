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



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.gos95.empire.util.ObjectListManager;
import net.gos95.empire.waf.servlet.EmpireServlet;
import net.gos95.empire.waf.util.messages.Message;
import net.gos95.empire.waf.util.messages.MessageBox;
import net.gos95.empire.waf.util.navigator.Navigator;
import net.gos95.empire.waf.util.navigator.Route;


/**
 * The <code>Dispatcher</code> servlet class manages routing dispatch
 * from client to competent controller objects, according to contents
 * of <code>Navigator</code> object.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 */
public class Dispatcher
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
     * Alpha template implementation of Dispatcher method doPost.<br>
     * Dispatch the client request to competent Controller, according
     * to contents of Navigator object.
     * 
     * @param  req  the servlet request object
     * @param  resp the servlet response object
     * @exception ServletException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException
    {
        String            httpParameter = req.getParameter(Navigator.PARAMETER);
        String            httpStep      = req.getParameter(Navigator.STEP);
        HttpSession       session       = req.getSession();
        RequestDispatcher rd            = null;
        String controllerUrl            = "";

        try {

        	// if session not contains sessionOLM, creates it and
        	// creates a new Navigator with only minimal login and logout routes,
        	// then put all into session.
            if (req.getSession().getAttribute("sessionOLM") == null)
            {
                sessionOLM = new ObjectListManager();
                navigator = new Navigator(getServletContext().getInitParameter(Constants.DISPATCHER_URL));

                // add LOGIN route
                Route r = new Route();
                r.code   = "login";
                r.name   = "Login";
                r.enable = true;
                navigator.addRoute(r);

                // add LOGOUT route
                Route r2  = new Route();
                r2.code   = "logout";
                r2.name   = "Logout";
                r2.enable = true;
                navigator.addRoute(r2);

                sessionOLM.add("navigator", navigator);
                req.getSession().setAttribute("sessionOLM", sessionOLM);
            }

            // retrieve sessionOLM and Navigator from session
            sessionOLM = (ObjectListManager)req.getSession().getAttribute("sessionOLM");
            navigator = (Navigator)sessionOLM.get("navigator");

            // sets the current route
            navigator.setCurrentRoute(httpParameter, httpStep);

            // "steady" navigation, or "out-loop" navigation (login or logout route)
            if (!httpParameter.equals("login") && !httpParameter.equals("logout"))
            {
                // tests user session to retrieve navigation info
                if (monitor.containsSession(session))
                {
                    monitor.updateLastHttpRequestDate(session);
                    controllerUrl = navigator.getControllerUrl();
                } else {
                    MessageBox mb = new MessageBox();
                    mb.setMessage(Message.ANONYM_CODE, getServletContext().getInitParameter(Constants.SESSION_ERROR_MESSAGE), Message.ERROR);
                    mb.addAction("Ok", "logout");
                    ControllerException ctrle = new ControllerException(mb);
                    rd = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.MESSAGE_CONTROLLER_URL));
                    req.setAttribute("exception", ctrle);
                    rd.forward(req, resp);
                    return;
                }
            } else {
                controllerUrl = getServletContext().getInitParameter(Constants.ACCESS_CONTROLLER_URL);
            }

            // log request into dispatcher log
            String s = "HOST=" + req.getRemoteHost() +
                       ",SESSIONID=" + req.getSession().getId() +
                       ",USERNAME=" + monitor.getUsername(req.getSession()) +
                       ",ROUTE=" + httpParameter + ((httpStep==null)? "" : "#" + httpStep);
            log.write(s, "dispatcher");

            // dispatch request to competent controller
            rd = req.getRequestDispatcher(controllerUrl);
            rd.forward(req, resp);

        } catch(Exception e) {

            // servlet error
            log.write("(Dispatcher)" + e.toString(), "ewaf-errors");

            try {
                // transform to ControllerException and dispatch to MessageController
                rd = req.getRequestDispatcher(getServletContext().getInitParameter(Constants.MESSAGE_CONTROLLER_URL));

                MessageBox mb = new MessageBox();
                mb.setMessage(Message.ANONYM_CODE, getServletContext().getInitParameter(Constants.SESSION_ERROR_MESSAGE)+ " - " + e.toString(), Message.ERROR);
                mb.addAction("Ok", "logout");
                ControllerException ctrle = new ControllerException(mb, e);

                req.setAttribute("exception", ctrle);
                rd.forward(req, resp);
            } catch(IOException inner) {
            	// oh no!
        		inner.printStackTrace();
        		log.write("(Dispatcher)" + inner.toString(), "ewaf-errors");
        		StackTraceElement[] ste = inner.getStackTrace();
    			for (int ii=0; ii<ste.length; ii++) log.write("    " + ste[ii].toString(), "ewaf-errors");
        		debug(req, resp, "(Dispatcher)" + getServletContext().getInitParameter(Constants.SERVLET_ERROR_MESSAGE) + "<br>" + inner.toString());
            }
        }
    }


    /**
	 * Returns class-version.
	 */
	public String version()
	{
		return "EmpireWAF" + EmpireServlet.version + "/" + getClass().getName() + ((float)serialVersionUID/100);
	}
}
