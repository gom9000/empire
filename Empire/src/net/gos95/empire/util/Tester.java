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


package net.gos95.empire.util;

import net.gos95.empire.lang.EmpireObject;


/**
 * La classe <code>Tester</code> implementa un set di metodi static per
 * il test di tipologie di dati e rappresentazioni di dati.
 *
 * @author Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 */
public class Tester
extends EmpireObject
{
	// the class-version of this class
	private static final long serialVersionUID = 100L;


    /* private constructor */
    private Tester()
    {
    	super(Tester.class, serialVersionUID);
    }


    /**
     * Testa se la stringa specificata è un valore numerico intero valido.
     *
     * @param str la rappresentazione stringa del tipo int da testare
     * @return il risultato boolean del test
     */
    static public boolean isIntegerNumber(String str)
    {
        try {
            new Integer(str.toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }


    /**
     * Testa se la stringa specificata è un valore numerico decimale valido.
     *
     * @param str la rappresentazione stringa del tipo float da testare
     * @return il risultato boolean del test
     */
    static public boolean isDecimalNumber(String str)
    {
        try {
            new Float(str.toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }


    /**
     * Ritorna i caratteri numerici eventualmente contenuti
     * alla fine della stringa specificata.
     * 
     * @param str il carattere da testare
     * @return una stringa composta da caratteri numerici o null
     */
    static public String getTailDigits(String str)
    {
    	String digits = null;
    	int index = 0;

    	if (str == null || str.length() == 0) return null;
    	
	    for (index = str.length()-1; index >= 0; index--)
	    {
	    	char ch = str.charAt(index);
		    if (((int)ch< 48) || ((int)ch > 57)) break;
	    }

	    try {
            digits = str.substring(++index);
	    	digits = ""+Integer.parseInt(digits);
	    } catch (NumberFormatException e) {
	    	return null;
	    }

	    return digits;
    }


    /**
     * Testa se la stringa specificata è nulla, vuota
     * o costituita da soli spazi.
     * 
     * @param str la stringa da testare
     * @return il risultato boolean del test
     */
    static public boolean isNullOrSpace(String str)
    {
	    if (!isNullOrBlank(str))
	    	if (str.trim().length() > 0) return false;
	    return true;
    }


    /**
     * Testa se la stringa specificata è nulla o vuota.
     * 
     * @param str la stringa da testare
     * @return il risultato boolean del test
     */
    static public boolean isNullOrBlank(String str)
    {
	    if (str != null && str.length() > 0) return false;
	    return true;
    }


    /**
     * Testa se il carattere specificato è una lettera o uno spazio.
     * 
     * @param ch il carattere da testare
     * @return il risultato boolean del test
     */
    static public boolean isAlphaOrSpace(char ch)
    {
    	if (((int)ch >= 65) && ((int)ch <= 90)
    		|| ((int)ch >= 97) && ((int)ch <= 122)
    		|| ((int)ch == 32) || ((int)ch == 45)) return true;
	    return false;
    }
}
