/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.model;

/**
 *
 * @author Tansel
 */
public class Urun {
    
    private String urunAdi;
    private double urunFiyati;
    private String urunDurumu;
    private String urunAciklamasi;

    public Urun(String urunAdi, double urunFiyati, String urunDurumu, String urunAciklamasi) {
        this.urunAdi = urunAdi;
        this.urunFiyati = urunFiyati;
        this.urunDurumu = urunDurumu;
        this.urunAciklamasi = urunAciklamasi;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public double getUrunFiyati() {
        return urunFiyati;
    }

    public void setUrunFiyati(float urunFiyati) {
        this.urunFiyati = urunFiyati;
    }

    public String getUrunDurumu() {
        return urunDurumu;
    }

    public void setUrunDurumu(String urunDurumu) {
        this.urunDurumu = urunDurumu;
    }

    public String getUrunAciklamasi() {
        return urunAciklamasi;
    }

    public void setUrunAciklamasi(String urunAciklamasi) {
        this.urunAciklamasi = urunAciklamasi;
    }
    
    @Override
    public String toString(){
        return  "Ürun: " + urunAdi + " Fiyat: " + urunFiyati 
                + " Durumu: " + urunDurumu + " Açıklama: " + urunAciklamasi;
    }
}
