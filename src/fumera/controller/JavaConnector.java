/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.controller;

import java.awt.HeadlessException;
import java.net.URL;
import java.net.URLDecoder;
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
    private static final String DBname = "sql27141";
    private static final String DBuser = "sql27141";
    private static final String DBpass = "jS1*rN2%25";
    
    public static Connection ConnectDB(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql2.freesqldatabase.com/" 
                    + DBname + "?user=" + DBuser + "&password=" + DBpass);
            JOptionPane.showMessageDialog(null, "Bağlandı!", "Sunucu Bağlantısı", JOptionPane.INFORMATION_MESSAGE, null);
            return connection;
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e, "Sunucu Bağlantısı", JOptionPane.WARNING_MESSAGE, null);
            return null;
        }
    }
}
