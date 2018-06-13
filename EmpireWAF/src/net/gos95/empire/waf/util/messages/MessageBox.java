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



import java.util.Enumeration;
import java.util.Hashtable;

import net.gos95.empire.dto.Dto;


/**
 * The <code>MessageBox</code> class represents a transfer object for
 * messages with attached actions.<br>
 * An action is a name-value pair of strings attached to message.
 *
 * @author  Alessandro Fraschetti
 * @version 1.1, 01/02/2006
 * @see     net.gos95.empire.waf.util.messages.Message
 */
public class MessageBox
extends Dto
{
	/* the class-version of this class */
	private static final long serialVersionUID = 101L;

    private Message   message;
    private Hashtable actions;


    /**
     * Creates a new empty <code>MessageBox</code> object.
     */
    public MessageBox()
    {
        super(MessageBox.class, serialVersionUID);
        actions = new Hashtable();
    }


    /**
     * Sets message info.
     *
     * @param  code  the message code
     * @param  value the message string value
     * @param  type  the message type
     */
    public void setMessage(String code, String value, int type)
    {
        message = new Message(code, value, type);
    }


    /**
     * Sets message info.
     *
     * @param  code   the message code
     * @param  values the message string-array values
     * @param  type   the message type
     */
    public void setMessage(String code, String[] values, int type)
    {
        message = new Message(code, values, type);
    }


    /**
     * Sets message info.
     *
     * @param  message the message
     */
    public void setMessage(Message message)
    {
        this.message = message;
    }


    /**
     * Adds an action to message box.
     *
     * @param  name   the action name
     * @param  action the action value
     */
    public void addAction(String name, String action)
    {
        actions.put(name, action);
    }


    /**
     * Returns an enumeration of the message box actions.
     *
     * @return the actions enumeration
     */
    public Enumeration getActionEnum()
    {
        return actions.keys();
    }


    /**
     * Returns the action identified by given name.
     *
     * @param  name the action name
     * @return the action
     */
    public String getAction(String name)
    {
        return (String)actions.get(name);
    }


    /**
     * Returns the message object.
     *
     * @return the string message value
     */
    public Message getMessage()
    {
        return message;
    }


    /**
     * Returns the message code.
     *
     * @return the message string code
     */
    public String getCode()
    {
        return message.code;
    }


    /**
     * Returns the message value.
     *
     * @return the message string value
     */
    public String getValue()
    {
        return message.value;
    }


    /**
     * Returns the message values.
     *
     * @return the message string-array values
     */
    public String[] getValues()
    {
        return message.values;
    }


    /**
     * Returns the message type
     *
     * @return the message int type
     */
    public int getType()
    {
        return message.type;
    }
}
