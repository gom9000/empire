/*
 * E  M  P  I  R  E   Library
 *   ________        _________________.________
 *  /  _____/  ____ /   _____/   __   \   ____/
 * /   \  ___ /  _ \\_____  \\____    /____  \
 * \    \_\  (  <_> )        \  /    //       \
 *  \______  /\____/_______  / /____//______  /
 *         \/              \/               \/
 * Copyright (c) 2007 2009 by
 * Alessandro Fraschetti (gos95@gommagomma.net)
 * 
 * This file is part of the Empire library.
 * For more information about Empire visit:
 *     http://gommagomma.net/gos95/Empire
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


package net.gos95.empire.logger;


import net.gos95.empire.lang.EmpireException;


/**
 * The class <code>LoggerException</code> is the <code>Logger</code>
 * class exception.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 * @see     net.gos95.empire.logger.Logger
 * @since   1.3
 */
public class LoggerException
extends EmpireException
{
	private static final long serialVersionUID = 100L;


    /**
     * Costruisce una <code>LoggerException</code> senza messaggio
     * di dettaglio.
     */
    public LoggerException()
    {
        super(LoggerException.class, serialVersionUID);
    }


    /**
     * Costruisce una <code>LoggerException</code> con il messaggio
     * di dettaglio specificato.
     *
     * @param   message   messaggio di dettaglio.
     */
    public LoggerException(String message)
    {
    	super(LoggerException.class, serialVersionUID, message);
    }
}
