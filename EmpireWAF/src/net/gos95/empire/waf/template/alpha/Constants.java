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


/**
 * The <code>Constants</code> class contains the constant names of the
 * alpha template parameters.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 */
public class Constants
{
    /** The constant name of the servlet dispatcher url as mapped in web-xml */
    static public final String DISPATCHER_URL = "dispatcherURL";

	/** The constant name of the servlet message controller url as mapped in web-xml */
    static public final String MESSAGE_CONTROLLER_URL = "messageControllerURL";

    /** The constant name of the servlet access controller url as mapped in web-xml */
    static public final String ACCESS_CONTROLLER_URL = "accessControllerURL";


    /** The constant name of the servlet error message as mapped in web-xml */
    static public final String SERVLET_ERROR_MESSAGE = "servletErrorMessage";

    /** The constant name of the session error message as mapped in web-xml */
    static public final String SESSION_ERROR_MESSAGE = "sessionErrorMessage";

}
