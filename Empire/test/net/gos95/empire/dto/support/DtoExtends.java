/*
 * E  M  P  I  R  E  Library
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


package net.gos95.empire.dto.support;


import java.util.*;

import net.gos95.empire.dto.Dto;


// The Dto extends for Unit Test.
public class DtoExtends
extends Dto
{
	private static final long serialVersionUID = 9999L;
	private int privateIntAttribute1;
    private int privateIntAttribute2 = -12;

    private byte privateByteAttribute1;
    private byte privateByteAttribute2 = 'a';

    public char publicCharAttribute1 = 65;
    private char privateCharAttribute2 = 'e';

    private float privateFloatAttribute1;
    private float privateFloatAttribute2 = 12.58F;

    private boolean privateBool = false;
    private Boolean privateBoolean;

    private Object privateObject1;
    private Object privateObject2 = new Object();

    private Vector privateVector1;
    private Vector privateVector2 = new Vector();

    private String[] privateStringArray1;
    private String[] privateStringArray2 = new String[2];


    public DtoExtends(int ii)
    {
    	super(DtoExtends.class, serialVersionUID);
        privateVector2.add("elem1");
        privateVector2.add("elem2");
        privateStringArray2[0] = "elem1";
        privateStringArray2[1] = "elem2";
    }

    public DtoExtends()
    {
        privateVector2.add("elem1");
        privateVector2.add("elem2");
        privateStringArray2[0] = "elem1";
        privateStringArray2[1] = "elem2";
    }


	public void setPrivateIntAttribute1(int privateIntAttribute1) {
		this.privateIntAttribute1 = privateIntAttribute1;
	}
	public int getPrivateIntAttribute1() {
		return privateIntAttribute1;
	}
	public void setPrivateIntAttribute2(int privateIntAttribute2) {
		this.privateIntAttribute2 = privateIntAttribute2;
	}
	public int getPrivateIntAttribute2() {
		return privateIntAttribute2;
	}
	public void setPrivateByteAttribute1(byte privateByteAttribute1) {
		this.privateByteAttribute1 = privateByteAttribute1;
	}
	public byte getPrivateByteAttribute1() {
		return privateByteAttribute1;
	}
	public void setPrivateByteAttribute2(byte privateByteAttribute2) {
		this.privateByteAttribute2 = privateByteAttribute2;
	}
	public byte getPrivateByteAttribute2() {
		return privateByteAttribute2;
	}
	public void setPrivateCharAttribute2(char privateCharAttribute2) {
		this.privateCharAttribute2 = privateCharAttribute2;
	}
	public char getPrivateCharAttribute2() {
		return privateCharAttribute2;
	}
	public void setPrivateFloatAttribute1(float privateFloatAttribute1) {
		this.privateFloatAttribute1 = privateFloatAttribute1;
	}
	public float getPrivateFloatAttribute1() {
		return privateFloatAttribute1;
	}
	public void setPrivateFloatAttribute2(float privateFloatAttribute2) {
		this.privateFloatAttribute2 = privateFloatAttribute2;
	}
	public float getPrivateFloatAttribute2() {
		return privateFloatAttribute2;
	}
	public void setPrivateBool(boolean privateBool) {
		this.privateBool = privateBool;
	}
	public boolean isPrivateBool() {
		return privateBool;
	}
	public void setPrivateBoolean(Boolean privateBoolean) {
		this.privateBoolean = privateBoolean;
	}
	public Boolean getPrivateBoolean() {
		return privateBoolean;
	}
	public void setPrivateObject1(Object privateObject1) {
		this.privateObject1 = privateObject1;
	}
	public Object getPrivateObject1() {
		return privateObject1;
	}
	public void setPrivateObject2(Object privateObject2) {
		this.privateObject2 = privateObject2;
	}
	public Object getPrivateObject2() {
		return privateObject2;
	}
	public void setPrivateVector1(Vector privateVector1) {
		this.privateVector1 = privateVector1;
	}
	public Vector getPrivateVector1() {
		return privateVector1;
	}
	public void setPrivateStringArray1(String[] privateStringArray1) {
		this.privateStringArray1 = privateStringArray1;
	}
	public String[] getPrivateStringArray1() {
		return privateStringArray1;
	}
}
