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
                        urunler.add( new Urun( resultsetU.getString("urun_adi"), resultsetU.getDouble("urun_fiyati"),resultsetU.getInt("urun_adedi"), resultsetU.getString("urun_durumu"), resultsetU.getString("urun_aciklamasi")));
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                }
                
               siparisListesi.add( 
                       new Siparis(resultsetS.getInt("siparis_id"), resultsetS.getString("durum"), resultsetS.getString("siparisi_isteyen"), resultsetS.getString("siparisi_alan"),
                       resultsetS.getString("aciklama"), resultsetS.getDate("siparis_tarih"), resultsetS.getDate("istenen_tarih"), resultsetS.getDate("bitis_tarih"),
                       firma, urunler, resultsetS.getDouble("toplam")));
               
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
    
    public int getNewID(){
        connection = JavaConnector.ConnectDB();

        int id = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            statement = connection.prepareStatement( "SELECT MAX( siparis_id ) AS id FROM siparis");
            resultSet = statement.executeQuery();
            if( resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (Exception e) {
            }
        }
        return (id+1);
    }
    
    public void InsertDB( Siparis siparis){
        connection = JavaConnector.ConnectDB();
        
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement( "INSERT INTO sql27141.siparis "
                    + "( siparis_id, siparisi_isteyen, siparisi_alan, durum, aciklama, siparis_tarih, istenen_tarih, bitis_tarih, toplam) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setNull(1, java.sql.Types.INTEGER);
            statement.setString(2, siparis.getSiparisi_isteyen());
            statement.setString(3, siparis.getSiparisi_alan());
            statement.setInt(4, 1);
            statement.setString(5, siparis.getAciklama());
            statement.setDate(6, new java.sql.Date(siparis.getSiparis_tarih().getYear(), siparis.getSiparis_tarih().getMonth(), siparis.getSiparis_tarih().getDay()));
            statement.setDate(7, new java.sql.Date(siparis.getSiparis_istenen_tarih().getYear(), siparis.getSiparis_istenen_tarih().getMonth(), siparis.getSiparis_istenen_tarih().getDay()));
            statement.setNull(8, java.sql.Types.DATE);
            statement.setDouble(9, siparis.getToplam());
            
            statement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
        /*
         * INSERT INTO `sql27141`.`siparis` (`siparis_id`, `siparisi_isteyen`, `siparisi_alan`, `durum`, `aciklama`, `siparis_tarih`, `istenen_tarih`, `bitis_tarih`, `toplam`) VALUES (NULL, 'Umutcan Batı', 'Mustafa Akarsu', 'Hazırlanıyor', NULL, '2013-10-05', '2013-10-31', NULL, '13548');
         */
    }
}
