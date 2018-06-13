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
import java.util.ArrayList;
import java.util.Properties;

import net.gos95.empire.configuration.ConfigurationException;
import net.gos95.empire.lang.EmpireObject;


/**
 * La classe <code>PropertiesLoader</code> si occupa di caricare in memoria e rendere
 * disponibili alla consultazione le proprieta' di configurazione contenute in uno
 * o piu' file specificati.
 * <br>
 * Le proprieta' vengono lette dai file di configurazione specificati nelle chiamate
 * al metodo <code>load</code> e nei file di configurazione embedded, specificati
 * mediante valorizzazione della proprieta' nominata <code>EMBED_FILE_PROP_NAME</code>
 * (per default <code>EMBED_FILE_PROP_NAME</code> = <code>embed-file</code>).
 *
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 */
public class PropertiesLoader
extends EmpireObject
{
	// the class-version of this class
	private static final long serialVersionUID = 100L;

	/** Contiene il nome della proprietà per il puntamento al file embedded */
	public static String EMBED_FILE_PROP_NAME = "embed-file";

	/** Contiene l'elenco dei messaggi di notifica */
	protected NotificationsTable notifications;

    /** Contiene tutte le proprieta' di configurazione lette */
	private Properties loadedProperties;

	/** Contiene la lista dei file di configurazione specificati */
	private ArrayList configurationFileList;


	/**
	 * Costruisce un nuovo oggetto <code>PropertiesLoader</code>.
	 * 
	 * @param notificationsFile puntamento al file contenente il testo
	 * dei messaggi di notifica.
	 */
	public PropertiesLoader(String notificationsFile)
	throws ConfigurationException
	{
		this(PropertiesLoader.class, serialVersionUID, notificationsFile);
	}


	/**
	 * Costruisce un nuovo oggetto <code>PropertiesLoader</code>.
	 * 
	 * @param clazz    classname
     * @param lversion version
	 * @param notificationsFile puntamento al file contenente il testo
	 * dei messaggi di notifica.
	 */
	public PropertiesLoader(Class clazz, long lversion, String notificationsFile)
	throws ConfigurationException
	{
		super(clazz, lversion);

		this.loadedProperties = new Properties();
		this.configurationFileList = new ArrayList();

		try {
			notifications = new NotificationsTable(notificationsFile);
		} catch (Exception e) {
			throw new ConfigurationException(NotificationsTable.INVALID_NOTIFICATIONS_FILE + ": " + notificationsFile, e.toString());
		}
	}


	/**
	 * Esegue il caricamento delle proprieta' dal file di configurazione specificato.
	 * <br>
	 * Il file di configurazione deve essere un file di tipo <code>properties</code>
	 * o un file xml con doctype <code>http://java.sun.com/dtd/properties.dtd</code>.
	 * <br>
	 * Se la stessa proprietà è presente all'interno di più file, il suo valore
	 * risultera' quello relativo all'ultimo caricato.
	 * <br>
	 * Se nel file è valorizzata la proprieta' <code>EMBED_FILE_PROP_NAME</code>,
	 * allora il caricamento sara' reiterato al file embedded.
	 * 
	 * @param configurationFile file contenente le proprieta' da caricare
	 */
	public void load(String configurationFile)
	throws ConfigurationException
	{
    	try {
    		if (configurationFile != null && configurationFile.endsWith(".xml"))
    			this.loadedProperties.loadFromXML(new FileInputStream(configurationFile));
    		else
    		    this.loadedProperties.load(new FileInputStream(configurationFile));

    		if (!this.configurationFileList.contains(configurationFile))
    			this.configurationFileList.add(configurationFile);
    	} catch (Exception e) {
    		throw new ConfigurationException(notifications.INVALID_PROPERTIES_FILE_MESSAGE + ": " + configurationFile, e.toString());
    	}
    	loadEmbedded();
	}


	private void loadEmbedded()
	throws ConfigurationException
	{
		String embeddedFile;
		String[] embeddedFiles = null;
		String separator = ",";

		if (this.loadedProperties.containsKey(EMBED_FILE_PROP_NAME))
		{
			embeddedFile = this.loadedProperties.getProperty(EMBED_FILE_PROP_NAME);
			if (embeddedFile != null && embeddedFile.length() > 0)
			{
				embeddedFiles = (embeddedFile.trim() + separator).split(separator);
				if (embeddedFiles != null)
		    	{
		    		for (int ii=0; ii < embeddedFiles.length; ii++)
		    		{
		    			if (embeddedFiles[ii] != null)
		    				if (!this.configurationFileList.contains(embeddedFiles[ii]))
		    					load(embeddedFiles[ii].trim());
		    		}
		    	}
				this.loadedProperties.remove(EMBED_FILE_PROP_NAME);
			}
		}
	}


	/**
	 * Riesegue il caricamento delle proprieta' da tutti i file di configurazione
	 * precedentemente specificati.
	 * <br>
	 * Tutte le proprieta' precedentemente caricate vengono cancellate
	 * prima del nuovo caricamento.
	 */
	public void reload()
	throws ConfigurationException
	{
	// TODO XNEW Configuration - PropertiesLoader.reload()
	/*
		this.loadedProperties.clear();

		Iterator iterator = this.configurationFileList.iterator();
		while (iterator != null && iterator.hasNext())
            load((String)iterator.next());
     */
	}


	/**
     * Restituisce l'oggetto <code>Properties</code> contenente tutte le proprieta'
     * lette dai file di configurazione.
     * 
     * @return la properties contenente tutte le proprieta'
     */
    public Properties getProperties()
    {
        return this.loadedProperties;
    }


	/**
     * Restituisce l'oggetto <code>ArrayList</code> contenente la lista dei file
     * di configurazione specificati nelle chiamate al metodo <code>load</code>.
     * 
     * @return la lista dei file di configurazione
     */
    public ArrayList getConfigurationFileList()
    {
        return this.configurationFileList;
    }
}
