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

import net.gos95.empire.configuration.core.PropertiesLoader;


/**
 * La classe <code>SimpleConfiguration</code> si occupa del caricamento delle proprieta'
 * dai file di configurazione specificati e dell'utilizzo delle stesse mediante opportuni
 * metodi getter.
 * <br>
 * Sono gestite la tipizzazione e l'obbligatorieta' della presenza di valori per le proprieta',
 * restituendo situazioni di errore nei casi queste non siano rispettate.
 * <br>
 * Le proprieta' vengono lette dai file di configurazione specificati nelle chiamate al metodo
 * <code>load</code> (ereditato da <code>PropertiesLoader</code>) e nei file di configurazione embedded,
 * specificati mediante valorizzazione della proprieta' nominata <code>EMBED_FILE_PROP_NAME</code>
 * (per default <code>EMBED_FILE_PROP_NAME</code> = <code>embed-file</code>).
 * <br>
 * Un estratto di codice che mostra l'utilizzo dell'oggetto <code>SimpleConfiguration</code>
 * e' il seguente:<br>
 * <pre>
 *     ...
 *     ...
 *     SimpleConfiguration conf;
 *
 *     try {
 *          conf = new SimpleConfiguration();
 *          conf.load(confFile1);
 *          conf.load(confFile2);
 *          ...
 *          ...
 *          String prop1 = conf.getStringValue("prop1name");
 *          Boolean prop2 = conf.getStringValue("prop2name");
 *          Integer prop3 = conf.getIntegerValue("prop3name", true);
 *          ...
 *          ...
 *      } catch (ConfigurationException e) {
 *          System.out.println(e.getMessage());
 *          System.out.println(e.getInternalMessage());
 *      }
 *      ...
 *      ...
 *  </pre>
 *
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 * @see net.gos95.empire.configuration.core.PropertiesLoader
 */
