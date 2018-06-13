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


package net.gos95.empire.configuration;


import net.gos95.empire.lang.EmpireException;


/**
 * La classe <code>ConfigurationException</code> incapsula le eccezioni generate
 * dagli oggetti Configuration in presenza di problemi di caricamento o
 * tipizzazione delle proprieta' di configurazione.
 * 
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 */
public class ConfigurationException
extends EmpireException
{
	// the class-version of this class
	private static final long serialVersionUID = 100L;

	/** Contiene lo stack di messaggi di errore originale relativo all'errore corrente */
	private String internalMessage;


	/**
	 * Crea una nuova <code>ConfigurationException</code>
	 * senza messaggio di dettaglio.
	 */
	public ConfigurationException()
	{
		super(ConfigurationException.class, serialVersionUID);
	}


	/**
	 * Crea una nuova <code>ConfigurationException</code>
	 * specificando nel messaggio di dettaglio l'evento che l'ha causata.
	 * 
	 * @param message messaggio di dettaglio che descrive l'eccezione
	 * @param internalMessage stack di messaggi originale che ha causato l'errore corrente
	 */
	public ConfigurationException(String message, String internalMessage)
	{
		super(ConfigurationException.class, serialVersionUID, message);
		this.internalMessage = internalMessage;
	}


	/**
	 * Crea una nuova <code>ConfigurationException</code>
	 * specificando nel messaggio di dettaglio l'evento che l'ha causata.
	 * 
	 * @param message messaggio di dettaglio che descrive l'eccezione
	 */
	public ConfigurationException(String message)
	{
		this(message, null);
	}


	/**
     * Restituisce lo stack di messaggi di errore originale
     * relativo all'errore corrente.
     * 
     * @return messaggio di errore originale
     */
    public String getInternalMessage()
    {
        return this.internalMessage;
    }
}
