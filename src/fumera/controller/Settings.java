/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fumera.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Tansel
 */
public class Settings {
 
    private static final Properties settings = new Properties();
    private static final String settingsFile = "settings.fum";
    
    private static final String DBaddress = "DBaddress";
    private static final String DBname = "DBname";
    private static final String DBuser = "DBuser";
    private static final String DBpass = "DBpass";
    
    public Settings(){}
    
    private static void checkFile(){
        File file = new File( settingsFile);
        if( !file.exists()){
            try {
                settings.setProperty( DBaddress, "sql2.freesqldatabase.com");
                settings.setProperty( DBname, "sql27141");
                settings.setProperty( DBuser, "sql27141");
                settings.setProperty( DBpass, "jS1*rN2%25");

                settings.store( new FileOutputStream( settingsFile), null);

            } catch (IOException e) {
                FileLogger.hata( e.toString());
            }
        }
    }
    
    public static void setSettings( String address, String name, String user, String pass){
        checkFile();
        try {
            settings.setProperty( DBaddress, address);
            settings.setProperty( DBname, name);
            settings.setProperty( DBuser, user);
            settings.setProperty( DBpass, pass);
            
            settings.store( new FileOutputStream( settingsFile), null);
            
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
    }
    
    public static String getDBaddress(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return settings.getProperty(DBaddress);
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBname(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return settings.getProperty(DBname);
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBuser(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return settings.getProperty(DBuser);
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBpassword(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return settings.getProperty(DBpass);
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
}
