/*
 * Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
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
import java.util.Arrays;
import java.util.Properties;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Tansel
 */
public class Settings {
    //===============================================
    // SETTING PROPERT & FILE
    private static final Properties settings = new Properties();
    private static final String settingsFile = "settings.fum";
    
    
    //===============================================
    // DATABASE
    private static final String DBaddress = "DBaddress";
    private static final String DBname = "DBname";
    private static final String DBuser = "DBuser";
    private static final String DBpass = "DBpass";
    
    //===============================================
    // GUI
    
    // Table Order
    private static final int[] aktifColumns = new int[]{0,1,2,3,4,5};
    private static final int[] bitmisColumns = new int[]{0,1,2,3,4,5};
    private static final int[] teklifColumns = new int[]{0,1,2,3,4,5};
    private static final int[] silinmisColumns = new int[]{0,1,2,3,4,5};
    
    private static final int[] yeniUrunColumns = new int[]{0,1,2,3,4,5};
    private static final int[] eskiUrunColumns = new int[]{0,1,2,3,4,5};
    
    // Table widths
    private static final int[] aktifColumnsW = new int[]{20,20,20,20,20,20};
    private static final int[] bitmisColumnsW = new int[]{20,20,20,20,20,20};
    private static final int[] teklifColumnsW = new int[]{20,20,20,20,20,20};
    private static final int[] silinmisColumnsW = new int[]{20,20,20,20,20,20};
    
    private static final int[] yeniUrunColumnsW = new int[]{20,20,20,20,20,20};
    private static final int[] eskiUrunColumnsW = new int[]{20,20,20,20,20,20};
    
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
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return str;
    }
    
    private static void checkFile(){

        File file = new File( settingsFile);
        if( !file.exists()){
            try {
                settings.setProperty( DBaddress, encode("193.140.224.43:3306"));
                settings.setProperty( DBname, encode("siparis_utf"));
                settings.setProperty( DBuser, encode("siparis"));
                settings.setProperty( DBpass, encode("vDAyMJbEUFsxzb5r"));
                
                settings.setProperty( "aktifColumns", encode( Arrays.toString(aktifColumns)));
                settings.setProperty( "bitmisColumns", encode( Arrays.toString(bitmisColumns)));
                settings.setProperty( "teklifColumns", encode( Arrays.toString(teklifColumns)));
                settings.setProperty( "silinmisColumns", encode( Arrays.toString(silinmisColumns)));
                settings.setProperty( "yeniUrunColumns", encode( Arrays.toString(yeniUrunColumns)));
                settings.setProperty( "eskiUrunColumns", encode( Arrays.toString(eskiUrunColumns)));

                settings.setProperty( "aktifColumnsW", encode( Arrays.toString(aktifColumnsW)));
                settings.setProperty( "bitmisColumnsW", encode( Arrays.toString(bitmisColumnsW)));
                settings.setProperty( "teklifColumnsW", encode( Arrays.toString(teklifColumnsW)));
                settings.setProperty( "silinmisColumnsW", encode( Arrays.toString(silinmisColumnsW)));
                settings.setProperty( "yeniUrunColumnsW", encode( Arrays.toString(yeniUrunColumnsW)));
                settings.setProperty( "eskiUrunColumnsW", encode( Arrays.toString(eskiUrunColumnsW)));
                
                settings.store( new FileOutputStream( settingsFile), null);

            } catch (IOException e) {
                FileLogger.hata( e.toString());
            }
        }
    }
    
    private static int[] convertToInt( String str){
        String[] items = str.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
                results[i] = Integer.parseInt(items[i]);
        }
        
        return results;
    }
    //==========================================================================
    public static void setDBSettings( String address, String name, String user, String pass){
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
    
    public static void setTableOrder( int[] aktif, int[] bitmis, int[] teklif, int[] silinmis, int[] yeni, int[] eski){
        checkFile();
        try {
            settings.setProperty( "aktifColumns", encode( Arrays.toString(aktif)));
            settings.setProperty( "bitmisColumns", encode( Arrays.toString(bitmis)));
            settings.setProperty( "teklifColumns", encode( Arrays.toString(teklif)));
            settings.setProperty( "silinmisColumns", encode( Arrays.toString(silinmis)));
            settings.setProperty( "yeniUrunColumns", encode( Arrays.toString(yeni)));
            settings.setProperty( "eskiUrunColumns", encode( Arrays.toString(eski)));
            
            settings.store( new FileOutputStream( settingsFile), null);
            
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
    }
    
    public static void setTableWidth( int[] aktif, int[] bitmis, int[] teklif, int[] silinmis, int[] yeni, int[] eski){
        checkFile();
        try {
            settings.setProperty( "aktifColumnsW", encode( Arrays.toString(aktif)));
            settings.setProperty( "bitmisColumnsW", encode( Arrays.toString(bitmis)));
            settings.setProperty( "teklifColumnsW", encode( Arrays.toString(teklif)));
            settings.setProperty( "silinmisColumnsW", encode( Arrays.toString(silinmis)));
            settings.setProperty( "yeniUrunColumnsW", encode( Arrays.toString(yeni)));
            settings.setProperty( "eskiUrunColumnsW", encode( Arrays.toString(eski)));
            
            settings.store( new FileOutputStream( settingsFile), null);
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
    }
    //==========================================================================
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
    //==========================================================================
    public static int[] getAktif(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "aktifColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getBitmis(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "bitmisColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getTeklif(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "teklifColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getSilinmis(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "silinmisColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getYeni(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "yeniUrunColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getEski(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "eskiUrunColumns")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    
    //==========================================================================
        public static int[] getAktifW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "aktifColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getBitmisW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "bitmisColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getTeklifW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "teklifColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getSilinmisW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "silinmisColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getYeniW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "yeniUrunColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
    public static int[] getEskiW(){
        checkFile();
        try {
            settings.load( new FileInputStream( settingsFile));
            return convertToInt( decode( settings.getProperty( "eskiUrunColumnsW")));
        } catch (IOException e) {
            FileLogger.hata( e.toString());
        }
        return null;
    }
}
