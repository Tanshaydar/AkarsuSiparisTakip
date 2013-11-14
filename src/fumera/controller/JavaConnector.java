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
    /*
    private static final String DBaddr = "localhost";
    private static final String DBname = "siparis";
    private static final String DBuser = "root";
    private static final String DBpass = "";*/
    /*
    private static final String DBaddr = "localhost";
    private static final String DBname = "siparis_takip";
    private static final String DBuser = "root";
    private static final String DBpass = ""; */
    
//    private static final String DBaddr = "193.140.224.43";
    private static final String DBaddr = "192.168.0.111:3306";
    private static final String DBname = "siparis_takip";
    private static final String DBuser = "siparis";
    private static final String DBpass = "vDAyMJbEUFsxzb5r";
    /*
    private static Connection conn = null; //Bağlantı nesnemiz
    private static String url = "jdbc:mysql://localhost:3306/";//veritabanının adresi ve portu
    private static String dbName = "siparis"; //veritabanının ismi
    private static String properties= "?useUnicode=true&amp;characterEncoding=utf8"; //Türkçe karakter problemi yaşamamak için
    private static String driver = "com.mysql.jdbc.Driver";//MySQL-Java bağlantısını sağlayan JDBC sürücüsü
    private static String userName = "root"; //veritabanı için kullanıcı adı
    private static String password = ""; //kullanıcı şifresi*/
    
    public static Connection ConnectDB(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + DBaddr + "/" + DBname + "?user=" + DBuser + "&password=" + DBpass);
            //JOptionPane.showMessageDialog(null, "Bağlandı!", "Sunucu Bağlantısı", JOptionPane.INFORMATION_MESSAGE, null);
            return connection;
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e, "Sunucu Bağlantısı", JOptionPane.WARNING_MESSAGE, null);
            return null;
        }
    }
    
    /*
    public static Connection ConnectDB() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName + properties, userName, password);//bağlantı açılıyor
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return conn;

    }*/
    
    public static String DBname(){
        return DBname;
    }
}
