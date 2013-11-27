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
public class Firma {
    
    private String firma_adi;
    private String ilgili_adi;
    private String mail;
    private String tel;
    private String gsm;
    private String fax;

    public Firma(String firma_adi, String ilgili_adi, String mail, String tel, String gsm, String fax) {
        this.firma_adi = firma_adi;
        this.ilgili_adi = ilgili_adi;
        this.mail = mail;
        this.tel = tel;
        this.gsm = gsm;
        this.fax = fax;
    }

    public String getFirma_adi() {
        return firma_adi;
    }

    public void setFirma_adi(String firma_adi) {
        this.firma_adi = firma_adi;
    }

    public String getIlgili_adi() {
        return ilgili_adi;
    }

    public void setIlgili_adi(String ilgili_adi) {
        this.ilgili_adi = ilgili_adi;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Override
    public String toString(){
        return  "Firma: " + firma_adi + "\n"
                + " İlgili: " + ilgili_adi + "\n"
                + " Mail: " + mail + "\n"
                + " Tel: " + tel + "\n"
                + " GSM: " + gsm + "\n"
                + " Fax: " + fax + "\n";
    }    
}
