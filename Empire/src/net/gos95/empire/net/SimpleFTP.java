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


package net.gos95.empire.net;


import java.io.*;
import java.net.*;
import java.util.*;

import net.gos95.empire.lang.EmpireObject;

// TODO Empire - SimpleFTP: TestCase.
// TODO XNEW Empire - SimpleFTP: store status for reconnect command.
/**
 * The class <code>SimpleFTP</code> implements a simple FTP client
 * with the follow available comands/macro.<br><pre>
 *      ABORt
 *      ascii
 *      bin
 *      connect
 *      CWD
 *      CDUP
 *      DELEte
 *      get
 *      LIST
 *      MKDir
 *      NOOP
 *      PWD
 *      put
 *      QUIT
 *      rename
 *      RMDir
 *      PASV
 *      SIZE
 *      STOR
 *      SYSTem
 *      TYPE
 * </pre>
 *
 * @author  Alessandro Fraschetti
 * @version 1.0, 28/05/2007
 */
public class SimpleFTP
extends EmpireObject
{
	private static final long serialVersionUID = 100L;
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private PrintStream out = System.out;
    private String response = "";
    private String pasv_ip = null;
    private int pasv_port = -1;

    private boolean SHOW_LOG = true;

    /* constants */
    private String CRLF  = System.getProperty("line.separator");
    private int INFO     = 0;
    private int COMMAND  = 1;
    private int RESPONSE = 2;

    /* messages */
    private String UNEXPECTED_RESPONSE = "SimpleFTP received unexpected response. ";
    private String NOT_CONNECTED       = "SimpleFTP is not connected.";
    private String ALREADY_CONNECTED   = "SimpleFTP is already connected. ";
    private String BAD_DATA_LINK       = "SimpleFTP received bad data link information. ";
    private String PASV_NOT_ALLOWED    = "SimpleFTP could not request passive mode. ";
    private String CMD_NOT_ALLOWED     = "SimpleFTP was not allowed to execute the command. ";


    /**
     * Creates a new empty <code>SimpleFTP</code>.
     */
    public SimpleFTP()
    {
    	super(SimpleFTP.class, serialVersionUID);
    }


    /**
     * Connect to FTP server.
     *
     * @param  host   the ftp server
     * @param  port   the ftp server listen port
     * @param  user   the username
     * @param  passwd the password
     * @exception IOException
     */
    public synchronized void connect(String host, int port, String user, String passwd)
    throws IOException
    {
        if (socket != null) throw new IOException(ALREADY_CONNECTED);

        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        log("Connecting to: " + host + ":" + port, INFO);

        response = readResponse();
        if (!response.startsWith("220 ")) throw new IOException(UNEXPECTED_RESPONSE + response);

        sendCommand("USER " + user);
        response = readResponse();
        if (!response.startsWith("331 ")) throw new IOException(UNEXPECTED_RESPONSE + response);

        sendCommand("PASS " + passwd);
        response = readResponse();
        if (!response.startsWith("230 ")) throw new IOException(UNEXPECTED_RESPONSE + response);
    }


    /**
     * Disconnect from FTP server.
     *
     * @exception IOException
     */
    public synchronized void quit()
    throws IOException
    {
        try {
            sendCommand("QUIT");
        } finally {
            log("Disconnected.", INFO);
            socket = null;
        }
    }


    /**
     * Returns the server OS name.
     *
     * @return the server OS name
     * @exception IOException
     */
    public synchronized String system()
    throws IOException
    {
        sendCommand("SYST");
        String os = null;
        response = readResponse();
        if (response.startsWith("215 ")) {
            int firstQuote = response.indexOf(' ');
            int secondQuote = response.indexOf(' ', firstQuote + 1);
            if (secondQuote > 0) {
                os = response.substring(firstQuote + 1, secondQuote);
            }
        }
        return os;
    }


    /**
     * Returns the server working directory.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized String pwd()
    throws IOException
    {
        sendCommand("PWD");
        String dir = null;
        response = readResponse();
        if (response.startsWith("257 ")) {
            int firstQuote = response.indexOf('\"');
            int secondQuote = response.indexOf('\"', firstQuote + 1);
            if (secondQuote > 0) {
                dir = response.substring(firstQuote + 1, secondQuote);
            }
        }
        return dir;
    }


    /**
     * Change current server working directory.
     *
     * @param dir the new working directory
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean cwd(String dir)
    throws IOException
    {
        sendCommand("CWD " + dir);
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Change up server working directory.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean cdup()
    throws IOException
    {
        sendCommand("CDUP");
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Make the specified server directory.
     *
     * @param dir directory to make
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean mkdir(String dir)
    throws IOException
    {
        sendCommand("MKD " + dir);
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Deletes specified server directory.
     *
     * @param dir directory to be deleted
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean rmdir(String dir)
    throws IOException
    {
        sendCommand("RMD " + dir);
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Returns the size of specified server file.
     *
     * @param filename the file to get size
     * @return the command result
     * @exception IOException
     */
    public synchronized int size(String filename)
    throws IOException
    {
        int size = -1;

        sendCommand("SIZE " + filename);
        response = readResponse();

        if (response.startsWith("213 "))
            try {
                size = Integer.parseInt(response.substring(4));
            } catch(Exception e) {
                throw new IOException(BAD_DATA_LINK + response);
            }

        return size;
    }


    /**
     * Delete specified server file.
     *
     * @param filename the file to be deleted
     * @return the command reslt
     * @exception IOException
     */
    public synchronized boolean delete(String filename)
    throws IOException
    {
        sendCommand("DELE " + filename);
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Rename specified server file.
     *
     * @param oldname the old file name
     * @param newname the new file name
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean rename(String oldname, String newname)
    throws IOException
    {
        sendCommand("RNFR " + oldname);
        response = readResponse();
        if (response.startsWith("550 ")) return false;
        sendCommand("RNTO " + newname);
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Sets transfer mode.
     *
     * @param type the transfer mode
     * @exception IOException
     */
    public synchronized boolean type(String type)
    throws IOException
    {
        sendCommand("TYPE " + type);
        response = readResponse();
        return (response.startsWith("200 "));
    }


    /**
     * Sets binary transfer mode.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean bin()
    throws IOException
    {
        return (type("I"));
    }


    /**
     * Sets ASCII transfer mode.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean ascii()
    throws IOException
    {
        return (type("A"));
    }


    /**
     * Send a non-operation to force server response.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean noop()
    throws IOException
    {
        sendCommand("NOOP");
        response = readResponse();
        return (response.startsWith("250 "));
    }


    /**
     * Aborts current transfer.
     *
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean abort()
    throws IOException
    {
        sendCommand("ABOR");
        response = readResponse();
        return (response.startsWith("226 "));
    }


    /**
     * Enable server passive mode.
     * 
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean pasv()
    throws IOException
    {
        sendCommand("PASV");
        response = readResponse();
        if (!response.startsWith("227 ")) throw new IOException(PASV_NOT_ALLOWED + response);

        pasv_ip = null;
        pasv_port = -1;
        int opening = response.indexOf('(');
        int closing = response.indexOf(')', opening + 1);
        if (closing > 0) {
            String dataLink = response.substring(opening + 1, closing);
            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
            try {
                pasv_ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken();
                pasv_port = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());
            }
            catch (Exception e) {
                throw new IOException(BAD_DATA_LINK + response);
            }
        }
        if (pasv_port != -1) return true;
        return false;
    }


    /**
     * Puts specified file on server.
     *
     * @param  file file to put on server
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean put(File file)
    throws IOException
    {
        String filename = file.getName();
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

        if (!pasv()) return false;

        sendCommand("STOR " + filename);
        Socket dataSocket = new Socket(pasv_ip, pasv_port);
        response = readResponse();
        if (!response.startsWith("150 ")) throw new IOException(CMD_NOT_ALLOWED + response);

        BufferedOutputStream output = new BufferedOutputStream(dataSocket.getOutputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.flush();
        output.close();
        input.close();

        response = readResponse();
        return response.startsWith("226 ");
    }


    /**
     * Gets the specified filename from server.
     *
     * @param  filename file to get from server
     * @param  target   local target file
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean get(String filename, File target)
    throws IOException
    {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(target));

        if (!pasv()) return false;

        sendCommand("RETR " + filename);
        Socket dataSocket = new Socket(pasv_ip, pasv_port);
        response = readResponse();
        if (!response.startsWith("150 ")) throw new IOException(CMD_NOT_ALLOWED + response);

        BufferedInputStream input = new BufferedInputStream(dataSocket.getInputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.flush();
        output.close();
        input.close();

        response = readResponse();
        return response.startsWith("226 ");
    }


    /**
     * Returns server specified directory files list.
     *
     * @param  filename directory to get files list
     * @param  printstream  stream to send data
     * @return the command result
     * @exception IOException
     */
    public synchronized boolean list(String filename, PrintStream printstream)
    throws IOException
    {
        if (filename == null) filename = "";

        if (!pasv()) return false;

        sendCommand("LIST " + filename);
        Socket dataSocket = new Socket(pasv_ip, pasv_port);
        response = readResponse();
        if (!response.startsWith("150 ")) throw new IOException(CMD_NOT_ALLOWED + response);

        BufferedInputStream input = new BufferedInputStream(dataSocket.getInputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = input.read(buffer)) != -1) {
            printstream.write(buffer, 0, bytesRead);
        }
        printstream.flush();
        input.close();

        response = readResponse();
        return response.startsWith("226 ");
    }


    /**
     * Send raw command line to server.
     *
     * @exception IOException
     */
    public String sendRaw(String raw)
    throws IOException
    {
        sendCommand(raw);
        log(raw, COMMAND);
        return response = readResponse();
    }


    /**
     * Enable or disable events log.
     *
     * @param show  boolean
     */
    public void showLog(boolean show)
    {
        SHOW_LOG = show;
    }


    /**
     * Sets the stream of the events log.
     *
     * @param printstream  stream of the log
     */
    public void setLog(PrintStream printstream)
    {
        out = printstream;
    }




    /* send command line to server */
    private void sendCommand(String cmd)
    throws IOException
    {
        if (socket == null) throw new IOException(NOT_CONNECTED);

        try {
            writer.write(cmd + CRLF);
            writer.flush();
            log(cmd, COMMAND);
        } catch (IOException e) {
            socket = null;
            throw e;
        }
    }


    /* read response line from server */
    private String readResponse()
    throws IOException
    {
        String line = reader.readLine();
        log(line, RESPONSE);
        return line;
    }


    /* write command result log */
    private void log(String str, int type)
    throws IOException
    {
        String logstr = "";

        if (SHOW_LOG) {
            if (type == COMMAND)       logstr = "COMMAND>   ";
            else if (type == RESPONSE) logstr = "RESPONSE>  ";
            else                       logstr = "INFO>      ";
            out.print(logstr + str + CRLF);
        }
    }
}
