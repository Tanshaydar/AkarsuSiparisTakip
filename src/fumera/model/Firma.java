/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
