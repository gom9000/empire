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


import net.gos95.empire.lang.StackException;
import net.gos95.empire.waf.util.messages.MessageBox;


/**
 * The <code>ControllerException</code> class is the <code>Controller</code>
 * class exception of alpha template.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 */
public class ControllerException
extends StackException
{
	/* the class-version of this class */
	private static final long serialVersionUID = 110L;
 
    private MessageBox mbox;


    /**
     * Creates a new empty <code>ControllerException</code>.
     */
    public ControllerException()
    {
    	super(ControllerException.class, serialVersionUID);
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given message box incapsuling message data.
     *
     * @param   mbox the message box containing exception data
     */
    public ControllerException(MessageBox mbox)
    {
    	super(ControllerException.class, serialVersionUID);
        this.mbox = mbox;
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given message box incapsuling message data, and the
     * exception data object.
     *
     * @param   mbox the message box containing exception data
     * @param   e    the exception data object
     */
    public ControllerException(MessageBox mbox, Exception e)
    {
        super(ControllerException.class, serialVersionUID, e);
        this.mbox = mbox;
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given detail message.
     *
     * @param message the detail message
     */
    public ControllerException(String message)
    {
        super(ControllerException.class, serialVersionUID, message);
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given object.
     *
     * @param obj the object
     */
    public ControllerException(Object obj)
    {
        super(ControllerException.class, serialVersionUID, obj);
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given exception data object.
     *
     * @param e the exception data object
     */
    public ControllerException(Exception e)
    {
        super(ControllerException.class, serialVersionUID, e);
    }


    /**
     * Creates a new <code>ControllerException</code> with
     * the given detail message and string code.
     *
     * @param message the detail message
     * @param code    the string code
     */
    public ControllerException(String message, String code)
    {
        super(ControllerException.class, serialVersionUID, message, code);
    }


    /**
     * Returns the message box.
     *
     * @return  the message box
     */
    public MessageBox getMessageBox()
    {
        return mbox;
    }
}
