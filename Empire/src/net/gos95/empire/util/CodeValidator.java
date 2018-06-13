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

// TODO XNEW Empire - CodeValidator: implements dictionary facilities.
/**
 * The class <code>CodeValidator</code> implements a test of
 * string codes to validate their similar with a set of defined rules.
 * <br>
 * An example:<br>
 * <PRE>
 *      CodeValidator cv = new CodeValidator();
 *      cv.setMinAlphaChars(3);
 *      cv.setMaxAlphaChars(5);
 *      cv.setMinDigitChars(3);
 *      cv.setMaxDigitChars(5);
 *      cv.setSpecialSet(".;$!-");
 *      cv.setCharSet(cv.ALPHA_SET_FLAG+cv.DIGIT_SET_FLAG+cv.SPECIAL_SET_FLAG);
 *      cv.setMaxNearbyEqualChars(3);
 *      System.out.println(cv.validate(codeToValidate));
 * </PRE>
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 07/12/2007
 * @see     net.gos95.empire.lang.EmpireObject
 */
public class CodeValidator
extends EmpireObject
{
	/* the class-version of this class */
	private static final long serialVersionUID = 100L;

	// default char sets
    private final String DEF_ALPHA_SET    = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public  final int    ALPHA_SET_FLAG   = 1;
    private final String DEF_DIGIT_SET    = "0123456789";
    public  final int    DIGIT_SET_FLAG   = 2;
    private final String DEF_SPECIAL_SET  = ".,;:!?-=_*+£$%&()[]{}@#°§^";
    public  final int    SPECIAL_SET_FLAG = 4;

    // user char sets
    private String alphaSet;
    private String digitSet;
    private String specialSet;
    private int charSet; // chars set allowed

    // dictionaries (forbidden & allowed)
    public  final int   STREAM_TYPE = 1;
    public  final int   STRING_TYPE = 2;
    private boolean     forbiddenDictionaryEnable;
    private boolean     allowedDictionaryEnable;
    private int         forbiddenDictionaryType;
    private int         allowedDictionaryType;
//    private InputStream forbiddenDictionaryIS;
//    private InputStream allowedDictionaryIS;
//    private char        separator;
    private String[]    forbiddenDictionary;
    private String[]    allowedDictionary;

    private int minAlphaChars;        // min alpha chars per code
    private int maxAlphaChars;        // max alpha chars per code
    private int minDigitChars;        // min digit chars per code
    private int maxDigitChars;        // max digit chars per code
    private int minSpecialChars;      // min special chars per code
    private int maxSpecialChars;      // max special chars per code

    private int maxNearbyEqualChars;  // max nearby equal chars per code
    private int minLength;            // min chars per code
    private int maxLength;            // max chars per code

    // semantic error codes
    public final int FORBIDDEN_DICTIONARY_ERROR  = 2;  // 0010
    public final int ALLOWED_DICTIONARY_ERROR    = 3;  // 0011
    // length error codes
    public final int TOO_MANY_CHARS              = 4;  // 0100
    public final int FEW_CHARS                   = 5;  // 0101
    // syntactic error codes
    public final int TOO_MANY_ALPHA_CHARS        = 8;  // 1000
    public final int FEW_ALPHA_CHARS             = 9;  // 1001
    public final int TOO_MANY_DIGIT_CHARS        = 10; // 1010
    public final int FEW_DIGIT_CHARS             = 11; // 1011
    public final int TOO_MANY_SPECIAL_CHARS      = 12; // 1100
    public final int FEW_SPECIAL_CHARS           = 13; // 1101
    public final int NOT_ALLOWED_CHARS           = 14; // 1110
    public final int TOO_MANY_NEARBY_EQUAL_CHARS = 15; // 1111
    // no error
    public final int VALID_CODE                  = 0;  // 0000


    /**
     * Creates a new <code>CodeValidator</code> object
     * with default setting roules.
     */
    public CodeValidator()
    {
    	super(CodeValidator.class, serialVersionUID);

    	alphaSet   = DEF_ALPHA_SET;
        digitSet   = DEF_DIGIT_SET;
        specialSet = DEF_SPECIAL_SET;

        charSet = ALPHA_SET_FLAG + DIGIT_SET_FLAG + SPECIAL_SET_FLAG;

        minLength           = 8;
        maxLength           = 8;

        minAlphaChars       = 0;
        maxAlphaChars       = 8;
        minDigitChars       = 0;
        maxDigitChars       = 8;
        minSpecialChars     = 0;
        maxSpecialChars     = 8;
        maxNearbyEqualChars = 8;

        forbiddenDictionaryEnable = false;
        allowedDictionaryEnable   = false;
    }


    /**
     * Sets the alphabetic char set.<br>
     * Default alphabetic char set is:<br>
     * <code>abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ</code>
     *
     * @param  alphaSet  alphabetic char set as list of letters
     */
    public void setAlphaSet(String alphaSet)
    {
        this.alphaSet = alphaSet;
    }


    /**
     * Sets the digit char set.</br>
     * Default digit char set is:<br>
     * <code>0123456789</code>
     *
     * @param  digitSet digit char set as list of digits
     */
    public void setDigitSet(String digitSet)
    {
        this.digitSet = digitSet;
    }


    /**
     * Sets the special char set.</br>
     * Default special char set is:<br>
     * <code>.,;:!?-=_*+£$%&()[]{}@#°§^</code>
     *
     * @param  specialSet  special char set as list of special chars
     */
    public void setSpecialSet(String specialSet)
    {
        this.specialSet = specialSet;
    }


    /**
     * Sets the char set.
     *
     * @param  charSet  char set as list of chars
     */
    public void setCharSet(int charSet)
    {
        this.charSet = charSet;
    }


    /**
     * Sets the min length of code.
     *
     * @param  minLength  min lenght of code
     */
    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }


    /**
     * Sets the max length of code.
     *
     * @param  maxLength  lmax length of code
     */
    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }


    /**
     * Sets max nearby equal chars number.
     *
     * @param  maxNearbyEqualChars  number of max nearby equal char
     */
    public void setMaxNearbyEqualChars(int maxNearbyEqualChars)
    {
        this.maxNearbyEqualChars = maxNearbyEqualChars;
    }


    /**
     * Sets min alphabetic chars number.
     *
     * @param  minAlphaChars  min number of alphabetic chars
     */
    public void setMinAlphaChars(int minAlphaChars)
    {
        this.minAlphaChars = minAlphaChars;
    }


    /**
     * Sets max alphabetic chars number.
     *
     * @param  maxAlphaChars  max number of alphabetic chars
     */
    public void setMaxAlphaChars(int maxAlphaChars)
    {
        this.maxAlphaChars = maxAlphaChars;
    }


    /**
     * Sets min digit chars number.
     *
     * @param  minDigitChars  min number of digit chars
     */
    public void setMinDigitChars(int minDigitChars)
    {
        this.minDigitChars = minDigitChars;
    }


    /**
     * Sets max digit chars number.
     *
     * @param  maxDigitChars  max number of digit chars
     */
    public void setMaxDigitChars(int maxDigitChars)
    {
        this.maxDigitChars = maxDigitChars;
    }


    /**
     * Sets min special chars number.
     *
     * @param  minSpecialChars  min number of special chars
     */
    public void setMinSpecialChars(int minSpecialChars)
    {
        this.minSpecialChars = minSpecialChars;
    }


    /**
     * Sets max special chars number.
     *
     * @param  maxSpecialChars  max number of special chars
     */
    public void setMaxSpecialChars(int maxSpecialChars)
    {
        this.maxSpecialChars = maxSpecialChars;
    }


    /**
     * Sets forbidden dictionary.
     *
     * @param  forbiddenDictionary  array of strings
     */
    public void setForbiddenDictionary(String[] forbiddenDictionary)
    {
        this.forbiddenDictionary       = forbiddenDictionary;
        this.forbiddenDictionaryEnable = true;
        this.forbiddenDictionaryType   = STRING_TYPE;
    }


    /**
     * Sets allowed dictionary.
     *
     * @param  allowedDictionary  array of strings
     */
    public void setAllowedDictionary(String[] allowedDictionary)
    {
        this.allowedDictionary       = allowedDictionary;
        this.allowedDictionaryEnable = true;
        this.allowedDictionaryType   = STRING_TYPE;
    }


    /*
     * Sets forbidden dictionary.
     *
     * @param  is         InputStream
     * @param  separator  list separator
     */
