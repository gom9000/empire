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


import java.util.Enumeration;

import net.gos95.empire.configuration.core.DefinitionsTable;
import net.gos95.empire.configuration.core.ManagedProperty;


//TODO Configuration - TestCase.
/**
 * La classe <code>ManagedConfiguration</code> estende le capacita' di <code>SimpleConfiguration</code>
 * occupandosi anche della verifica delle proprieta' caricate dai file di configurazione specificati.
 * <br>
 * Ogni proprieta' viene verificata sia per quanto riguarda la tipologia di dato che rappresenta
 * sia per quanto riguarda l'obbligatorieta' del campo, in accordo col contenuto del file di
 * definizione specificato. In caso di problemi nella verifica viene generata una eccezione
 * con relativo messaggio.
 * <br>
 * Uno dei vantaggi del verificare tutte le proprieta' chiamando un unico metodo e' che in questo
 * modo qualunque problema viene subito individuato in fase di configurazione, senza il rischio
 * che invece si presenti in uno stadio avanzato dell'elaborazione.
 * Altro vantaggio e' quello di poter usare i metodi di get delle proprieta' senza dover gestire
 * ogni volta una <code>ConfigurationException</code>.
 * <br>
 * Un estratto di codice che mostra l'utilizzo della classe e' il seguente:<br>
 * <pre>
 *     ...
 *     ...
 *     ManagedConfiguration conf;
 *
 *     try {
 *          conf = new ManagedConfiguration();
 *          conf.setDefinitionsFile(defsFile);
 *          conf.load(confFile1);
 *          conf.load(confFile2);
 *          conf.manage();
 *      } catch (ConfigurationException e) {
 *          System.out.println(e.getMessage());
 *          System.out.println(e.getInternalMessage());
 *      }
 *      ...
 *      ...
 *      String prop1 = (String)conf.getManagedValue("alias1");
 *      String[] prop2 = (String[])conf.getManagedValue("alias2");
 *      Integer prop3 = (Integer)conf.getManagedValue("alias3");
 *      ...
 *      ...
 *      ...
 *      contenuto del file defsFile:
 *      alias1,nome-prop1,STRING,MANDATORY,def_str_val
 *      alias2,nome-prop2,ASTRING,MANDATORY,v1,v2,v3,v4
 *      alias3,nome-prop3,INTEGER,MANDATORY,13
 *  </pre>
 * 
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 * @see net.gos95.empire.configuration.SimpleConfiguration
 */
public class ManagedConfiguration
extends SimpleConfiguration
{
	// the class-version of this class
	private static final long serialVersionUID = 100L;

	/* Contiene il puntamento al file di definizione delle proprieta' di configurazione */
	private String definitionsFile;
	private DefinitionsTable table;


	/**
	 * Costruisce un nuovo oggetto <code>ManagedConfiguration</code> vuoto.
	 */
	public ManagedConfiguration()
	throws ConfigurationException
	{
		super();
	}


	/**
	 * Costruisce un nuovo oggetto <code>ManagedConfiguration</code> che si occupa del
	 * caricamento delle proprieta' dal file di configurazione specificato e della verifica
	 * delle stesse in accordo col contenuto del file di definizione specificato.
	 * 
	 * @param configurationFile file contenente le proprieta' di configurazione
	 * @param definitionsFile file contenente le definizioni delle proprieta' di configurazione
	 * @exception ConfigurationException in caso di problemi di caricamento del file
	 */
	public ManagedConfiguration(String configurationFile, String definitionsFile)
	throws ConfigurationException
	{
		super(configurationFile);
		this.definitionsFile = definitionsFile;

		manage();
	}


	/**
     * Imposta il puntamento al nome del file contenente le definizioni delle proprieta'
     * di configurazione.
     * <br>
     * Tale file contiene, per ogni proprietà contenuta nei file di configurazione caricati
     * tramite <code>load</code> o embedded, un record formattato come segue:
     * <br>
     * &nbsp;&nbsp;<code>constant_alias,property_name,data_type,optional_or_mandatory,default_value</code>
     * <br>
     * dove:
     * <ul>
     *   <li><code>alias</code> - alias utilizzato per puntare al nome della proprietà;</li>
     *   <li><code>property_name</code> - il nome della proprietà così come è scritta nel file di configurazione;</li>
     *   <li><code>data_type</code> - il tipo di dato associato al valore della proprietà: STRING, ASTRING,
     *   INTEGER, AINTEGER, NUMBER, ANUMBER, BOOLEAN, ABOOLEAN</li>
     *   <li><code>optional_or_mandatory</code> - indica se il valore della proprietà è opzionale (OPTIONAL)
     *   o obbligatorio (MANDATORY);</li>
     *   <li><code>default_value</code> - contiene il valore di default della proprietà nel caso in cui
     *   nel file non sia presente un valore o questo non sia valido.</li>
     * </ul>
     * 
     * @param definitionsFile file contenente le definizioni delle proprieta' di configurazione
     */
    public void setDefinitionsFile(String definitionsFile)
    {
    	this.definitionsFile = definitionsFile;
    }


	/**
     * Restituisce il puntamento al nome del file contenente le definizioni delle proprieta'
     * di configurazione.
     * 
     * @return puntamento al nome del file di definizioni
     */
    public String getDefinitionsFile()
    {
        return this.definitionsFile;
    }


    //TODO Configuration - gestione dei valori multipli (array).
    /**
     * Verifica una ad una tutte le proprieta' lette dai file di configurazione
     * confrontandole con le informazioni contenute nel file di definizioni.<br>
     * Se anche una sola delle proprieta' fallisce la verifica, viene generata
     * una eccezione.
     * <br>
     * Questo metodo viene automaticamente richiamato nel costruttore con parametri
     * dell'oggetto. Invece va richiamato manualmente se si utilizza il costruttore
     * vuoto oppure comunque dopo la chiamata al metodo refresh di <code>PropertiesLoader</code>.  
     */
    public void manage()
    throws ConfigurationException
    {
    	String propname = null;

    	try {
    		table = new DefinitionsTable(definitionsFile);
    	} catch(Exception e) {
    		throw new ConfigurationException(super.notifications.INVALID_DEFINITIONS_FILE_MESSAGE + ": " + definitionsFile, e.toString());
    	}

    	try {
    		for (Enumeration e = table.getTable().elements(); e.hasMoreElements(); )
    		{
    			ManagedProperty property = (ManagedProperty)e.nextElement();
    			propname = property.name;

    			property.checked = false;
    			property.defaultValueUsed = false;
    			property.value = null;

    			if (property.name == null) throw new ConfigurationException(super.notifications.INVALID_PROPERTY_DEF_MESSAGE + ": " + propname, e.toString());

    			if (super.getProperties().containsKey(property.name))
    			{
    			    property.value = (String)super.getProperties().get(property.name);
    			    if (property.mandatory && property.value == null)
    			    {
    			    	property.value = property.defaultValue;
    			    	property.defaultValueUsed = true;
    			    }
    			    property.checked = true;
    			} else {
    				throw new ConfigurationException(super.notifications.INVALID_PROPERTY_DEF_MESSAGE + ": " + propname, e.toString());
    			}
    		}
    	} catch(Exception e) {
    		throw new ConfigurationException(super.notifications.INVALID_PROPERTY_DEF_MESSAGE + ": " + propname, e.toString());
    	} 
    }


    // TODO Configuration - implements getManagedObjectValue()
    public Object getManagedObjectValue()
    {
    	return null;
    }
}
