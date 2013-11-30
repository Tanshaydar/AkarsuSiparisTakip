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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
    
    public Settings(){
    }
    
    private static String encode( String str){
        BASE64Encoder encoder = new BASE64Encoder();
        str = new String( encoder.encodeBuffer( str.getBytes()));
        return str;
    }
    private static String decode( String str){
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            str = new String( decoder.decodeBuffer( str));
        } catch (Exception e) {
            FileLogger.hata( e.toString());
        }
        return str;
    }
    
    private static void checkFile(){

        File file = new File( settingsFile);
        if( !file.exists()){
            try {
                settings.setProperty( DBaddress, encode("sql2.freesqldatabase.com"));
                settings.setProperty( DBname, encode("sql27141"));
                settings.setProperty( DBuser, encode("sql27141"));
                settings.setProperty( DBpass, encode("jS1*rN2%25"));

                settings.store( new FileOutputStream( settingsFile), null);

            } catch (IOException e) {
                FileLogger.hata( e.toString());
            }
        }
    }
    
    public static void setSettings( String address, String name, String user, String pass){
        checkFile();
        try {
            settings.setProperty( DBaddress, encode(address));
            settings.setProperty( DBname, encode(name));
            settings.setProperty( DBuser, encode(user));
            settings.setProperty( DBpass, encode(pass));
            
            settings.store( new FileOutputStream( settingsFile), null);
            
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
    }
    
    public static String getDBaddress(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return decode(settings.getProperty(DBaddress));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBname(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return decode(settings.getProperty(DBname));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBuser(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return decode(settings.getProperty(DBuser));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static String getDBpassword(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return decode(settings.getProperty(DBpass));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
}
