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


package net.gos95.empire.waf.util.messages;


import net.gos95.empire.util.ObjectListManager;

// TODO WAF - TestCase net.gos95.empire.waf.util.messages
/**
 * The <code>MessageManager</code> class manages <code>Message</code>
 * object.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 * @see     net.gos95.empire.waf.util.messages.Message
 */
public class MessageManager
extends ObjectListManager
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


    /**
     * Creates a new <code>MessageManager</code> object.
     */
    public MessageManager()
    {
        super(MessageManager.class, serialVersionUID);
    }


    /**
     * Adds a new message object identified by given code.
     *
     * @param code the message unique code
     * @param message the message object
     */
    public void addMessage(String code, Message message)
    {
        super.add(code, message);
    }


    /**
     * Returns the message object identified by given code.
     *
     * @param code the message unique code
     * @return the message object
     */
    public Message getMessage(String code)
    {
        return (Message)super.get(code);
    }
}
