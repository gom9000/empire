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


package net.gos95.empire.waf.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gos95.empire.lang.Versionable;
import net.gos95.empire.util.ListManager;


/**
 * The <code>EmpireServlet</code> abstract servlet class is the tag and root class
 * of Empire WAF library hierarchy.
 * All servlets in the Empire WAF library inherit the methods and the implicit
 * Object List Manager objects defined in this class:
 * <ul>
 *   <li>serverOLM - reference to the server Object List Manager,
 *                   stored in the server context area (not implemented);
 *   <li>applicationOLM - reference to the application Object List Manager,
 *                        stored in the application context area;
 *   <li>sessionOLM - reference to the user-sesion Object List Manager,
 *                    stored in the user session context area;
 * </ul>
 * All servlets in the Empire WAF library must implement the doPost method.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 */
public abstract class EmpireServlet
extends HttpServlet
implements Versionable
{
	private static final long serialVersionUID = 100L;


	/** Empire WAF library release version */
	final public static String version   = "@EMPIREWAF_VERSION@";

	/** Empire WAF library vendor */
	final public static String vendor    = "@EMPIREWAF_VENDOR@"; 

	/** Empire WAF library name */
	final public static String name      = "@EMPIREWAF_NAME@";

	/** Empire WAF library release build time */
	final public static String buildtime = "@EMPIREWAF_BUILDTIME@";

	/** Empire WAF library release authors */
	final public static String author    = "@EMPIREWAF_AUTHOR@";



	/** Implicit reference to the server Object List Manager (not implemented) */
    public ListManager serverOLM;

    /** Implicit reference to the application Object List Manager */
    public ListManager applicationOLM;

    /** Implicit reference to the user-sesion Object List Manager */
    public ListManager sessionOLM;

    /** Contains the client method called to invoke the servlet. */
    public boolean isPost = true;


    /**
	 * Returns this class-object and library version.
	 */
	public String version()
	{
		return "EmpireWAF" + EmpireServlet.version + "/" + getClass().getName() + ((float)serialVersionUID/100);
	}


    /**
     * Returns <code>true</code> if client method called is <code>POST</code>,
     * <code>false</code> otherwise and the flag <code>isPost</code> changes.<br>
     * This is because this method is called by <code>doPost</code>. 
     *
     * @return <code>true</code> if client method called is <code>POST</code>,
     *         <code>false</code> otherwise
     */
    public boolean isPost()
    {
        boolean prevValue = isPost;
        if (!isPost) isPost = true;
        return prevValue;
    }


    /**
     * Sets flag <code>isPost</code> to <code>false</code>
     * and call <code>doPost</code> method.<br>
     * This is to prevent <code>doGet</code> calls.
     *
     * @param  request  the servlet request object
     * @param  response the servlet response object
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        isPost = false;
        doPost(request, response);
    }


    /**
     * Returns the HTTP request attribute corresponding to the given attribute name.
     *
     * @param request the servlet request object
     * @param name    the name of the HTTP attribute to return
     * @return the object value of the attribute
     * @exception ServletException
     */
    public Object getRequestAttribute(HttpServletRequest request, String name)
    throws ServletException
    {
    	return request.getSession().getAttribute(name);
    }


    /**
     * Returns the HTTP request parameter or attribute corresponding
     * to the given parameter/attribute name.
     * If exists boths parameter and attribute then returns parameter value.
     *
     * @param request the servlet request object
     * @param name    the name of the HTTP parameter/attribute to return
     * @return the string value of parameter/attribute
     * @exception ServletException
     */
    public String getRequestParameterOrAttribute(HttpServletRequest request, String name)
    throws ServletException
    {
    	String value = request.getParameter(name);

    	if (value == null) value = (String)request.getSession().getAttribute(name);

    	return value;
    }


    /**
     * Debug to video the given string error message.
     * 
     * @param request  the servlet request object
     * @param response the servlet response object
     * @param str string message to debug
     */
    public void debug(HttpServletRequest request, HttpServletResponse response, String str)
    {
        try {
        	response.setContentType("text/html");
            java.io.PrintWriter out = response.getWriter();
            out.println("<html><head></head><body text=#000000 bgcolor=#ffffff><table border=0 cellpadding=2 cellspacing=0 width=100%>");
            out.println("<tr><td rowspan=3 width=1% nowrap><b><font face=times color=#0039b6 size=10>Debug Info</font></b><td>&nbsp;</td></tr>");
            out.println("<tr><td bgcolor=#3366cc><font face=arial,sans-serif color=#ffffff><b>"+version()+"</b></td></tr>");
            out.println("<tr><td>&nbsp;</td></tr></table><blockquote>");
            out.println("<H3>" + str + "</H3>");
            out.println(request);
            out.println("<p></blockquote><table width=100% cellpadding=0 cellspacing=0><tr><td bgcolor=#3366cc><img alt='' width=1 height=4></td></tr></table></body></html>");
            out.close();
        } catch(Exception e) { e.printStackTrace(); }
    }


    /**
     * All servlets in the Empire WAF library must implement this method.
     * 
     * @param  request  the servlet request object
     * @param  response the servlet response object
     * @exception ServletException
     */
    public abstract void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException;
}
