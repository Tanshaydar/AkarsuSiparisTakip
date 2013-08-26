/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Tansel
 */
public class Siparis {
    
    private int siparis_id;
    private String durum;
    private String aciklama;
    private Date siparis_tarih;
    private Date bitis_tarih;
    private double toplam;
    
    private Firma firma;
    private ArrayList<Urun> urunler;

    public Siparis(int siparis_id, String durum, String aciklama, Date siparis_tarih, Date bitis_tarih, Firma firma, ArrayList<Urun> urunler, double toplam) {
        this.siparis_id = siparis_id;
        this.durum = durum;
        this.aciklama = aciklama;
        this.siparis_tarih = siparis_tarih;
        this.bitis_tarih = bitis_tarih;
        this.firma = firma;
        this.urunler = urunler;
        this.toplam = toplam;
    }

    public int getSiparis_id() {
        return siparis_id;
    }

    public void setSiparis_id(int siparis_id) {
        this.siparis_id = siparis_id;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getSiparis_tarih() {
        return siparis_tarih;
    }

    public void setSiparis_tarih(Date siparis_tarih) {
        this.siparis_tarih = siparis_tarih;
    }

    public Date getBitis_tarih() {
        return bitis_tarih;
    }

    public void setBitis_tarih(Date bitis_tarih) {
        this.bitis_tarih = bitis_tarih;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public ArrayList<Urun> getUrunler() {
        return urunler;
    }

    public void setUrunler(ArrayList<Urun> urunler) {
        this.urunler = urunler;
    }
    
    public double getToplam() {
        return toplam;
    }

    public void setUrunler(double toplam) {
        this.toplam = toplam;
    }
    
    @Override
    public String toString(){
        String str =  "ID: " + siparis_id + " Durum: " + durum
                + " Açıklama: " + aciklama + " S. Tarih: " + siparis_tarih
                + " B.Tarih: " + bitis_tarih + " Firma: " + firma.toString();
        for( int i = 0; i < urunler.size(); i++){
            str += urunler.get(i);
        }
        
        return str;
    }
}
