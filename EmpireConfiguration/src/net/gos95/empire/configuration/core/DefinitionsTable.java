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


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import net.gos95.empire.lang.EmpireObject;


/**
 * La classe <code>DefinitionsTable</code> si occupa di caricare in memoria
 * e rendere disponibili alla consultazione le definizioni delle proprietà,
 * utilizzate per verificare i relativi valori mediante il metodo <code>manage()</code>
 * della classe <code>ManagedConfiguration</code>.
 *
 * @author Alessandro Fraschetti
 * @version 1.0, 21/11/2009
 */
public class DefinitionsTable
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	private Hashtable definitions;


	/**
	 * Costruisce e popola un nuovo oggetto <code>DefinitionsTable</code>
	 * con le definizioni delle proprietà contenute nel file specificato.
	 * 
	 * @param defsfile file contenente le definizioni delle proprietà
	 */
	public DefinitionsTable(String defsfile)
	throws Exception
	{
		super(DefinitionsTable.class, serialVersionUID);

		FileInputStream fis = new FileInputStream(defsfile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		definitions = new Hashtable();
		String line, defvalue = "";
		String[] fields;

		while ((line = in.readLine()) != null)
		{
			if (line.length() > 0 && !line.trim().startsWith("#"))
			{
				fields = line.split(",");
				if (fields.length >= 4)
				{
					ManagedProperty property = new ManagedProperty();

					property.alias = fields[0];
					if (property.alias != null && property.alias.trim().length() == 0) property.alias = null;

					property.name = fields[1];
					if (property.name != null && property.name.trim().length() == 0) property.name = null;

					property.datatype = fields[2];
					if (property.datatype != null && property.datatype.trim().length() == 0) property.datatype = null;

					property.mandatory = (fields[3].equalsIgnoreCase("MANDATORY") ? true : false);

					defvalue = "";
					for (int ii=4; ii<fields.length; ii++) defvalue += fields[ii] + ",";
					if (defvalue.length()>0) defvalue = defvalue.substring(0, defvalue.length()-1);
					else defvalue = null;
					property.defaultValue = defvalue;

					definitions.put(property.alias, property);
				}
			}
		}
	}


	/**
	 * Ritorna la tavola contenente le definizioni delle proprietà di configurazione.
	 * 
	 * @return la tavola in formato hashtable
	 */
	public Hashtable getTable()
	{
		return definitions;
	}
}
