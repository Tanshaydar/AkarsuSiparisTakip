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

    Connection connection = null;
    /*
    private static final String DBaddr = "sql2.freesqldatabase.com";
    private static final String DBname = "sql27141";
    private static final String DBuser = "sql27141";
    private static final String DBpass = "jS1*rN2%25";*/
    
    private static final String DBaddr = "localhost";
    private static final String DBname = "siparis";
    private static final String DBuser = "root";
    private static final String DBpass = "";
    
    public static Connection ConnectDB(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
/*            Connection connection = DriverManager.getConnection("jdbc:mysql://sql2.freesqldatabase.com/" + DBname + "?useUnicode=true&characterEncoding=UTF-8",
                    DBuser, DBpass);*/
           Connection connection = DriverManager.getConnection("jdbc:mysql://" + DBaddr + "/" + DBname + "?user=" + DBuser + "&password=" + DBpass);
            //JOptionPane.showMessageDialog(null, "Bağlandı!", "Sunucu Bağlantısı", JOptionPane.INFORMATION_MESSAGE, null);
            return connection;
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e, "Sunucu Bağlantısı", JOptionPane.WARNING_MESSAGE, null);
            return null;
        }
    }
    
    public static String DBname(){
        return DBname;
    }
}