public class SimpleConfiguration
extends PropertiesLoader
{
	// the class-version of this class
	private static final long serialVersionUID = 100L;


	/** 
	 * Contiene il puntamento al file dei messaggi di notifica.
	 * <br>
	 * Di default i messaggi di notifica restituiti dall'oggetto sono contenuti all'interno
	 * del file <code>notifications.properties</code>, e possono essere modificati
	 * o all'interno dello stesso file, oppure provvedendo ad un nuovo file.
	 */
	public static String NOTIFICATIONS_FILE = "notifications.properties";


	/**
	 * Costruisce un nuovo oggetto <code>PropertiesLoader</code> vuoto.
	 */
	public SimpleConfiguration()
	throws ConfigurationException
	{
		super(SimpleConfiguration.class, serialVersionUID, SimpleConfiguration.NOTIFICATIONS_FILE);
	}


	/**
	 * Costruisce un nuovo oggetto <code>PropertiesLoader</code> che si occupa del
	 * caricamento delle proprieta' dal file di configurazione specificato.
	 * 
	 * @param configurationFile file contenente le proprieta' di configurazione
	 * @exception ConfigurationException in caso di problemi di caricamento del file
	 */
	public SimpleConfiguration(String configurationFile)
	throws ConfigurationException
	{
		this();
		super.load(configurationFile);
	}


    /**
     * Restituisce il valore <code>String</code> della proprieta' specificata.<br>
     * Il valore della proprieta' viene trimmata degli eventuali spazi in testa o
     * in coda. Se il valore della proprieta' e' espresso tra doppi apici, il trim
     * ha effetto solo sugli eventuali spazi esterni ai doppi apici. 
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @return il valore stringa della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato 
     */
    public String getStringValue(String name)
    throws ConfigurationException
    {
   	    return getStringValue(name, false);
    }


    /**
     * Restituisce il valore <code>String</code> della proprieta' specificata.<br>
     * Il valore della proprieta' viene trimmata degli eventuali spazi in testa o
     * in coda. Se il valore della proprieta' e' espresso tra doppi apici, il trim
     * ha effetto solo sugli eventuali spazi esterni ai doppi apici. 
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return il valore stringa della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato 
     */
    public String getStringValue(String name, boolean isMandatory)
    throws ConfigurationException
    {
    	String value = super.getProperties().getProperty(name);

    	if (value != null)
    	{
    		try {
    			value = value.trim();
    			if (value.startsWith("\"") && value.trim().endsWith("\"")) value = value.substring(1, value.lastIndexOf("\""));
    			if (value.length() == 0) value = null;
    		} catch (Exception e) {
    			throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());    			
    		}
    	}

    	if (isMandatory && value == null)
   	    {
    		throw new ConfigurationException(notifications.MISSING_PROPERTY_VALUE_MESSAGE + ": " + name);
   	    }

   	    return value;
    }


    /**
     * Restituisce un array di oggetti <code>String</code> contenenti
     * i valori della proprieta' specificata.<br>
     * Gli elementi dell'array vengono trimmati degli eventuali spazi in testa o
     * in coda. Se il valore di un elemento dell'array e' espresso tra doppi apici,
     * il trim ha effetto solo sugli eventuali spazi esterni ai doppi apici. 
     * 
     * @param name nome della proprieta' di cui ottenere i valori
     * @param separator il separatore di elenco dell'array
     * @return i valori string-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public String[] getStringValues(String name, String separator)
    throws ConfigurationException
    {
    	return getStringValues(name, separator, false);
    }


    /**
     * Restituisce un array di oggetti <code>String</code> contenenti
     * i valori della proprieta' specificata.<br>
     * Gli elementi dell'array vengono trimmati degli eventuali spazi in testa o
     * in coda. Se il valore di un elemento dell'array e' espresso tra doppi apici,
     * il trim ha effetto solo sugli eventuali spazi esterni ai doppi apici. 
     * 
     * @param name nome della proprieta' di cui ottenere i valori
     * @param separator il separatore di elenco dell'array
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return i valori string-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public String[] getStringValues(String name, String separator, boolean isMandatory)
    throws ConfigurationException
    {
    	String value = super.getProperties().getProperty(name);
    	String[] values = null;

    	if (value != null)
    	{
    		try {
    			value = value.trim() + separator;
    			if (value.length() == 1)
    				value = null;
    			else
    				values = value.split(separator);
    		} catch (Exception e) {
    			throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());    			
    		}
    	}

    	if (values != null)
    	{
    		for (int ii=0; ii < values.length; ii++)
    		{
    			if (values[ii] != null)
    			{
    				try {
    					value = values[ii].trim();
    					if (value.startsWith("\"") && value.trim().endsWith("\"")) value = value.substring(1, value.lastIndexOf("\""));
    					if (value.length() == 0) value = null;
    					values[ii] = value;
    				} catch (Exception e) {
    					throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());    			
    				}
    			}
    		}
    	}

    	if (isMandatory && (values == null || values.length == 0))
   	    {
    		throw new ConfigurationException(notifications.MISSING_PROPERTY_VALUE_MESSAGE + ": " + name);
   	    }

    	if (values!= null && values.length == 0)
    		return null;

   	    return values;
    }


    /**
     * Restituisce il valore <code>Integer</code> della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @return il valore integer della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Integer getIntegerValue(String name)
    throws ConfigurationException
    {
    	return getIntegerValue(name, false);
    }


    /**
     * Restituisce il valore <code>Integer</code> della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return il valore integer della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Integer getIntegerValue(String name, boolean isMandatory)
    throws ConfigurationException
    {
    	Integer value = null;

    	try {
    		String tmp = getStringValue(name, isMandatory);
    		if (tmp != null)
    	        value = new Integer(tmp.trim());
    	} catch(Exception e) {
    		throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());
    	}

   	    return value;
    }


    /**
     * Restituisce un array di oggetti <code>Integer</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @return i valori integer-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Integer[] getIntegerValues(String name, String separator)
    throws ConfigurationException
    {
    	return getIntegerValues(name, separator, false);
    }


    /**
     * Restituisce un array di oggetti <code>Integer</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return i valori integer-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Integer[] getIntegerValues(String name, String separator, boolean isMandatory)
    throws ConfigurationException
    {
    	String[]  svalues = getStringValues(name, separator, isMandatory);
    	Integer[] ivalues = null;

    	if (svalues != null)
    	{
    		try {
    			ivalues = new Integer[svalues.length];
    			for (int ii=0; ii < svalues.length; ii++)
    			{
    				if (svalues[ii] != null)
    					ivalues[ii] = new Integer(svalues[ii]);
    			}
    		} catch(Exception e) {
    			throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());
    		}
    	}

   	    return ivalues;
    }


    /**
     * Restituisce il valore <code>Boolean</code> della proprieta' specificata.<br>
     * Sono considerati <code>true</code> i valori: 1, on, true, enable; invece
     * <code>false</code> i valori opposti: 0, off, false, disable.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @return il valore boolean della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Boolean getBooleanValue(String name)
    throws ConfigurationException
    {
    	return getBooleanValue(name, false);
    }


    /**
     * Restituisce il valore <code>Boolean</code> della proprieta' specificata.<br>
     * Sono considerati <code>true</code> i valori: 1, on, true, enable; invece
     * <code>false</code> i valori opposti: 0, off, false, disable.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return il valore boolean della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Boolean getBooleanValue(String name, boolean isMandatory)
    throws ConfigurationException
    {
    	boolean value = false;
    	String tmp = getStringValue(name, isMandatory);

    	if (tmp != null)
    	{
    		if (tmp.equals("1")) value = true;
    		else if (tmp.equals("0")) value = false;
    		else if (tmp.equalsIgnoreCase("on")) value = true;
    		else if (tmp.equalsIgnoreCase("off")) value = false;
    		else if (tmp.equalsIgnoreCase("true")) value = true;
    		else if (tmp.equalsIgnoreCase("false")) value = false;
    		else if (tmp.equalsIgnoreCase("enable")) value = true;
    		else if (tmp.equalsIgnoreCase("disable")) value = false;
    		else throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name); 
    	}

    	return new Boolean(value);
    }


    /**
     * Restituisce un array di <code>boolean</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @return i valori Boolean-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Boolean[] getBooleanValues(String name, String separator)
    throws ConfigurationException
    {
    	return getBooleanValues(name, separator, false);
    }


    /**
     * Restituisce un array di <code>boolean</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return i valori Boolean-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Boolean[] getBooleanValues(String name, String separator, boolean isMandatory)
    throws ConfigurationException
    {
    	String[]  svalues = getStringValues(name, separator, isMandatory);
    	Boolean[] bvalues = null;
    	boolean bvalue = false;

    	if (svalues != null)
    	{
    		try {
    			bvalues = new Boolean[svalues.length];
    			for (int ii=0; ii < svalues.length; ii++)
    			{
    				if (svalues[ii] != null)
    				{
    					if (svalues[ii].equals("1")) bvalue = true;
    		    		else if (svalues[ii].equals("0")) bvalue = false;
    		    		else if (svalues[ii].equalsIgnoreCase("on")) bvalue = true;
    		    		else if (svalues[ii].equalsIgnoreCase("off")) bvalue = false;
    		    		else if (svalues[ii].equalsIgnoreCase("true")) bvalue = true;
    		    		else if (svalues[ii].equalsIgnoreCase("false")) bvalue = false;
    		    		else if (svalues[ii].equalsIgnoreCase("enable")) bvalue = true;
    		    		else if (svalues[ii].equalsIgnoreCase("disable")) bvalue = false;
    		    		else throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + svalues[ii]);
    					bvalues[ii] = new Boolean(bvalue);
    				}
    			}
    		} catch(Exception e) {
    			throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());
    		}
    	}

   	    return bvalues;
    }


    /**
     * Restituisce il valore <code>Double</code> della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @return il valore in doppia precisione della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Double getDoubleValue(String name)
    throws ConfigurationException
    {
    	return getDoubleValue(name, false);
    }


    /**
     * Restituisce il valore <code>Double</code> della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return il valore in doppia precisione della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Double getDoubleValue(String name, boolean isMandatory)
    throws ConfigurationException
    {
    	Double value = null;

    	try {
    		String tmp = getStringValue(name, isMandatory);
    		if (tmp != null)
    	        value = new Double(tmp.trim());
    	} catch(Exception e) {
    		throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());
    	}

   	    return value;
    }


    /**
     * Restituisce un array di oggetti <code>Double</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @return i valori Double-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetto il tipo di dato
     */
    public Double[] getDoubleValues(String name, String separator)
    throws ConfigurationException
    {
    	return getDoubleValues(name, separator, false);
    }


    /**
     * Restituisce un array di oggetti <code>Double</code> contenenti i valori
     * della proprieta' specificata.
     * 
     * @param name nome della proprieta' di cui ottenere il valore
     * @param separator il separatore di elenco dell'array
     * @param isMandatory indica se la proprieta' e' mandatoria o opzionale
     * @return i valori Double-array della proprieta'
     * @throws ConfigurationException in caso non sia rispetta l'obbligatorieta' o il tipo di dato
     */
    public Double[] getDoubleValues(String name, String separator, boolean isMandatory)
    throws ConfigurationException
    {
    	String[]  svalues = getStringValues(name, separator, isMandatory);
    	Double[] dvalues = null;

    	if (svalues != null)
    	{
    		try {
    			dvalues = new Double[svalues.length];
    			for (int ii=0; ii < svalues.length; ii++)
    			{
    				if (svalues[ii] != null)
    					dvalues[ii] = new Double(svalues[ii]);
    			}
    		} catch(Exception e) {
    			throw new ConfigurationException(notifications.INVALID_PROPERTY_VALUE_MESSAGE + ": " + name, e.toString());
    		}
    	}

   	    return dvalues;
    }
}
