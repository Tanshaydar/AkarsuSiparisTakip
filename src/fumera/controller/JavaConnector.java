/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
