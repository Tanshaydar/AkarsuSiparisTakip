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
    
    Connection connection = null;
    
    public ArrayList<Siparis> fetchDB(){
        connection = JavaConnector.ConnectDB();
        ResultSet resultsetS = null;
        ResultSet resultsetF = null;
        ResultSet resultsetU = null;
        PreparedStatement statementS = null;
        PreparedStatement statementF = null;
        PreparedStatement statementU = null;

        String sqlSiparis = "SELECT * FROM siparis";
        String sqlFirma = "SELECT * FROM firma WHERE siparis_id=?";
        String sqlUrun = "SELECT * FROM urun WHERE siparis_id=?";
        
        
        ArrayList<Siparis> siparisListesi = new ArrayList<>();
        
        try {
            statementS = connection.prepareStatement( sqlSiparis);
            resultsetS = statementS.executeQuery();
            
            while( resultsetS.next()) {

                Firma firma = null;
                ArrayList<Urun> urunler = new ArrayList<>();
                /*System.out.println(resultsetS.getInt("siparis_id") + resultsetS.getString("durum") 
                        + resultsetS.getString("aciklama") + resultsetS.getDate("siparis_tarih") + resultsetS.getDate("bitis_tarih") + resultsetS.getDouble("toplam") );*/
                System.out.println(resultsetS.getInt("siparis_id"));
                try {
                    
                    statementF = connection.prepareStatement(sqlFirma);
                    statementF.setInt(1, resultsetS.getInt("siparis_id"));
                    resultsetF = statementF.executeQuery();
                    if( resultsetF.next()) {
                        //String firma_adi, String ilgili_adi, String mail, String tel, String gsm, String fax)
                        firma = new Firma( resultsetF.getString("firma_adi"), resultsetF.getString("ilgili_adi"), resultsetF.getString("mail"),
                                resultsetF.getString("tel"), resultsetF.getString("gsm"), resultsetF.getString("fax"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Firma bulunamadı!");
                    }
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    try {
                        resultsetF.close();
                        statementF.close();
                    } catch (Exception e) {
                    }
                }
                
                
                try {
                    
                    statementU = connection.prepareStatement(sqlUrun);
                    statementU.setInt(1, resultsetS.getInt("siparis_id"));
                    resultsetU = statementU.executeQuery();

                    while( resultsetU.next()) {
                        //Urun(String urunAdi, double urunFiyati, int urunAdedi, String urunDurumu, String urunAciklamasi)
                        urunler.add( new Urun( resultsetU.getString("urun_adi"), resultsetU.getDouble("urun_fiyati"), resultsetU.getInt("urun_adedi"), resultsetU.getString("urun_durumu"), resultsetU.getString("urun_aciklamasi")));
                        System.out.println();
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                } finally{
                    try {
                        resultsetU.close();
                        statementU.close();
                    } catch (Exception e) {
                    }
                }
                
               siparisListesi.add( 
                       /*
                        * (int siparis_id, String durum, String siparisi_isteyen, String siparisi_alan, String aciklama, Date siparis_tarih, Date siparis_istenen_tarih,
            Date bitis_tarih, Firma firma, ArrayList<Urun> urunler, double toplam)
                        */
                       new Siparis(resultsetS.getInt("siparis_id"), resultsetS.getString("durum"), resultsetS.getString("siparisi_isteyen"), resultsetS.getString("siparisi_alan"),
                       resultsetS.getString("aciklama"), resultsetS.getDate("siparis_tarih"), resultsetS.getDate("istenen_tarih"), resultsetS.getDate("bitis_tarih"),
                       firma, urunler, resultsetS.getDouble("toplam")));
               
            }
            System.out.println("Liste çekildi!");
            
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                resultsetS.close();
                statementS.close();
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
        /*
         * INSERT INTO `sql27141`.`siparis` (`siparis_id`, `siparisi_isteyen`, `siparisi_alan`, `durum`, `aciklama`, `siparis_tarih`, `istenen_tarih`, `bitis_tarih`, `toplam`) VALUES (NULL, 'Umutcan Batı', 'Mustafa Akarsu', 'Hazırlanıyor', NULL, '2013-10-05', '2013-10-31', NULL, '13548');
         */
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
        
        try {
            /*
             * INSERT INTO  `sql27141`.`firma` (
                `firma_adi` ,
                `ilgili_adi` ,
                `mail` ,
                `tel` ,
                `gsm` ,
                `fax` ,
                `siparis_id`
                )
                VALUES (
                'Neokodera',  'Tansel ALTINEL',  'alirsin@gmail.com',  '03125734686',  '05312831924',  '03122270409',  '259'
                );

             */
            statement = connection.prepareStatement( "INSERT INTO sql27141.firma "
                    + "( firma_adi, ilgili_adi, mail, tel, gsm, fax, siparis_id) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, siparis.getFirma().getFirma_adi());
            statement.setString(2, siparis.getFirma().getIlgili_adi());
            statement.setString(3, siparis.getFirma().getMail());
            statement.setString(4, siparis.getFirma().getTel());
            statement.setString(5, siparis.getFirma().getGsm());
            statement.setString(6, siparis.getFirma().getFax());
            statement.setInt(7, siparis.getSiparis_id());
            
            statement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
        for( int i = 0; i < siparis.getUrunler().size(); i++){
            Urun urun = siparis.getUrunler().get(i);
            try {

                /*
                 * INSERT INTO  `sql27141`.`urun` (
                    `urun_adi` ,
                    `urun_fiyati` ,
                    `urun_adedi` ,
                    `urun_durumu` ,
                    `urun_aciklamasi` ,
                    `siparis_id`
                    )
                    VALUES (
                    'Sipariş Takip Programı',  '2500,00',  '1',  'Hazırlanıyor',  'İşbu program hazırlanmaktadır.',  '259'
                    );
                 */
                statement = connection.prepareStatement( "INSERT INTO sql27141.urun "
                        + "( urun_adi, urun_fiyati, urun_adedi, urun_durumu, urun_aciklamasi, siparis_id) VALUES"
                        + "(?, ?, ?, ?, ?, ?)");
                statement.setString(1, urun.getUrunAdi());
                statement.setDouble(2, urun.getUrunFiyati());
                statement.setInt(3, urun.getUrunAdedi());
                statement.setInt(4, urun.getUrunDurumu());
                statement.setString(5, urun.getUrunAciklamasi());
                statement.setInt(6, siparis.getSiparis_id());

                statement.executeUpdate();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    statement.close();
                } catch (Exception e) {
                }
            }
        }
 
    }
}
