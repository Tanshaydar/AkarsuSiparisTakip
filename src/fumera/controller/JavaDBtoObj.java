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

import fumera.model.Firma;
import fumera.model.Siparis;
import fumera.model.Urun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tansel
 */
public class JavaDBtoObj {

    public static ArrayList<Siparis> getSiparislerFromDB(){
        Connection connection = JavaConnector.ConnectDB();
        ResultSet resultsetSiparis = null;
        ResultSet resultsetUrun = null;
        PreparedStatement statementSiparis = null;
        PreparedStatement statementUrun = null;

        String sqlSiparisAndFirma = "SELECT * FROM siparis LEFT JOIN firma ON siparis.siparis_id = firma.siparis_id";
        String sqlUrun = "SELECT * FROM urun WHERE siparis_id=?";
        
        ArrayList<Siparis> siparisListesi = new ArrayList<>();

        ///////
        // SIPARISLER
        //////
        try{
            statementSiparis = connection.prepareStatement( sqlSiparisAndFirma);
            resultsetSiparis = statementSiparis.executeQuery();
            
            while (resultsetSiparis.next()) {
                siparisListesi.add( new Siparis( resultsetSiparis.getInt("siparis_id"), resultsetSiparis.getString("durum"), resultsetSiparis.getString("siparisi_isteyen"),
                        resultsetSiparis.getString("siparisi_alan"), resultsetSiparis.getString("aciklama"), resultsetSiparis.getDate("siparis_tarih"),
                        resultsetSiparis.getDate("istenen_tarih"), resultsetSiparis.getDate("bitis_tarih"),
                        new Firma( resultsetSiparis.getString("firma_adi"), resultsetSiparis.getString("ilgili_adi"), resultsetSiparis.getString("mail"),
                        resultsetSiparis.getString("tel"), resultsetSiparis.getString("gsm"), resultsetSiparis.getString("fax")),
                        null, resultsetSiparis.getDouble("toplam")));
            }
            
        } catch (SQLException e){
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                resultsetSiparis.close();
                statementSiparis.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        ///////
        // URUNLER
        //////
        for( Siparis siparis : siparisListesi){
            ArrayList<Urun> urunListesi = new ArrayList<>();
            try {
                statementUrun = connection.prepareStatement(sqlUrun);
                statementUrun.setInt(1, siparis.getSiparis_id());
                resultsetUrun = statementUrun.executeQuery();
                
                while ( resultsetUrun.next()) {
                    urunListesi.add( new Urun( resultsetUrun.getString("urun_adi"), resultsetUrun.getDouble("urun_fiyati"),
                            resultsetUrun.getInt("urun_adedi"), resultsetUrun.getString("urun_durumu"), resultsetUrun.getString("urun_aciklamasi")));
                }
                
                siparis.setUrunler(urunListesi);
                
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    resultsetUrun.close();
                    statementUrun.close();
                } catch (SQLException e) {
                    FileLogger.hata( e.toString());
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        return siparisListesi;
    }
    

    public static void insertYeniSiparisToDB( Siparis siparis, int durum){
        Connection connection = JavaConnector.ConnectDB();
        ResultSet resultsetLastId = null;
        PreparedStatement statement = null;
        int lastID = 0;
        
        try {

            statement = connection.prepareStatement( "INSERT INTO " + JavaConnector.DBname() + ".siparis "
                    + "( siparis_id, siparisi_isteyen, siparisi_alan, durum, aciklama, siparis_tarih, istenen_tarih, bitis_tarih, toplam) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setNull(1, java.sql.Types.INTEGER);
            statement.setString(2, siparis.getSiparisi_isteyen());
            statement.setString(3, siparis.getSiparisi_alan());
            if( durum == 0)
                statement.setInt(4, 1);     // Hazırlanıyor
            else 
                statement.setInt(4, 4);     // Teklif
            statement.setString(5, siparis.getAciklama());
            statement.setDate(6, new java.sql.Date(siparis.getSiparis_tarih().getYear(), siparis.getSiparis_tarih().getMonth(), siparis.getSiparis_tarih().getDay()));
            statement.setDate(7, new java.sql.Date(siparis.getSiparis_istenen_tarih().getYear(),
                    siparis.getSiparis_istenen_tarih().getMonth(), siparis.getSiparis_istenen_tarih().getDay()));
            statement.setNull(8, java.sql.Types.DATE);
            statement.setDouble(9, siparis.getToplam());
            
            statement.executeUpdate();
            resultsetLastId = statement.getGeneratedKeys();
            if( resultsetLastId.next()) {
                lastID = resultsetLastId.getInt( 1);
                resultsetLastId.close();
            }
            else{
                JOptionPane.showMessageDialog(null, "Veritabanına Kaydedilemedi!", "Hata!", JOptionPane.WARNING_MESSAGE, null);
            }
            
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
        
        try {

            statement = connection.prepareStatement( "INSERT INTO " + JavaConnector.DBname() + ".firma "
                    + "( firma_adi, ilgili_adi, mail, tel, gsm, fax, siparis_id) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, siparis.getFirma().getFirma_adi());
            statement.setString(2, siparis.getFirma().getIlgili_adi());
            statement.setString(3, siparis.getFirma().getMail());
            statement.setString(4, siparis.getFirma().getTel());
            statement.setString(5, siparis.getFirma().getGsm());
            statement.setString(6, siparis.getFirma().getFax());
            statement.setInt(7, lastID);
            
            statement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
        for( int i = 0; i < siparis.getUrunler().size(); i++){
            Urun urun = siparis.getUrunler().get(i);
            try {
                statement = connection.prepareStatement( "INSERT INTO " + JavaConnector.DBname() + ".urun "
                        + "( urun_adi, urun_fiyati, urun_adedi, urun_durumu, urun_aciklamasi, siparis_id) VALUES"
                        + "(?, ?, ?, ?, ?, ?)");
                statement.setString(1, urun.getUrunAdi());
                statement.setDouble(2, urun.getUrunFiyati());
                statement.setInt(3, urun.getUrunAdedi());
                statement.setInt(4, urun.getUrunDurumu());
                statement.setString(5, urun.getUrunAciklamasi());
                statement.setInt(6, lastID);

                statement.executeUpdate();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    FileLogger.hata( e.toString());
                }
            }
        }
 
    }
    
    public static void updateSiparisToDB( Siparis siparis){
        Connection connection = JavaConnector.ConnectDB();
        
        PreparedStatement statement = null;
        
        try {
        /*
            UPDATE  `siparis`.`siparis` SET  `siparisi_isteyen` =  'İsmail DALAMAZ' WHERE  `siparis`.`siparis_id` =271;
         *  INSERT INTO `sql27141`.`siparis` (`siparis_id`, `siparisi_isteyen`, `siparisi_alan`, `durum`, `aciklama`, `siparis_tarih`, `istenen_tarih`, 
            `bitis_tarih`, `toplam`) VALUES (NULL, 'Umutcan Batı', 'Mustafa Akarsu', 'Hazırlanıyor', NULL, '2013-10-05', '2013-10-31', NULL, '13548');
         */
            statement = connection.prepareStatement( "UPDATE " + JavaConnector.DBname() + ".siparis SET siparisi_isteyen=?, siparisi_alan=?,"
                    + " durum=?, aciklama=?, siparis_tarih=?, istenen_tarih=?, bitis_tarih=?, toplam=? WHERE siparis.siparis_id=?;");
            statement.setString(1, siparis.getSiparisi_isteyen());
            statement.setString(2, siparis.getSiparisi_alan());
            statement.setInt(3, siparis.getDurumInt());
            statement.setString(4, siparis.getAciklama());
            statement.setDate(5, new java.sql.Date(siparis.getSiparis_tarih().getYear(), siparis.getSiparis_tarih().getMonth(), siparis.getSiparis_tarih().getDay()));
            statement.setDate(6, new java.sql.Date(siparis.getSiparis_istenen_tarih().getYear(), siparis.getSiparis_istenen_tarih().getMonth(), siparis.getSiparis_istenen_tarih().getDay()));
            if( siparis.getDurumInt() == 2)
                statement.setDate(7, new java.sql.Date( siparis.getBitis_tarih().getYear(), siparis.getBitis_tarih().getMonth(), siparis.getBitis_tarih().getDay()));
            else
                statement.setNull(7, java.sql.Types.DATE);
            statement.setDouble(8, siparis.getToplam());
            statement.setInt(9, siparis.getSiparis_id());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
        
        //Firma Duzenle
        try {
            /*
             */
            statement = connection.prepareStatement( "UPDATE " + JavaConnector.DBname() + ".firma SET firma_adi=?, ilgili_adi=?, mail=?, tel=?, gsm=?, fax=? WHERE firma.siparis_id=?;");
            statement.setString(1, siparis.getFirma().getFirma_adi());
            statement.setString(2, siparis.getFirma().getIlgili_adi());
            statement.setString(3, siparis.getFirma().getMail());
            statement.setString(4, siparis.getFirma().getTel());
            statement.setString(5, siparis.getFirma().getGsm());
            statement.setString(6, siparis.getFirma().getFax());
            statement.setInt(7, siparis.getSiparis_id());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
        
        // Var olan urunleri silip yerine yenilerini ekle
        // ÖNCE SİL
        try {
            statement = connection.prepareStatement( "DELETE FROM urun WHERE urun.siparis_id=?;");
            statement.setInt(1, siparis.getSiparis_id());           
            statement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
        
        // URUNLERI YERLESTIR
        for( int i = 0; i < siparis.getUrunler().size(); i++){
            Urun urun = siparis.getUrunler().get(i);
            try {
                statement = connection.prepareStatement( "INSERT INTO " + JavaConnector.DBname() + ".urun "
                        + "( urun_adi, urun_fiyati, urun_adedi, urun_durumu, urun_aciklamasi, siparis_id) VALUES"
                        + "(?, ?, ?, ?, ?, ?)");
                statement.setString(1, urun.getUrunAdi());
                statement.setDouble(2, urun.getUrunFiyati());
                statement.setInt(3, urun.getUrunAdedi());
                statement.setInt(4, urun.getUrunDurumu());
                //statement.setString(4, urun.getUrunDurumuStr());
                statement.setString(5, urun.getUrunAciklamasi());
                statement.setInt(6, siparis.getSiparis_id());

                statement.executeUpdate();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    FileLogger.hata( e.toString());
                }
            }
        }
    }
    
    public static void SiparisiSil( int siparisID){
        Connection connection = JavaConnector.ConnectDB();
        
        PreparedStatement statement = null;
        
        try {
        /*
         * UPDATE  `sql27141`.`siparis` SET  `durum` =  'Silindi' WHERE  `siparis`.`siparis_id` =259;
         */
            statement = connection.prepareStatement( "UPDATE siparis SET durum = 'Silindi' WHERE siparis.siparis_id=?;");
            statement.setInt(1, siparisID);
            
            statement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
    }
}
