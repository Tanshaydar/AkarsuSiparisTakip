/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fumera.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tansel
 */
public class FileLogger {
    
    private static FileWriter fileStream;
    private static BufferedWriter out;
    private static String log;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
    public static void hata( String hata){
        log = dateFormat.format( Calendar.getInstance().getTime()) + "\r\n" + hata + "\r\n";
        
        try {
            fileStream = new FileWriter("error.log", true);
            out = new BufferedWriter( fileStream);
            out.write( log);
            out.close();
        } catch (IOException e) {
            FileLogger.hata( e.toString());
            Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
