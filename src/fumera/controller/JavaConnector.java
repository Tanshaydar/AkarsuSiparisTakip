/*
 * �Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions, and the following disclaimer. 
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions, and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * 3. The name of the author may not be used to endorse or promote products 
 * derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package fumera.controller;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tansel
 */
public class JavaConnector {
    /*
    private static final String DBaddr = "sql2.freesqldatabase.com";
    private static final String DBname = "sql27141";
    private static final String DBuser = "sql27141";
    private static final String DBpass = "jS1*rN2%25";
    */

    private static final String DBaddr = "193.140.224.43:3306";
    private static final String DBname = "siparis_utf";
    private static final String DBuser = "siparis";
    private static final String DBpass = "vDAyMJbEUFsxzb5r";


    public static Connection ConnectDB(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            /*Connection connection = DriverManager.getConnection("jdbc:mysql://" + Settings.getDBaddress() + "/" + Settings.getDBname() 
            + "?useUnicode=true&characterEncoding=utf8", Settings.getDBuser(), Settings.getDBpassword());*/
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + DBaddr + "/" + DBname + "?useUnicode=true&characterEncoding=utf8",DBuser, DBpass);
            return connection;
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e, "Sunucu Bağlantısı", JOptionPane.WARNING_MESSAGE, null);
            return null;
        }
    }

    public static String DBname(){
        return DBname;
    }
}
