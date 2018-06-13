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


import net.gos95.empire.waf.template.alpha.MessageController;
import net.gos95.empire.waf.util.messages.Message;
import net.gos95.empire.waf.util.messages.MessageBox;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * The EWA.alpha message controller servlet.
 */
public class EwaMessageController
extends MessageController
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


	/*
	 * Dispatches message elements to the competent method. 
	 */
	public void dispatcher(MessageBox mbox, HttpServletRequest req,	HttpServletResponse resp)
	throws Exception
	{
		if (mbox.getType() == Message.ERROR)
			log.write("EwaMessageController: " + exception.toStackString() + ": " + mbox.getValue(), "errors");

		RequestDispatcher rd = req.getRequestDispatcher("message.jsp");
		rd.forward(req, resp);
	}
}
