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
    private String siparisi_isteyen;
    private String siparisi_alan;
    private String aciklama;
    private Date siparis_tarih;
    private Date siparis_istenen_tarih;
    private Date bitis_tarih;
    private double toplam;
    
    private Firma firma;
    private ArrayList<Urun> urunler;

    public Siparis(int siparis_id, String durum, String siparisi_isteyen, String siparisi_alan, String aciklama, Date siparis_tarih, Date siparis_istenen_tarih,
            Date bitis_tarih, Firma firma, ArrayList<Urun> urunler, double toplam) {
        this.siparis_id = siparis_id;
        this.durum = durum;
        this.siparisi_isteyen = siparisi_isteyen;
        this.siparisi_alan = siparisi_alan;
        this.aciklama = aciklama;
        this.siparis_tarih = siparis_tarih;
        this.siparis_istenen_tarih = siparis_istenen_tarih;
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
    
    public int getDurumInt(){
        int durumInt = 0;
        switch ( durum) {
            case "Hazırlanıyor":
                durumInt = 1;
                break;
            case "Tamamlandı":
                durumInt = 2;
                break;
            case "Silindi":
                durumInt = 3;
                break;
            case "Teklif":
                durumInt = 4;
                break;
        }
        
        return durumInt;
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
        public String getSiparisi_isteyen() {
        return siparisi_isteyen;
    }

    public void setSiparisi_isteyen(String siparisi_isteyen) {
        this.siparisi_isteyen = siparisi_isteyen;
    }

    public String getSiparisi_alan() {
        return siparisi_alan;
    }

    public void setSiparisi_alan(String siparisi_alan) {
        this.siparisi_alan = siparisi_alan;
    }

    public Date getSiparis_istenen_tarih() {
        return siparis_istenen_tarih;
    }

    public void setSiparis_istenen_tarih(Date siparis_istenen_tarih) {
        this.siparis_istenen_tarih = siparis_istenen_tarih;
    }
    
    @Override
    public String toString(){
        String str =  "ID: " + siparis_id + "\n"
                + " Durum: " + durum + "\n"
                + " Siparişi İsteyen: " + siparisi_isteyen + "\n"
                + " Siparişi Alan: " + siparisi_alan + "\n"
                + " Açıklama: " + aciklama + "\n"
                + " S. Tarih: " + siparis_tarih + "\n"
                + " S. İstenen Tarih: " + siparis_istenen_tarih + "\n"
                + " S. Bitiş Tarihi: " + bitis_tarih + "\n"
                + " Firma: " + firma.toString();
        for( int i = 0; i < urunler.size(); i++){
            str += urunler.get(i);
        }

        return str;
    }
}