/*    public void setForbiddenDictionary(InputStream is, char separator)
    {
        this.forbiddenDictionaryIS     = is;
        this.forbiddenDictionaryEnable = true;
        this.forbiddenDictionaryType   = STREAM_TYPE;
    }
*/

    /*
     * Sets allowed dictionary.
     *
     * @param  is         InputStream
     * @param  separator  list separator
     */
/*    public void setAllowedDictionary(InputStream is, char separator)
    {
        this.allowedDictionaryIS     = is;
        this.allowedDictionaryEnable = true;
        this.allowedDictionaryType   = STREAM_TYPE;
    }
*/

    private int getCharSetCharsOf(String code, String charSet)
    {
        int n = 0;

        for (int i=0; i<code.length(); i++)
            if (charSet.indexOf(code.charAt(i)) != -1) n++;

        return (n);
    }


    private int getNearbyEqualChars(String code)
    {
        int count = 1;
        int maxcount = 1;
        int old_ch, new_ch;

        old_ch = new_ch = code.charAt(0);
        for (int i=1; i<code.length(); i++)
        {
            new_ch = code.charAt(i);
            if (new_ch == old_ch) count++;
            else count = 1;
            old_ch = new_ch;
            if (count > maxcount) maxcount = count;
        }

        return maxcount;
    }


    /**
     * Validates the input code by means of defined rules.
     *
     * @param  code      string code to validate
     * @return           validation result
     */
    public int validate(String code)
    {
        int length       = code.length();
        int alphaChars   = 0;
        int digitChars   = 0;
        int specialChars = 0;

        // length ctrl
        if (length < minLength) return FEW_CHARS;
        if (length > maxLength) return TOO_MANY_CHARS;

        // semantic ctrl
        if (forbiddenDictionaryEnable) {
            if (forbiddenDictionaryType == STREAM_TYPE) {
            	; // todo
            } else if (forbiddenDictionaryType == STRING_TYPE) {
                for (int i=0; i<forbiddenDictionary.length; i++)
                    if (code.equals(forbiddenDictionary[i])) return (FORBIDDEN_DICTIONARY_ERROR);
            }
        }
        if (allowedDictionaryEnable) {
            if (allowedDictionaryType == STREAM_TYPE) {
                ; // todo
            } else if (allowedDictionaryType == STRING_TYPE) {
                int n = 0;
                for (int i=0; i<allowedDictionary.length; i++)
                    if (code.equals(allowedDictionary[i])) n++;

                if (n == 0) return (ALLOWED_DICTIONARY_ERROR);
            }
        }

        // syntactic ctrl
        if ((charSet & ALPHA_SET_FLAG) == ALPHA_SET_FLAG) alphaChars = getCharSetCharsOf(code, alphaSet);
        if ((charSet & DIGIT_SET_FLAG) == DIGIT_SET_FLAG) digitChars = getCharSetCharsOf(code, digitSet);
        if ((charSet & SPECIAL_SET_FLAG) == SPECIAL_SET_FLAG) specialChars = getCharSetCharsOf(code, specialSet);

        if (alphaChars + digitChars + specialChars != length) return (NOT_ALLOWED_CHARS);
        if (alphaChars < minAlphaChars) return (FEW_ALPHA_CHARS);
        if (alphaChars > maxAlphaChars) return (TOO_MANY_ALPHA_CHARS);
        if (digitChars < minDigitChars) return (FEW_DIGIT_CHARS);
        if (digitChars > maxDigitChars) return (TOO_MANY_DIGIT_CHARS);
        if (specialChars < minSpecialChars) return (FEW_SPECIAL_CHARS);
        if (specialChars > maxSpecialChars) return (TOO_MANY_SPECIAL_CHARS);

        if (getNearbyEqualChars(code) > maxNearbyEqualChars) return (TOO_MANY_NEARBY_EQUAL_CHARS);

        return (VALID_CODE);
    }
}
