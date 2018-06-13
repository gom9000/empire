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


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import net.gos95.empire.lang.EmpireObject;


/**
 * La classe <code>NotificationsTable</code> si occupa di caricare in memoria
 * e rendere disponibili alla consultazione i messaggi di testo, utilizzati dagli
 * oggetti <code>PropertiesLoader</code>, per le notifiche di warning ed errori.
 * <br>
 * Il testo dei messaggi di notifica viene letto dal file specificato nel
 * costruttore della classe.
 *
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 */
public class NotificationsTable
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;


	/** Contiene il testo del messaggio di errore relativo all'impossibilita' di aprire il file delle notifiche */
	public static final String INVALID_NOTIFICATIONS_FILE = "Impossibile aprire il file delle notifiche";

	/** Contiene il testo del messaggio di errore relativo all'impossibilita' di aprire il file delle definizioni */
	public final String INVALID_DEFINITIONS_FILE_MESSAGE;

	/** Testo del messaggio di errore trlativo alla non validita' del file di configurazione specificato */
	public final String INVALID_PROPERTIES_FILE_MESSAGE;

	/** Testo del messaggio di errore relativo alla mancanza di un valore per la proprieta' specificata */
	public final String MISSING_PROPERTY_VALUE_MESSAGE;

	/** Testo del messaggio di errore relativo alla non validita' del valore della proprieta' specificata */
	public final String INVALID_PROPERTY_VALUE_MESSAGE;

	/** Testo del messaggio di errore relativo alla non validita' della definizione della proprieta' specificata */
	public final String INVALID_PROPERTY_DEF_MESSAGE;


	/**
	 * Costruisce e popola un nuovo oggetto <code>NotificationsTable</code>
	 * con il testo dei messaggi di notifica contenuti nel file specificato.
	 * 
	 * @param messagefile file di configurazione contenente il testo dei messaggi
	 */
	public NotificationsTable(String messagefile)
	throws Exception
	{
		super(NotificationsTable.class, serialVersionUID);

		Properties messageProperties = new Properties();
		InputStream is;

		try {
		    is = new FileInputStream(messagefile);
		} catch(FileNotFoundException fnfe) {
			is = getClass().getResourceAsStream(messagefile);
		}
		messageProperties.load(is);

		this.INVALID_DEFINITIONS_FILE_MESSAGE = messageProperties.getProperty("INVALID_DEFINITIONS_FILE_MESSAGE");
		this.INVALID_PROPERTIES_FILE_MESSAGE = messageProperties.getProperty("INVALID_PROPERTIES_FILE_MESSAGE");
		this.MISSING_PROPERTY_VALUE_MESSAGE = messageProperties.getProperty("MISSING_PROPERTY_VALUE_MESSAGE");
		this.INVALID_PROPERTY_VALUE_MESSAGE = messageProperties.getProperty("INVALID_PROPERTY_VALUE_MESSAGE");
		this.INVALID_PROPERTY_DEF_MESSAGE = messageProperties.getProperty("INVALID_PROPERTY_DEF_MESSAGE");
	}
}
