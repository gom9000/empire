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


import net.gos95.empire.dto.Dto;


/**
 * The <code>Message</code> class is the struct contains
 * the info of a textual message.
 *
 * @author  Alessandro Fraschetti
 * @version 1.1, 01/02/2006
 */
public class Message
extends Dto
{
	/* the class-version of this class */
	private static final long serialVersionUID = 101L;


    /** message type: info */
    static public final int INFORMATION = 1;

    /** message type: warn */
    static public final int WARNING     = 2;

    /** message type: err */
    static public final int ERROR       = 3;

    /** message type: quest */
    static public final int QUESTION    = 4;

    /** message code used by anonym messages */
    static public final String ANONYM_CODE = "ANONYM";


    /** the unique string code that identify message */
    public String code;

    /** the string value of the message */
    public String value;

    /** the string-array values of the message */ 
    public String values[];

    /** the type of the message */
    public int    type;


    /**
     * Creates a new empty <code>Message</code> object.
     */
    public Message()
    {
        super(Message.class, serialVersionUID);
    }


    /**
     * Creates a new <code>Message</code> object copy of
     * the given Message object.
     *
     * @param m the message object to copy
     */
    public Message(Message m)
    {
        this();

        this.code   = m.code;
        this.value  = m.value;
        this.values = m.values;
        this.type   = m.type;
    }


    /**
     * Creates a new <code>Message</code> object with
     * the given params.
     *
     * @param code  the unique message code
     * @param value the message value
     * @param type  the message type
     */
    public Message(String code, String value, int type)
    {
        this();

        this.code   = code;
        this.value = value;
        this.type   = type;
    }


    /**
     * Creates a new <code>Message</code> object with
     * the given params.
     *
     * @param code   the unique message code
     * @param values the message values array
     * @param type   the message type
     */
    public Message(String code, String[] values, int type)
    {
        this();

        this.code   = code;
        this.values = values;
        this.type   = type;
    }
}
