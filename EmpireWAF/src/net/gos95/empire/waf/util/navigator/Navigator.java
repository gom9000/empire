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


package net.gos95.empire.waf.util.navigator;


import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


import net.gos95.empire.lang.EmpireObject;

//TODO WAF - TestCase net.gos95.empire.waf.util.navigator
/**
 * Manages all the user navigation routes.
 * TODO WAF - Navigator: docs.
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 01/02/2006
 * @see     net.gos95.empire.waf.util.navigator.NavigatorException
 * @see     net.gos95.empire.waf.util.navigator.Route
 */
public class Navigator
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

    /** http parameter key-name that identify route */
    public static final String PARAMETER = "route";

    /** http parameter key-name that identify a router step */
    public static final String STEP = "step";

    /** contains url of routes Dispatcher */
    public String DISPATCHER;

    // contains routes (code, Route)
    private Hashtable routes;

    // contains hierarchy pairs (code, masterCode)
    private Hashtable map;

    // contains code children (masterCode, Collection codes)
    private Hashtable list;

    // contains user navigation history
    private Vector history;

    // contains user navigation tree
//    private Vector tree;

    // contains route step value
    private String step;


    /**
     * Creates a new <code>Navigator</code> object
     * with the given routes dispatcher url.
     *
     * @param  dispatcher url of dispatcher that dispatch routes to
     * competent controller/resource url
     */
    public Navigator(String dispatcher)
    {
        super(Navigator.class, serialVersionUID);
        DISPATCHER = dispatcher;
        reset();
    }


    /**
     * Resets the Navigation properties.
     */
    public void reset()
    {
        routes = new Hashtable();
        map    = new Hashtable();
        list   = new Hashtable();

        history = new Vector();
//        tree    = new Vector();
    }

    /**
     * Adds a new route to the list.
     *
     * @param  r the route to add
     */
    public void addRoute(Route r)
    {
        Collection children;
        String masterCode = r.masterCode;

        if (routes.containsKey(r.code))        
        {
        	routes.put(r.code, r);
        } else {
        	routes.put(r.code, r);
        	if (masterCode == null) masterCode = "NULL";
        	map.put(r.code, masterCode);

        	if (list.containsKey(masterCode))
        		children = (Vector)list.get(masterCode);
        	else
        		children = new Vector();
        	children.add(r.code);
        	list.put(masterCode, children);
        }
    }


    /**
     * Returns the Route specified by given route code.
     *
     * @param  code the route code
     * @return the route object
     */
    public Route getRoute(String code)
    {
        return (Route)routes.get(code);
    }


    /**
     * Returns a Collection of route codes.
     *
     * @return the collection of route codes
     */
    public Collection getRouteCodes()
    {
        Collection codes = new Vector();

        for (Enumeration e = routes.keys(); e.hasMoreElements(); )
            codes.add((String)e.nextElement());

        return codes;
    }


    /**
     * Returns master code of given route code.<br>
     * If specified code has not master, then returns <code>null</code>.
     *
     * @param  code the route code
     * @return the master route code, or null
     */
    public String getRouteMasterCode(String code)
    {
        String masterCode = (String)map.get(code);

        if (masterCode.equals("NULL"))
            return (String)null;

        return masterCode;
    }


    /**
     * Returns top-master code of given route code.<br>
     * If specified code has not master, then returns <code>null</code>.
     *
     * @param  code route code
     * @return the top-master route code, or null
     */
    public String getRouteTopMasterCode(String code)
    {
        String masterCode = (String)map.get(code);
        String childCode  = masterCode;

        while (masterCode != "NULL")
        {
            childCode  = masterCode;
            masterCode = (String)map.get(masterCode);
        }

        if (childCode.equals("NULL"))
            return (String)null;

        return childCode;
    }


    /**
     * Returns a collection of routes codes that are direct-children of
     * given route code.<br>
     * If code is null then returns top-master route code collection.
     *
     * @param  code route code
     * @return the collection of direct-children codes
     */
    public Collection getRouteChildrenCodes(String code)
    {
        if (code == null) code = "NULL";

        return (Collection)list.get(code);
    }


    /**
     * Tests if specified code exists in navigator list.
     *
     * @param  code route code
     * @return the boolean result of test
     */
    public boolean containsRouteCode(String code)
    {
        return routes.containsKey(code);
    }


    /**
     * Sets current route by given route code
     * and forces update of history and navigation tree.
     *
     * @param code the route code
     * @throws NavigatorException
     */
    public void setCurrentRoute(String code)
    throws NavigatorException
    {
        try {
            Route route = getRoute(code);

            // update history
            history.add(route);

            // update navigation tree
            // TODO XNEW WAF - Navigation: implements tree.
        } catch(Exception e) {
            throw new NavigatorException(e.getMessage(), code);
        }
    }


    /**
     * Sets current route by givens code and step
     * and forces update of history and navigation tree.
     *
     * @param code the route code
     * @param step the route step
     * @throws NavigatorException
     */
    public void setCurrentRoute(String code, String step)
    throws NavigatorException
    {
        setCurrentRoute(code);
        this.step = step;
    }


    /**
     * Returns current route controller url.
     *
     * @return the current route controller url
     */
    public String getControllerUrl()
    {
        return ((Route)history.lastElement()).controllerUrl;
    }


    /**
     * Returns current route resource url.
     *
     * @return the current route resource url
     */
    public String getResourceUrl()
    {
        return ((Route)history.lastElement()).resourceUrl;
    }


    /**
     * Returns current route code.
     *
     * @return the current route code
     */
    public String getCode()
    {
        return ((Route)history.lastElement()).code;
    }


    /**
     * Returns current route id.
     *
     * @return the current route int id
     */
    public int getId()
    {
        return ((Route)history.lastElement()).id;
    }


    /**
     * Returns current route step value.
     *
     * @return the current route step
     */
    public String getStep()
    {
        return step;
    }


    /**
     * Returns current route object.
     *
     * @return the current route object
     */
    public Route getRoute()
    {
        return ((Route)history.lastElement());
    }


    /**
     * Returns n-last route code.
     *
     * @param  num the n-last number
     * @return the route code
     */
    public String getCode(int num)
    {
        int idx = history.size() - num;

        if ( (idx > 0) && (idx < history.size()) )
            return ((Route)history.elementAt(idx-1)).code;
        else
            return null;
    }


    /**
     * Returns last route code not equals with current route code.
     *
     * @return the route code
     */
    public String getBeforeLastCode() 
    {
        Route rc = (Route)history.elementAt(history.size()-1);
        for (int i=history.size()-2; i>=0; i--) {
           if (!((Route)history.elementAt(i)).code.equalsIgnoreCase(rc.code)) {
               return ((Route)history.elementAt(i)).code;
           }
        }
        return getCode();
    }


    /**
     * Returns n-last route code not equals with current route code.
     *
     * @param  num num the n-last number
     * @return the route code
     */
    public String getNLastCode(int num)
    {
        Route rc = (Route)history.elementAt(history.size()-1);
        int count = 0;
        for (int i= history.size()-2; i>=0  ; i-- ) {
           if (!((Route)history.elementAt(i)).code.equalsIgnoreCase(rc.code)) {
               rc = (Route)history.elementAt(i);
               count ++;
               if (count == num)
                 return ((Route)history.elementAt(i)).code;
           }
        }
        return getCode();
    }
}
