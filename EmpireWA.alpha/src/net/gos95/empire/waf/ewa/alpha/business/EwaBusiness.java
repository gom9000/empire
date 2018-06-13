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


package net.gos95.empire.waf.ewa.alpha.business;

import java.util.ArrayList;
import java.util.List;

import net.gos95.empire.util.Encoder;
import net.gos95.empire.waf.util.messages.Message;


/*
 * The EWA.alpha business class.
 */
public class EwaBusiness
{
	static int ii=0;


	public static List loadMessages()
    throws BusinessException
    {
		List list = null;

		try {
			list = new ArrayList();
			list.add(new Message("LOGIN_ERROR", "Invalid username or password", Message.ERROR));
			list.add(new Message("LOGIN_OK", "Login ok, go to home page", Message.INFORMATION));
			list.add(new Message("GENERIC_ERROR", "A generic error occurred", Message.ERROR));
			list.add(new Message("BUSINESS_ERROR", "A business error occurred", Message.ERROR));
			list.add(new Message("F1_OK", "Function One Done!", Message.INFORMATION));
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return list;
	}


	public static boolean login(String username, String password)
    throws BusinessException
    {
		boolean result = false;
		String testLogin = "test";
		String testMD5Password = "09ffffff8f6bffffffcd4621ffffffd373ffffffcaffffffde4effffff832627ffffffb4fffffff6";
		
		try {

			if (testLogin.equals(username) && testMD5Password.equals(Encoder.hexcrypt(password, Encoder.MD5)))
			{
				// login ok
				result = true;
			} else {
				// login error
			}

		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return result;
	}


	public static void functionOne()
    throws BusinessException
    {
		if (EwaBusiness.ii++ % 2 == 0)
		    throw new BusinessException("error on executing function one! (ii==" + (ii-1) + ")");
	}
}
