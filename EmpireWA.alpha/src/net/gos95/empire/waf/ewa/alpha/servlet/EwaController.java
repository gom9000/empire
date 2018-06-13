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


package net.gos95.empire.waf.ewa.alpha.servlet;


import net.gos95.empire.waf.ewa.alpha.business.BusinessException;
import net.gos95.empire.waf.ewa.alpha.business.EwaBusiness;
import net.gos95.empire.waf.template.alpha.Constants;
import net.gos95.empire.waf.template.alpha.Controller;
import net.gos95.empire.waf.template.alpha.ControllerException;
import net.gos95.empire.waf.util.messages.Message;
import net.gos95.empire.waf.util.messages.MessageBox;
import net.gos95.empire.waf.util.navigator.Route;

import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * The EWA.alpha controller servlet.
 */
public class EwaController
extends Controller
{
	private static final long serialVersionUID = 100L;


	/*
	 * Inits controller.
	 */
	public void init()
	{
		super.init();

		try {
			// load messages
			Iterator iterator = EwaBusiness.loadMessages().iterator();
			while (iterator.hasNext())
			{
				Message message = (Message)iterator.next();
				messages.addMessage(message.code, message);
			}
		} catch (Exception e) {
			log.write("EwaController.init(): " + e.toString(), "errors");
		}
	}


	/*
	 * Dispatches the request.
	 * 
	 * @param code the route code
	 * @param step the route code-step
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	public void dispatcher(String code, String step, HttpServletRequest req, HttpServletResponse resp) 
	throws ControllerException
	{
		if (code.equals("login"))
		{
			login(req, resp);
		}
		else if (code.equals("logout"))
		{
			logout(req, resp);
		}
		else if (code.equals("home"))
		{
			homePage(req, resp);
		}
		else if (code.equals("f1"))
		{
			if (step == null)
			    f1Page(req, resp);
			else
				f1(req, resp);
		}
		else if (code.equals("f2"))
		{
			f2(req, resp);
	    }
	}

	/*
	 * Login method.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String accessLogString = "HOST=" + req.getRemoteHost() + ",SESSIONID=" + req.getSession().getId() + ",USERNAME=" + username;

			boolean isAuthenticated = EwaBusiness.login(username, password);
			if (isAuthenticated)
			{
                Route rlogout = new Route();
				rlogout.code = "logout";
				rlogout.name = "Logout";
				rlogout.id = 0;
				rlogout.menuitem = "Logout";
				rlogout.enable = true;
				navigator.addRoute(rlogout);

				Route rhome = new Route();
				rhome.id = 1;
				rhome.code = "home";
				rhome.name = "Home Page";
				rhome.description = "EWA.alpha Home Page";
				rhome.enable = true;
				rhome.masterCode = null;
				rhome.resourceUrl = "home.jsp";
				rhome.controllerUrl = getServletContext().getInitParameter(Constants.ACCESS_CONTROLLER_URL);
				rhome.menuitem = "Home";
				navigator.addRoute(rhome);

				Route rf1 = new Route();
				rf1.id = 2;
				rf1.code = "f1";
				rf1.name = "Function One";
				rf1.description = "The function One";
				rf1.enable = true;
				rf1.masterCode = null;
				rf1.resourceUrl = "f1.jsp";
				rf1.controllerUrl = getServletContext().getInitParameter(Constants.ACCESS_CONTROLLER_URL);
				rf1.menuitem = "Function1";
				navigator.addRoute(rf1);

				Route rf2 = new Route();
				rf2.id = 2;
				rf2.code = "f2";
				rf2.name = "Function Two";
				rf2.description = "The function Two (causes of ewaf-error)";
				rf2.enable = true;
				rf2.masterCode = null;
				rf2.resourceUrl = null;
				rf2.controllerUrl = getServletContext().getInitParameter(Constants.ACCESS_CONTROLLER_URL);
				rf2.menuitem = "Function2";
				navigator.addRoute(rf2);

				// update session objects
				monitor.addSession(req.getSession().getId(), username, req.getSession(), req.getRemoteAddr());
				navigator.setCurrentRoute("home");

                log.write(accessLogString + ", LOGIN", "access");

				homePage(req, resp);

			} else {
				log.write(accessLogString + ", LOGIN ERROR", "access");

				MessageBox mb = new MessageBox();
				mb.setMessage(messages.getMessage("LOGIN_ERROR"));
				mb.addAction("logout and go to login page", "logout");
				throw new ControllerException(mb);
			}
		} catch (ControllerException ce) {
			throw ce;
		} catch (Exception e) {
			log.write("EwaController: " + e.toString(), "errors");
			MessageBox mb = new MessageBox();
			mb.setMessage(messages.getMessage("GENERIC_ERROR"));
			mb.addAction("ok", "logout");
			throw new ControllerException(mb, e);
		}
	}


	/*
	 * Go to home page.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void homePage(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			RequestDispatcher rd = req.getRequestDispatcher(navigator.getResourceUrl());
			rd.forward(req, resp);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}


	/*
	 * Go to login page.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void loginPage(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
			rd.forward(req, resp);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}


	/*
	 * Logout user and go to login page.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void logout(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			if (monitor.containsSession(req.getSession()))
			{
			    String accessLogString = "HOST=" + req.getRemoteHost() + ",SESSIONID=" + req.getSession().getId() + ",USERNAME=" + monitor.getUsername(monitor.getKey(req.getSession()));
			    log.write(accessLogString + ", LOGOUT", "access");
			}
			super.free(req, resp);
		} catch (Exception e) {
		    throw new ControllerException(e);
		}

		loginPage(req, resp);
	}


	/*
	 * Go to f1 function page.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void f1Page(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			RequestDispatcher rd = req.getRequestDispatcher(navigator.getResourceUrl());
			rd.forward(req, resp);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}


	/*
	 * Call the f1 function and display result message.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void f1(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			EwaBusiness.functionOne();

		    MessageBox mb = new MessageBox();
		    mb.setMessage(messages.getMessage("F1_OK"));
		    mb.addAction("return to home page", "home");
		    throw new ControllerException(mb);

		} catch (BusinessException e) {
		    MessageBox mb = new MessageBox();
		    mb.setMessage(Message.ANONYM_CODE, e.getMessage(), Message.ERROR);
		    mb.addAction("ok", "home");
		    throw new ControllerException(mb, e);
	    }
	}


	/*
	 * Call the f2 function page that causes ewaf error.
	 * 
	 * @param req  the servlet request object
     * @param resp the servlet response object
	 */
	private void f2(HttpServletRequest req, HttpServletResponse resp)
	throws ControllerException
	{
		try {
			RequestDispatcher rd = req.getRequestDispatcher(null);
			rd.forward(req, resp);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}
}
