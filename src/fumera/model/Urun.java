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
