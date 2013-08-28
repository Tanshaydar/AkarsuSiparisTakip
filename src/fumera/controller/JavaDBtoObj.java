/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.controller;

import fumera.model.Firma;
import fumera.model.Siparis;
import fumera.model.Urun;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tansel
 */
public class JavaDBtoObj {
    private ArrayList<Siparis> siparisListesi = new ArrayList<>();
    private Firma firma;
    private ArrayList<Urun> urunler = new ArrayList<>();
    
    Connection connection = null;
    ResultSet resultsetS = null;
    ResultSet resultsetF = null;
    ResultSet resultsetU = null;
    PreparedStatement statementS = null;
    PreparedStatement statementF = null;
    PreparedStatement statementU = null;
    
    private String sqlSiparis = "SELECT * FROM siparis"; //WHERE durum = Hazırlanıyor
    private String sqlFirma = "SELECT * FROM firma WHERE siparis_id=?";
    private String sqlUrun = "SELECT * FROM urun WHERE siparis_id=?";
    
    public ArrayList<Siparis> fetchDB(){
        connection = JavaConnector.ConnectDB();
        try {
            statementS = connection.prepareStatement( sqlSiparis);
            resultsetS = statementS.executeQuery();
            
            while( resultsetS.next()) {

                /*System.out.println(resultsetS.getInt("siparis_id") + resultsetS.getString("durum") 
                        + resultsetS.getString("aciklama") + resultsetS.getDate("siparis_tarih") + resultsetS.getDate("bitis_tarih") + resultsetS.getDouble("toplam") );*/
                System.out.println(resultsetS.getString("siparis_id"));
                try {
                    
                    statementF = connection.prepareStatement(sqlFirma);
                    statementF.setString(1, resultsetS.getString("siparis_id"));
                    resultsetF = statementF.executeQuery();
                    if( resultsetF.next()) {
                        firma = new Firma( resultsetF.getString("firma_adi"), resultsetF.getString("ilgili_adi"), resultsetF.getString("mail"),
                                resultsetF.getString("tel"), resultsetF.getString("gsm"), resultsetF.getString("fax"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Firma bulunamadı!");
                    }
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                
                
                try {
                    
                    statementU = connection.prepareStatement(sqlUrun);
                    statementU.setString(1, resultsetS.getString("siparis_id"));
                    resultsetU = statementU.executeQuery();
                    while( resultsetU.next()) {
                        urunler.add( new Urun( resultsetU.getString("urun_adi"), resultsetU.getDouble("urun_fiyati"), resultsetU.getString("urun_durumu"), resultsetU.getString("urun_aciklamasi")));
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                }
                
               siparisListesi.add( new Siparis(resultsetS.getInt("siparis_id"), resultsetS.getString("durum"), resultsetS.getString("aciklama"), resultsetS.getDate("siparis_tarih"), resultsetS.getDate("bitis_tarih"), firma, urunler, resultsetS.getDouble("toplam")));
            }
            System.out.println("Liste çekildi!");
            
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                resultsetF.close();
                resultsetS.close();
                resultsetU.close();
                statementF.close();
                statementS.close();
                statementU.close();
            } catch (Exception e) {
            }
        }
        
        return siparisListesi;
    }
}
