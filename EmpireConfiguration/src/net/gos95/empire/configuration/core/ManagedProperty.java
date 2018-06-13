/*
 * E  M  P  I  R  E   Library
 *   ________        _________________.________
 *  /  _____/  ____ /   _____/   __   \   ____/
 * /   \  ___ /  _ \\_____  \\____    /____  \
 * \    \_\  (  <_> )        \  /    //       \
 *  \______  /\____/_______  / /____//______  /
 *         \/              \/               \/
 * Copyright (c) 2007 2009 2010 by
 * Alessandro Fraschetti (gos95@gommagomma.net)
 * 
 * This file is part of the Empire library.
 * For more information about Empire visit:
 *     http://gommagomma.net/gos95/empire
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


package net.gos95.empire.configuration.core;

import net.gos95.empire.dto.Dto;


/**
 * La classe <code>ManagedProperty</code> incapsula una proprieta'
 * di configurazione con tutti i suoi attributi.
 * 
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 */
public class ManagedProperty
extends Dto
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


	/** Alias del nome della proprieta' espresso nel file di configurazione */
    public String alias;

    /** Nome della proprieta' espresso nel file di configurazione */
    public String name;

    /** Valore della proprieta' */
    public String value;

    /** data-type della proprieta' */
    public String datatype;

    /** Indica se la proprieta' deve obbligatoriamente avere un valore */
    public boolean mandatory;

    /** Contiene il valore di default da attribuire alla proprieta' nel caso sia vuota nel file di configurazione */
    public String defaultValue;

    /** Specifica se la proprieta' e' verificata o meno */
    public boolean checked;

    /** Specifica se come valore di questa proprieta' e' stato utilizzato il valore di default */
    public boolean defaultValueUsed;


    /**
     * Creates a new empty <code>ManagedProperty</code>.
     */
    public ManagedProperty()
    {
    	super(ManagedProperty.class, serialVersionUID);
    }
}
