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
    private int urunAdedi;
    private double urunFiyati;
    private String urunDurumu;
    private String urunAciklamasi;

    public Urun(String urunAdi, double urunFiyati, int urunAdedi, String urunDurumu, String urunAciklamasi) {
        this.urunAdi = urunAdi;
        this.urunFiyati = urunFiyati;
        this.urunAdedi = urunAdedi;
        this.urunDurumu = urunDurumu;
        this.urunAciklamasi = urunAciklamasi;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getUrunAdedi() {
        return urunAdedi;
    }

    public void setUrunAdedi(int urunAdedi) {
        this.urunAdedi = urunAdedi;
    }
    
    public double getUrunFiyati() {
        return urunFiyati;
    }

    public void setUrunFiyati(float urunFiyati) {
        this.urunFiyati = urunFiyati;
    }

    public int getUrunDurumu() {
        if( urunDurumu.equalsIgnoreCase("Hazırlanıyor"))
            return 1;
        else if( urunDurumu.equalsIgnoreCase("Tamamlandı"))
            return 2;
        else
            return 0;
    }
    
    public String getUrunDurumuStr(){
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
        return  "Ürun: " + urunAdi + "\n"
                + "Fiyat: " + urunFiyati + "\n"
                + "Adet: " + urunAdedi + "\n"
                + "Durumu: " + urunDurumu + "\n"
                + "Açıklama: " + urunAciklamasi + "\n";
    }
}
