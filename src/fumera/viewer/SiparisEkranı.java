/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.viewer;

import fumera.controller.FileLogger;
import fumera.controller.JavaDBtoObj;
import fumera.model.Firma;
import fumera.model.Siparis;
import fumera.model.Urun;
import fumera.model.User;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.ProgressMonitor;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tansel
 */
public class SiparisEkranı extends javax.swing.JFrame {

    // DATA LAYER
    private static ArrayList<Siparis> siparisler = new ArrayList<>();
    
    private static ArrayList<Siparis> aktifSiparisler = new ArrayList<>();
    private static ArrayList<Siparis> tamamlanmisSiparisler = new ArrayList<>();
    private static ArrayList<Siparis> teklifler = new ArrayList<>();
    private static ArrayList<Siparis> silinmisSiparisler = new ArrayList<>();
    
    private static User user = null;
    
    //================================================
    // GUI VARIABLES
    JComboBox urunComboBox = new JComboBox();
    ProgressMonitor progressMonitor = new ProgressMonitor( SiparisEkranı.this, "Kaydediliyor...", " NOT! ", 0, 100);
    //================================================
    
    private int currentSiparis = 0;
    
    /**
     * Creates new form SiparisEkranı
     * @param user
     */
    
    // CONSTRUCTOR
    public SiparisEkranı( User user) {
        
        SiparisEkranı.user = user;
        
        initComponents();
        SiparisleriAyir();
        SetTablePropertiesAndEdits();
        UpdateTable();
    }
    
    // This function sets the table and their editable cells' properties
    private void SetTablePropertiesAndEdits() {
        
        // Ürün düzenleme kısmında ürünün Hazırlanıyor mu yoksa Tamamlanmış mı olduğunu ayarlamak için ComboBox
        urunComboBox.addItem("Hazırlanıyor");
        urunComboBox.addItem("Tamamlandı");
        urunComboBox.addItem("Özel");
        
        // Ürün girerken ve Düzenlerken bu combobox'u tabloya ekliyoruz
        yeniSiparis_urunTablosu.getColumnModel().getColumn(2).setCellEditor( new DefaultCellEditor(urunComboBox));
        siparisGoruntule_urunTablosu.getColumnModel().getColumn(2).setCellEditor( new DefaultCellEditor(urunComboBox)); 
       
        // Ürün tablolarının kutucuklarını göster
        yeniSiparis_urunTablosu.setShowVerticalLines( true);
        yeniSiparis_urunTablosu.setShowHorizontalLines( true);
        siparisGoruntule_urunTablosu.setShowVerticalLines( true);
        siparisGoruntule_urunTablosu.setShowHorizontalLines( true);
        
        // Aktif Siparişleri gösterirken
        // kutucukları göster,
        // Sadece satır seçimi yaptır
        // Ürünleri alt alta göster
        aktifSiparis_tablosu.setShowHorizontalLines( true);
        aktifSiparis_tablosu.setShowVerticalLines( true);
        aktifSiparis_tablosu.setRowSelectionAllowed( true);
        aktifSiparis_tablosu.getColumnModel().getColumn( 5).setCellRenderer( new MultiLineTableCellRenderer());
        // Tamamlanmış Siparişleri gösterirken
        // kutucukları göster,
        // Sadece satır seçimi yaptır
        // Ürünleri alt alta göster
        tamamlanmisSiparis_tablosu.setShowHorizontalLines( true);
        tamamlanmisSiparis_tablosu.setShowVerticalLines( true);
        tamamlanmisSiparis_tablosu.setRowSelectionAllowed(true);
        tamamlanmisSiparis_tablosu.getColumnModel().getColumn( 5).setCellRenderer( new MultiLineTableCellRenderer());
        // Silinmiş Siparişleri gösterirken
        // kutucukları göster,
        // Sadece satır seçimi yaptır
        // Ürünleri alt alta göster
        silinmisSiparis_tablosu.setShowHorizontalLines( true);
        silinmisSiparis_tablosu.setShowVerticalLines( true);
        silinmisSiparis_tablosu.setRowSelectionAllowed( true);
        silinmisSiparis_tablosu.getColumnModel().getColumn( 5).setCellRenderer( new MultiLineTableCellRenderer());
        // Teklifleri gösterirken
        // kutucukları göster,
        // Sadece satır seçimi yaptır
        // Ürünleri alt alta göster
        teklif_tablosu.setShowHorizontalLines( true);
        teklif_tablosu.setShowVerticalLines( true);
        teklif_tablosu.setRowSelectionAllowed( true);
        teklif_tablosu.getColumnModel().getColumn( 5).setCellRenderer( new MultiLineTableCellRenderer());
    }
    
    // Listeleri temizleyerek yeniden ayarla
    private void SiparisleriSifirla(){
        // Sipariş listelerini temizle
        siparisler.clear();
        teklifler.clear();
        aktifSiparisler.clear();
        tamamlanmisSiparisler.clear();
        silinmisSiparisler.clear();
        
        // Tabloları Temizle
        DefaultTableModel modelAktif = (DefaultTableModel) aktifSiparis_tablosu.getModel();
        modelAktif.setRowCount(0);
        DefaultTableModel modelTamamlanmis = (DefaultTableModel) tamamlanmisSiparis_tablosu.getModel();
        modelTamamlanmis.setRowCount(0);
        DefaultTableModel modelSilinmis = (DefaultTableModel) silinmisSiparis_tablosu.getModel();
        modelSilinmis.setRowCount(0);
        DefaultTableModel modelTeklif = (DefaultTableModel) teklif_tablosu.getModel();
        modelTeklif.setRowCount(0);
    }
    
    // SİPARİŞLERİ TÜRÜNE GÖRE AYIR
    private void SiparisleriAyir(){

        siparisler = JavaDBtoObj.getSiparislerFromDB();
        
        for( int i = 0; i < siparisler.size(); i++){
            switch (siparisler.get(i).getDurumInt()) {
                case 1:
                    aktifSiparisler.add( siparisler.get(i));
                    break;
                case 2:
                    tamamlanmisSiparisler.add( siparisler.get(i));
                    break;
                case 3:
                    silinmisSiparisler.add( siparisler.get(i));
                    break;
                case 4:
                    teklifler.add( siparisler.get(i));
                    break;
            }
        }
        
        // Ana listeyi temizle
        siparisler.clear();
    }
    
    // TABLOLARI GÜNCELLE
    private void UpdateTable(){
        
        DefaultTableModel modelAktif = (DefaultTableModel) aktifSiparis_tablosu.getModel();
        for( int i = 0; i < aktifSiparisler.size(); i++){
            
            String urunStr = "";
            for( int j = 0; j < aktifSiparisler.get(i).getUrunler().size(); j++){
                urunStr += aktifSiparisler.get(i).getUrunler().get(j).getUrunAdi();
                if( j < aktifSiparisler.get(i).getUrunler().size() -1)
                    urunStr += "\r\n";
            }
            
            modelAktif.insertRow(aktifSiparis_tablosu.getRowCount(), new Object[]{
            aktifSiparisler.get(i).getFirma().getFirma_adi(),
            aktifSiparisler.get(i).getSiparisi_isteyen(),
            aktifSiparisler.get(i).getSiparisi_alan(),
            aktifSiparisler.get(i).getSiparis_tarih(),
            aktifSiparisler.get(i).getSiparis_istenen_tarih(),
            urunStr});
            if( aktifSiparisler.get(i).getAciklama() != null){
                //Arka plan değişecek
            }
        }
        aktifSiparis_tablosu.revalidate();

        DefaultTableModel modelTamamlanmis = (DefaultTableModel) tamamlanmisSiparis_tablosu.getModel();
        for( int i = 0; i < tamamlanmisSiparisler.size(); i++){
            
            String urunStr = "";
            for( int j = 0; j < tamamlanmisSiparisler.get(i).getUrunler().size(); j++){
                urunStr += tamamlanmisSiparisler.get(i).getUrunler().get(j).getUrunAdi();
                if( j < tamamlanmisSiparisler.get(i).getUrunler().size() -1)
                    urunStr += "\r\n";
            }
            
            modelTamamlanmis.insertRow(tamamlanmisSiparis_tablosu.getRowCount(), new Object[]{
            tamamlanmisSiparisler.get(i).getFirma().getFirma_adi(),
            tamamlanmisSiparisler.get(i).getSiparisi_isteyen(),
            tamamlanmisSiparisler.get(i).getSiparisi_alan(),
            tamamlanmisSiparisler.get(i).getSiparis_tarih(),
            tamamlanmisSiparisler.get(i).getSiparis_istenen_tarih(),
            urunStr});
        }
        tamamlanmisSiparis_tablosu.revalidate();
        
        DefaultTableModel modelSilinmis = (DefaultTableModel) silinmisSiparis_tablosu.getModel();
        for( int i = 0; i < silinmisSiparisler.size(); i++){
            
            String urunStr = "";
            for( int j = 0; j < silinmisSiparisler.get(i).getUrunler().size(); j++){
                urunStr += silinmisSiparisler.get(i).getUrunler().get(j).getUrunAdi();
                if( j < silinmisSiparisler.get(i).getUrunler().size() -1)
                    urunStr += "\r\n";
            }
            
            modelSilinmis.insertRow(silinmisSiparis_tablosu.getRowCount(), new Object[]{
            silinmisSiparisler.get(i).getFirma().getFirma_adi(),
            silinmisSiparisler.get(i).getSiparisi_isteyen(),
            silinmisSiparisler.get(i).getSiparisi_alan(),
            silinmisSiparisler.get(i).getSiparis_tarih(),
            silinmisSiparisler.get(i).getSiparis_istenen_tarih(),
            urunStr});
        }
        silinmisSiparis_tablosu.revalidate();
        
        DefaultTableModel modelTeklif = (DefaultTableModel) teklif_tablosu.getModel();
        for( int i = 0; i < teklifler.size(); i++) {
            String urunStr = "";
            for( int j = 0; j < teklifler.get(i).getUrunler().size(); j++ ){
                urunStr += teklifler.get(i).getUrunler().get(j).getUrunAdi();
                if( j < teklifler.get(i).getUrunler().size() -1)
                    urunStr += "\r\n";
            }
            
            modelTeklif.insertRow( teklif_tablosu.getRowCount(), new Object[]{
            teklifler.get(i).getFirma().getFirma_adi(),
            teklifler.get(i).getSiparisi_isteyen(),
            teklifler.get(i).getSiparisi_alan(),
            teklifler.get(i).getSiparis_tarih(),
            teklifler.get(i).getSiparis_istenen_tarih(),
            urunStr});
        }
        teklif_tablosu.revalidate();
        
        currentSiparis = 0;
    }

    private void yeniSiparisTemizle(){
        DefaultTableModel urunler = (DefaultTableModel) yeniSiparis_urunTablosu.getModel();
        urunler.setRowCount(0);
        
        yeniSiparis_firmaAdi.setText("");
        yeniSiparis_ilgiliAdi.setText("");
        yeniSiparis_ePosta.setText("");
        yeniSiparis_telefon.setText("");
        yeniSiparis_gsm.setText("");
        yeniSiparis_fax.setText("");
        yeniSiparis_siparisiIsteyen.setText("");
        yeniSiparis_siparisiAlan.setText("");
        yeniSiparis_siparisAciklamasi.setText("");
        yeniSiparis_Siparis_Teklif.setSelected( false);
        yeniSiparis_Siparis_Teklif.setText("Yeni Sipariş");
    }
    
    private void siparisGoruntuleTemizle(){
        DefaultTableModel urunler = (DefaultTableModel) siparisGoruntule_urunTablosu.getModel();
        urunler.setRowCount(0);
        
        siparisGoruntule_firmaAdi.setText("");
        siparisGoruntule_ilgiliAdi.setText("");
        siparisGoruntule_ePosta.setText("");
        siparisGoruntule_telefon.setText("");
        siparisGoruntule_gsm.setText("");
        siparisGoruntule_fax.setText("");
        siparisGoruntule_siparisiIsteyen.setText("");
        siparisGoruntule_siparisiAlan.setText("");
        siparisGoruntule_siparisAciklamasi.setText("");
    }
    
    private void siparisGoruntuleEnable( boolean ayar){
        siparisGoruntule_firmaAdi.setEditable( ayar);
        siparisGoruntule_ilgiliAdi.setEditable( ayar);
        siparisGoruntule_ePosta.setEditable( ayar);
        siparisGoruntule_telefon.setEditable( ayar);
        siparisGoruntule_gsm.setEditable( ayar);
        siparisGoruntule_fax.setEditable( ayar);
        siparisGoruntule_siparisiIsteyen.setEditable( ayar);
        siparisGoruntule_siparisiAlan.setEditable( ayar);
        siparisGoruntule_siparisTarihi.setEnabled( ayar);
        siparisGoruntule_siparisIstenenTarih.setEnabled( ayar);
        siparisGoruntule_Durum.setEnabled( ayar);
        siparisGoruntule_urunTablosu.setEnabled( ayar);
        siparisGoruntule_siparisAciklamasi.setEditable( ayar);
        siparisGoruntule_yeniUrunEkle.setEnabled( ayar);
        siparisGoruntule_UrunSil.setEnabled( ayar);
        siparisGoruntule_Temizle.setEnabled( ayar);
        siparisGoruntule_Kaydet.setEnabled( ayar);
        
        if( !ayar) {
            siparisGoruntule_siparisTamamlanmaTarihi.setEnabled(ayar);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        siparisSekmeleri = new javax.swing.JTabbedPane();
        yeniSiparisGirisPaneli = new javax.swing.JPanel();
        yeniSiparis_FirmaPaneli = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        yeniSiparis_siparisAciklamasi = new javax.swing.JTextArea();
        yeniSiparis_telefon = new javax.swing.JTextField();
        yeniSiparis_fax = new javax.swing.JTextField();
        yeniSiparis_ilgiliAdi = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        sg_aciklama2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        yeniSiparis_ePosta = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        yeniSiparis_gsm = new javax.swing.JTextField();
        yeniSiparis_firmaAdi = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        yeniSiparis_siparisiIsteyen = new javax.swing.JTextField();
        yeniSiparis_siparisiAlan = new javax.swing.JTextField();
        yeniSiparis_siparisTarihi = new datechooser.beans.DateChooserCombo();
        yeniSiparis_siparisIstenenTarih = new datechooser.beans.DateChooserCombo();
        sg_aciklama4 = new javax.swing.JLabel();
        yeniSiparis_Siparis_Teklif = new javax.swing.JToggleButton();
        yeniSiparis_urunlerPaneli = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        yeniSiparis_urunTablosu = new javax.swing.JTable();
        yeniSiparis_butons = new javax.swing.JPanel();
        yeniSiparis_yeniUrunEkle = new javax.swing.JButton();
        yeniSiparis_UrunSil = new javax.swing.JButton();
        yeniSiparis_Temizle = new javax.swing.JButton();
        yeniSiparis_Kaydet = new javax.swing.JButton();
        tekliflerPaneli = new javax.swing.JPanel();
        tekflifScrollPane = new javax.swing.JScrollPane();
        teklif_tablosu = new javax.swing.JTable();
        aktifSiparislerPaneli = new javax.swing.JPanel();
        aktifSiparisScrollPane = new javax.swing.JScrollPane();
        aktifSiparis_tablosu = new javax.swing.JTable();
        tamamlanmısSiparisPaneli = new javax.swing.JPanel();
        tamamlanmisSiparisScrollPane = new javax.swing.JScrollPane();
        tamamlanmisSiparis_tablosu = new javax.swing.JTable();
        siparisGoruntulemePaneli = new javax.swing.JPanel();
        siparisGoruntule_FirmaPaneli = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        siparisGoruntule_siparisAciklamasi = new javax.swing.JTextArea();
        siparisGoruntule_telefon = new javax.swing.JTextField();
        siparisGoruntule_fax = new javax.swing.JTextField();
        siparisGoruntule_ilgiliAdi = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        sg_aciklama3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        siparisGoruntule_ePosta = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        siparisGoruntule_gsm = new javax.swing.JTextField();
        siparisGoruntule_firmaAdi = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        siparisGoruntule_siparisiIsteyen = new javax.swing.JTextField();
        siparisGoruntule_siparisiAlan = new javax.swing.JTextField();
        siparisGoruntule_siparisTarihi = new datechooser.beans.DateChooserCombo();
        siparisGoruntule_siparisIstenenTarih = new datechooser.beans.DateChooserCombo();
        jLabel39 = new javax.swing.JLabel();
        siparisGoruntule_siparisTamamlanmaTarihi = new datechooser.beans.DateChooserCombo();
        jLabel40 = new javax.swing.JLabel();
        siparisGoruntule_Durum = new javax.swing.JComboBox();
        siparisGoruntule_urunlerPaneli = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        siparisGoruntule_urunTablosu = new javax.swing.JTable();
        siparisGoruntule_butons = new javax.swing.JPanel();
        siparisGoruntule_yeniUrunEkle = new javax.swing.JButton();
        siparisGoruntule_UrunSil = new javax.swing.JButton();
        siparisGoruntule_Temizle = new javax.swing.JButton();
        siparisGoruntule_Kaydet = new javax.swing.JButton();
        siparisGoruntule_Duzenle = new javax.swing.JButton();
        siparisGoruntule_Sil = new javax.swing.JButton();
        silinmisSiparisPaneli = new javax.swing.JPanel();
        silinmisSiparisScrollPane = new javax.swing.JScrollPane();
        silinmisSiparis_tablosu = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        Dosya = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        Kullanicilar = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        Ayarlar = new javax.swing.JMenu();
        siparisFrameAlwaysOnTop = new javax.swing.JCheckBoxMenuItem();
        Yardim = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Akarsu Sipariş Takip Sistemi");
        setAlwaysOnTop(true);

        siparisSekmeleri.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        yeniSiparis_FirmaPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Sipariş Bilgileri"));
        yeniSiparis_FirmaPaneli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        yeniSiparis_siparisAciklamasi.setColumns(20);
        yeniSiparis_siparisAciklamasi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yeniSiparis_siparisAciklamasi.setRows(5);
        yeniSiparis_siparisAciklamasi.setMinimumSize(new java.awt.Dimension(200, 19));
        jScrollPane8.setViewportView(yeniSiparis_siparisAciklamasi);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel19.setText("Firma:");

        sg_aciklama2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        sg_aciklama2.setText("Açıklama:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel20.setText("Tel:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel21.setText("E-Posta:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel22.setText("Fax:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel23.setText("İlgili:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel24.setText("GSM:");

        yeniSiparis_firmaAdi.setMinimumSize(new java.awt.Dimension(200, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel27.setText("Siparişi İsteyen:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel28.setText("Siparişi Alan:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel29.setText("Sipariş Tarihi:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel30.setText("İstenen Teslim Tarihi:");

        yeniSiparis_siparisTarihi.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    yeniSiparis_siparisTarihi.setCalendarPreferredSize(new java.awt.Dimension(350, 270));
    yeniSiparis_siparisTarihi.setLocale(new java.util.Locale("tr", "TR", ""));
    yeniSiparis_siparisTarihi.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    yeniSiparis_siparisTarihi.setShowOneMonth(true);

    yeniSiparis_siparisIstenenTarih.setCalendarPreferredSize(new java.awt.Dimension(350, 270));
    yeniSiparis_siparisIstenenTarih.setLocale(new java.util.Locale("tr", "TR", ""));
    yeniSiparis_siparisIstenenTarih.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    yeniSiparis_siparisIstenenTarih.setShowOneMonth(true);

    sg_aciklama4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    sg_aciklama4.setText("Teklif?");

    yeniSiparis_Siparis_Teklif.setText("Yeni Sipariş");
    yeniSiparis_Siparis_Teklif.setToolTipText("Yeni Teklif veya Yeni Sipariş ayarını bu buton ile yapabilirsiniz. [Varsayılan: Yeni Sipariş]");
    yeniSiparis_Siparis_Teklif.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_Siparis_TeklifActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout yeniSiparis_FirmaPaneliLayout = new javax.swing.GroupLayout(yeniSiparis_FirmaPaneli);
    yeniSiparis_FirmaPaneli.setLayout(yeniSiparis_FirmaPaneliLayout);
    yeniSiparis_FirmaPaneliLayout.setHorizontalGroup(
        yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel19)
                .addComponent(jLabel23)
                .addComponent(jLabel21)
                .addComponent(jLabel20)
                .addComponent(jLabel24)
                .addComponent(jLabel22)
                .addComponent(jLabel27)
                .addComponent(jLabel28)
                .addComponent(jLabel29)
                .addComponent(jLabel30)
                .addComponent(sg_aciklama2)
                .addComponent(sg_aciklama4))
            .addGap(18, 18, 18)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addComponent(yeniSiparis_siparisiAlan)
                .addComponent(yeniSiparis_siparisiIsteyen)
                .addComponent(yeniSiparis_fax)
                .addComponent(yeniSiparis_gsm)
                .addComponent(yeniSiparis_telefon)
                .addComponent(yeniSiparis_ePosta)
                .addComponent(yeniSiparis_ilgiliAdi)
                .addComponent(yeniSiparis_firmaAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yeniSiparis_Siparis_Teklif)
                .addComponent(yeniSiparis_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    yeniSiparis_FirmaPaneliLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel19, jLabel20, jLabel21, jLabel22, jLabel23, jLabel24, jLabel27, jLabel28, jLabel29, jLabel30, sg_aciklama2});

    yeniSiparis_FirmaPaneliLayout.setVerticalGroup(
        yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel19)
                .addComponent(yeniSiparis_firmaAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel23)
                .addComponent(yeniSiparis_ilgiliAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21)
                .addComponent(yeniSiparis_ePosta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel20)
                .addComponent(yeniSiparis_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel24)
                .addComponent(yeniSiparis_gsm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel22)
                .addComponent(yeniSiparis_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(yeniSiparis_siparisiIsteyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel27))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel28)
                .addComponent(yeniSiparis_siparisiAlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel29)
                .addComponent(yeniSiparis_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(yeniSiparis_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel30))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sg_aciklama2)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sg_aciklama4)
                .addComponent(yeniSiparis_Siparis_Teklif))
            .addGap(0, 0, 0))
    );

    yeniSiparis_FirmaPaneliLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel19, jLabel20, jLabel21, jLabel22, jLabel23, jLabel24, jLabel27, jLabel28, jLabel29, jLabel30, sg_aciklama2});

    yeniSiparis_urunlerPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Ürünler"));

    yeniSiparis_urunTablosu.setAutoCreateRowSorter(true);
    yeniSiparis_urunTablosu.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
    yeniSiparis_urunTablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Ürün Adı", "Ürün Fiyatı", "Ürün Durumu", "Ürün Adedi", "Toplam Fiyat", "Ürün Açıklaması"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.Float.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            true, true, true, true, false, true
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    yeniSiparis_urunTablosu.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    yeniSiparis_urunTablosu.setFillsViewportHeight(true);
    yeniSiparis_urunTablosu.setName("Yeni Sipariş Ürünler"); // NOI18N
    yeniSiparis_urunTablosu.setRowHeight(20);
    yeniSiparis_urunTablosu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            yeniSiparis_urunTablosuPropertyChange(evt);
        }
    });
    jScrollPane9.setViewportView(yeniSiparis_urunTablosu);

    javax.swing.GroupLayout yeniSiparis_urunlerPaneliLayout = new javax.swing.GroupLayout(yeniSiparis_urunlerPaneli);
    yeniSiparis_urunlerPaneli.setLayout(yeniSiparis_urunlerPaneliLayout);
    yeniSiparis_urunlerPaneliLayout.setHorizontalGroup(
        yeniSiparis_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
    );
    yeniSiparis_urunlerPaneliLayout.setVerticalGroup(
        yeniSiparis_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
    );

    yeniSiparis_yeniUrunEkle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    yeniSiparis_yeniUrunEkle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/new_urun.png"))); // NOI18N
    yeniSiparis_yeniUrunEkle.setText("Yeni Ürün Ekle");
    yeniSiparis_yeniUrunEkle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_yeniUrunEkleActionPerformed(evt);
        }
    });

    yeniSiparis_UrunSil.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    yeniSiparis_UrunSil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/delete_urun.png"))); // NOI18N
    yeniSiparis_UrunSil.setText("Ürünü Sil");
    yeniSiparis_UrunSil.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_UrunSilActionPerformed(evt);
        }
    });

    yeniSiparis_Temizle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    yeniSiparis_Temizle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/temizle.png"))); // NOI18N
    yeniSiparis_Temizle.setText("Temizle");
    yeniSiparis_Temizle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_TemizleActionPerformed(evt);
        }
    });

    yeniSiparis_Kaydet.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    yeniSiparis_Kaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/save.png"))); // NOI18N
    yeniSiparis_Kaydet.setText("Kaydet");
    yeniSiparis_Kaydet.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_KaydetActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout yeniSiparis_butonsLayout = new javax.swing.GroupLayout(yeniSiparis_butons);
    yeniSiparis_butons.setLayout(yeniSiparis_butonsLayout);
    yeniSiparis_butonsLayout.setHorizontalGroup(
        yeniSiparis_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_butonsLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(yeniSiparis_yeniUrunEkle)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(yeniSiparis_UrunSil)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(yeniSiparis_Temizle)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(yeniSiparis_Kaydet)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    yeniSiparis_butonsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {yeniSiparis_Kaydet, yeniSiparis_Temizle, yeniSiparis_UrunSil, yeniSiparis_yeniUrunEkle});

    yeniSiparis_butonsLayout.setVerticalGroup(
        yeniSiparis_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_butonsLayout.createSequentialGroup()
            .addGap(1, 1, 1)
            .addGroup(yeniSiparis_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(yeniSiparis_yeniUrunEkle)
                .addComponent(yeniSiparis_UrunSil)
                .addComponent(yeniSiparis_Temizle)
                .addComponent(yeniSiparis_Kaydet))
            .addGap(1, 1, 1))
    );

    javax.swing.GroupLayout yeniSiparisGirisPaneliLayout = new javax.swing.GroupLayout(yeniSiparisGirisPaneli);
    yeniSiparisGirisPaneli.setLayout(yeniSiparisGirisPaneliLayout);
    yeniSiparisGirisPaneliLayout.setHorizontalGroup(
        yeniSiparisGirisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparisGirisPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(yeniSiparisGirisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(yeniSiparis_FirmaPaneli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yeniSiparis_urunlerPaneli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yeniSiparis_butons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    yeniSiparisGirisPaneliLayout.setVerticalGroup(
        yeniSiparisGirisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparisGirisPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(yeniSiparis_FirmaPaneli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(yeniSiparis_urunlerPaneli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(yeniSiparis_butons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    siparisSekmeleri.addTab("<html><body><table width='120'><tr><td><font color=\"green\"><b>Yeni Sipariş Girişi</b></font></td></tr></table></body></html>", yeniSiparisGirisPaneli);

    teklif_tablosu.setAutoCreateRowSorter(true);
    teklif_tablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Firma Adı", "Siparişi İsteyen", "Siparişi Alan", "Sipariş Alınma Tarihi", "Sipariş İstenen Tarih", "Ürunler"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    teklif_tablosu.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            teklif_tablosuMouseClicked(evt);
        }
    });
    tekflifScrollPane.setViewportView(teklif_tablosu);
    teklif_tablosu.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    javax.swing.GroupLayout tekliflerPaneliLayout = new javax.swing.GroupLayout(tekliflerPaneli);
    tekliflerPaneli.setLayout(tekliflerPaneliLayout);
    tekliflerPaneliLayout.setHorizontalGroup(
        tekliflerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(tekflifScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
    );
    tekliflerPaneliLayout.setVerticalGroup(
        tekliflerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(tekflifScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("<html><body><table width='120'>Teklifler</table></body></html>", tekliflerPaneli);

    aktifSiparis_tablosu.setAutoCreateRowSorter(true);
    aktifSiparis_tablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Firma Adı", "Siparişi İsteyen", "Siparişi Alan", "Sipariş Alınma Tarihi", "Sipariş İstenen Tarih", "Ürunler"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    aktifSiparis_tablosu.setCellSelectionEnabled(false);
    aktifSiparis_tablosu.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            aktifSiparis_tablosuMouseClicked(evt);
        }
    });
    aktifSiparisScrollPane.setViewportView(aktifSiparis_tablosu);
    aktifSiparis_tablosu.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    javax.swing.GroupLayout aktifSiparislerPaneliLayout = new javax.swing.GroupLayout(aktifSiparislerPaneli);
    aktifSiparislerPaneli.setLayout(aktifSiparislerPaneliLayout);
    aktifSiparislerPaneliLayout.setHorizontalGroup(
        aktifSiparislerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(aktifSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
    );
    aktifSiparislerPaneliLayout.setVerticalGroup(
        aktifSiparislerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(aktifSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("<html><body><table width='120'>Aktif Siparişler</table></body></html>", aktifSiparislerPaneli);

    tamamlanmisSiparis_tablosu.setAutoCreateRowSorter(true);
    tamamlanmisSiparis_tablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Firma Adı", "Siparişi İsteyen", "Siparişi Alan", "Sipariş Alınma Tarihi", "Sipariş İstenen Tarih", "Ürunler"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    tamamlanmisSiparis_tablosu.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tamamlanmisSiparis_tablosuMouseClicked(evt);
        }
    });
    tamamlanmisSiparisScrollPane.setViewportView(tamamlanmisSiparis_tablosu);
    tamamlanmisSiparis_tablosu.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    javax.swing.GroupLayout tamamlanmısSiparisPaneliLayout = new javax.swing.GroupLayout(tamamlanmısSiparisPaneli);
    tamamlanmısSiparisPaneli.setLayout(tamamlanmısSiparisPaneliLayout);
    tamamlanmısSiparisPaneliLayout.setHorizontalGroup(
        tamamlanmısSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(tamamlanmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
    );
    tamamlanmısSiparisPaneliLayout.setVerticalGroup(
        tamamlanmısSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(tamamlanmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("<html><body><table width='150'>Tamamlanmış Siparişler</table></body></html>", tamamlanmısSiparisPaneli);

    siparisGoruntule_FirmaPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Sipariş Bilgileri"));

    siparisGoruntule_siparisAciklamasi.setEditable(false);
    siparisGoruntule_siparisAciklamasi.setColumns(20);
    siparisGoruntule_siparisAciklamasi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    siparisGoruntule_siparisAciklamasi.setRows(5);
    siparisGoruntule_siparisAciklamasi.setMinimumSize(new java.awt.Dimension(200, 19));
    jScrollPane10.setViewportView(siparisGoruntule_siparisAciklamasi);

    siparisGoruntule_telefon.setEditable(false);

    siparisGoruntule_fax.setEditable(false);

    siparisGoruntule_ilgiliAdi.setEditable(false);

    jLabel25.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel25.setText("Firma:");

    sg_aciklama3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    sg_aciklama3.setText("Açıklama");

    jLabel26.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel26.setText("Tel:");

    siparisGoruntule_ePosta.setEditable(false);

    jLabel31.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel31.setText("E-Posta:");

    jLabel32.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel32.setText("Fax:");

    jLabel33.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel33.setText("İlgili:");

    jLabel34.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel34.setText("GSM:");

    siparisGoruntule_gsm.setEditable(false);

    siparisGoruntule_firmaAdi.setEditable(false);
    siparisGoruntule_firmaAdi.setMinimumSize(new java.awt.Dimension(200, 20));

    jLabel35.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel35.setText("Siparişi İsteyen:");

    jLabel36.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel36.setText("Siparişi Alan:");

    jLabel37.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel37.setText("Sipariş Tarihi:");

    jLabel38.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel38.setText("İstenen Teslim Tarihi:");

    siparisGoruntule_siparisiIsteyen.setEditable(false);

    siparisGoruntule_siparisiAlan.setEditable(false);

    siparisGoruntule_siparisTarihi.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
siparisGoruntule_siparisTarihi.setCalendarPreferredSize(new java.awt.Dimension(350, 270));
siparisGoruntule_siparisTarihi.setEnabled(false);
siparisGoruntule_siparisTarihi.setLocale(new java.util.Locale("tr", "TR", ""));
siparisGoruntule_siparisTarihi.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
siparisGoruntule_siparisTarihi.setShowOneMonth(true);

siparisGoruntule_siparisIstenenTarih.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
    new datechooser.view.appearance.ViewAppearance("custom",
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            true,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 255),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(128, 128, 128),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.LabelPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.LabelPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(255, 0, 0),
            false,
            false,
            new datechooser.view.appearance.swing.ButtonPainter()),
        (datechooser.view.BackRenderer)null,
        false,
        true)));
siparisGoruntule_siparisIstenenTarih.setCalendarPreferredSize(new java.awt.Dimension(350, 270));
siparisGoruntule_siparisIstenenTarih.setEnabled(false);
siparisGoruntule_siparisIstenenTarih.setLocale(new java.util.Locale("tr", "TR", ""));
siparisGoruntule_siparisIstenenTarih.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
siparisGoruntule_siparisIstenenTarih.setShowOneMonth(true);

jLabel39.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
jLabel39.setText("Tamamlanma Tarihi:");

siparisGoruntule_siparisTamamlanmaTarihi.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
new datechooser.view.appearance.ViewAppearance("custom",
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(0, 0, 0),
        new java.awt.Color(0, 0, 255),
        false,
        true,
        new datechooser.view.appearance.swing.ButtonPainter()),
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(0, 0, 0),
        new java.awt.Color(0, 0, 255),
        true,
        true,
        new datechooser.view.appearance.swing.ButtonPainter()),
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(0, 0, 255),
        new java.awt.Color(0, 0, 255),
        false,
        true,
        new datechooser.view.appearance.swing.ButtonPainter()),
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(128, 128, 128),
        new java.awt.Color(0, 0, 255),
        false,
        true,
        new datechooser.view.appearance.swing.LabelPainter()),
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(0, 0, 0),
        new java.awt.Color(0, 0, 255),
        false,
        true,
        new datechooser.view.appearance.swing.LabelPainter()),
    new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
        new java.awt.Color(0, 0, 0),
        new java.awt.Color(255, 0, 0),
        false,
        false,
        new datechooser.view.appearance.swing.ButtonPainter()),
    (datechooser.view.BackRenderer)null,
    false,
    true)));
    siparisGoruntule_siparisTamamlanmaTarihi.setCalendarPreferredSize(new java.awt.Dimension(350, 270));
    siparisGoruntule_siparisTamamlanmaTarihi.setEnabled(false);
    siparisGoruntule_siparisTamamlanmaTarihi.setLocale(new java.util.Locale("tr", "TR", ""));
    siparisGoruntule_siparisTamamlanmaTarihi.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    siparisGoruntule_siparisTamamlanmaTarihi.setShowOneMonth(true);

    jLabel40.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    jLabel40.setText("Sipariş Durumu:");

    siparisGoruntule_Durum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hazırlanıyor", "Tamamlandı", "Silindi", "Teklif" }));

    siparisGoruntule_Durum.setEnabled(false);
    siparisGoruntule_Durum.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            siparisGoruntule_DurumItemStateChanged(evt);
        }
    });

    javax.swing.GroupLayout siparisGoruntule_FirmaPaneliLayout = new javax.swing.GroupLayout(siparisGoruntule_FirmaPaneli);
    siparisGoruntule_FirmaPaneli.setLayout(siparisGoruntule_FirmaPaneliLayout);
    siparisGoruntule_FirmaPaneliLayout.setHorizontalGroup(
        siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntule_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel25)
                .addComponent(jLabel33)
                .addComponent(jLabel31)
                .addComponent(jLabel26)
                .addComponent(jLabel34)
                .addComponent(jLabel32)
                .addComponent(jLabel35)
                .addComponent(jLabel36)
                .addComponent(jLabel37)
                .addComponent(jLabel38)
                .addComponent(sg_aciklama3)
                .addComponent(jLabel39)
                .addComponent(jLabel40))
            .addGap(18, 18, 18)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane10)
                .addComponent(siparisGoruntule_siparisiAlan)
                .addComponent(siparisGoruntule_siparisiIsteyen)
                .addComponent(siparisGoruntule_fax)
                .addComponent(siparisGoruntule_gsm)
                .addComponent(siparisGoruntule_telefon)
                .addComponent(siparisGoruntule_ePosta)
                .addComponent(siparisGoruntule_ilgiliAdi)
                .addComponent(siparisGoruntule_firmaAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(siparisGoruntule_FirmaPaneliLayout.createSequentialGroup()
                    .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(siparisGoruntule_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(siparisGoruntule_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(siparisGoruntule_siparisTamamlanmaTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(siparisGoruntule_Durum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    siparisGoruntule_FirmaPaneliLayout.setVerticalGroup(
        siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntule_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel25)
                .addComponent(siparisGoruntule_firmaAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel33)
                .addComponent(siparisGoruntule_ilgiliAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel31)
                .addComponent(siparisGoruntule_ePosta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel26)
                .addComponent(siparisGoruntule_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel34)
                .addComponent(siparisGoruntule_gsm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel32)
                .addComponent(siparisGoruntule_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(siparisGoruntule_siparisiIsteyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel35))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel36)
                .addComponent(siparisGoruntule_siparisiAlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel37)
                .addComponent(siparisGoruntule_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(siparisGoruntule_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel38))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(siparisGoruntule_siparisTamamlanmaTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel39))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel40)
                .addComponent(siparisGoruntule_Durum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(siparisGoruntule_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sg_aciklama3)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    siparisGoruntule_urunlerPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Ürünler"));

    siparisGoruntule_urunTablosu.setAutoCreateRowSorter(true);
    siparisGoruntule_urunTablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Ürün Adı", "Ürün Fiyatı", "Ürün Durumu", "Ürün Adedi", "Toplam Fiyat", "Ürün Açıklaması"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.Float.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            true, true, true, true, false, true
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    siparisGoruntule_urunTablosu.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    siparisGoruntule_urunTablosu.setEnabled(false);
    siparisGoruntule_urunTablosu.setFillsViewportHeight(true);
    siparisGoruntule_urunTablosu.setName("Yeni Sipariş Ürünler"); // NOI18N
    siparisGoruntule_urunTablosu.setRowHeight(20);
    siparisGoruntule_urunTablosu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            siparisGoruntule_urunTablosuPropertyChange(evt);
        }
    });
    jScrollPane11.setViewportView(siparisGoruntule_urunTablosu);

    javax.swing.GroupLayout siparisGoruntule_urunlerPaneliLayout = new javax.swing.GroupLayout(siparisGoruntule_urunlerPaneli);
    siparisGoruntule_urunlerPaneli.setLayout(siparisGoruntule_urunlerPaneliLayout);
    siparisGoruntule_urunlerPaneliLayout.setHorizontalGroup(
        siparisGoruntule_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane11)
    );
    siparisGoruntule_urunlerPaneliLayout.setVerticalGroup(
        siparisGoruntule_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
    );

    siparisGoruntule_yeniUrunEkle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_yeniUrunEkle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/new_urun.png"))); // NOI18N
    siparisGoruntule_yeniUrunEkle.setText("Yeni Ürün Ekle");
    siparisGoruntule_yeniUrunEkle.setEnabled(false);
    siparisGoruntule_yeniUrunEkle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_yeniUrunEkleActionPerformed(evt);
        }
    });

    siparisGoruntule_UrunSil.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_UrunSil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/delete_urun.png"))); // NOI18N
    siparisGoruntule_UrunSil.setText("Ürünü Sil");
    siparisGoruntule_UrunSil.setEnabled(false);
    siparisGoruntule_UrunSil.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_UrunSilActionPerformed(evt);
        }
    });

    siparisGoruntule_Temizle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_Temizle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/temizle.png"))); // NOI18N
    siparisGoruntule_Temizle.setText("Temizle");
    siparisGoruntule_Temizle.setEnabled(false);
    siparisGoruntule_Temizle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_TemizleActionPerformed(evt);
        }
    });

    siparisGoruntule_Kaydet.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_Kaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/save.png"))); // NOI18N
    siparisGoruntule_Kaydet.setText("Kaydet");
    siparisGoruntule_Kaydet.setEnabled(false);
    siparisGoruntule_Kaydet.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_KaydetActionPerformed(evt);
        }
    });

    siparisGoruntule_Duzenle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_Duzenle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/edit.png"))); // NOI18N
    siparisGoruntule_Duzenle.setText("Siparişi Düzenle");
    siparisGoruntule_Duzenle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_DuzenleActionPerformed(evt);
        }
    });

    siparisGoruntule_Sil.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    siparisGoruntule_Sil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/delete.png"))); // NOI18N
    siparisGoruntule_Sil.setText("Siparişi Sil");
    siparisGoruntule_Sil.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            siparisGoruntule_SilActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout siparisGoruntule_butonsLayout = new javax.swing.GroupLayout(siparisGoruntule_butons);
    siparisGoruntule_butons.setLayout(siparisGoruntule_butonsLayout);
    siparisGoruntule_butonsLayout.setHorizontalGroup(
        siparisGoruntule_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntule_butonsLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(siparisGoruntule_yeniUrunEkle)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_UrunSil)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_Temizle)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_Kaydet)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_Duzenle)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_Sil)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    siparisGoruntule_butonsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {siparisGoruntule_Duzenle, siparisGoruntule_Kaydet, siparisGoruntule_Sil, siparisGoruntule_Temizle, siparisGoruntule_UrunSil, siparisGoruntule_yeniUrunEkle});

    siparisGoruntule_butonsLayout.setVerticalGroup(
        siparisGoruntule_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntule_butonsLayout.createSequentialGroup()
            .addGap(1, 1, 1)
            .addGroup(siparisGoruntule_butonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(siparisGoruntule_yeniUrunEkle)
                .addComponent(siparisGoruntule_UrunSil)
                .addComponent(siparisGoruntule_Temizle)
                .addComponent(siparisGoruntule_Kaydet)
                .addComponent(siparisGoruntule_Duzenle)
                .addComponent(siparisGoruntule_Sil))
            .addGap(1, 1, 1))
    );

    javax.swing.GroupLayout siparisGoruntulemePaneliLayout = new javax.swing.GroupLayout(siparisGoruntulemePaneli);
    siparisGoruntulemePaneli.setLayout(siparisGoruntulemePaneliLayout);
    siparisGoruntulemePaneliLayout.setHorizontalGroup(
        siparisGoruntulemePaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntulemePaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(siparisGoruntulemePaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(siparisGoruntule_FirmaPaneli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(siparisGoruntule_urunlerPaneli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(siparisGoruntule_butons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    siparisGoruntulemePaneliLayout.setVerticalGroup(
        siparisGoruntulemePaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(siparisGoruntulemePaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(siparisGoruntule_FirmaPaneli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(siparisGoruntule_urunlerPaneli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(siparisGoruntule_butons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    siparisSekmeleri.addTab("<html><body><table width='120'>Sipariş Görüntüle</table></body></html>", siparisGoruntulemePaneli);

    silinmisSiparis_tablosu.setAutoCreateRowSorter(true);
    silinmisSiparis_tablosu.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Firma Adı", "Siparişi İsteyen", "Siparişi Alan", "Sipariş Alınma Tarihi", "Sipariş İstenen Tarih", "Ürunler"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    silinmisSiparis_tablosu.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            silinmisSiparis_tablosuMouseClicked(evt);
        }
    });
    silinmisSiparisScrollPane.setViewportView(silinmisSiparis_tablosu);
    silinmisSiparis_tablosu.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    javax.swing.GroupLayout silinmisSiparisPaneliLayout = new javax.swing.GroupLayout(silinmisSiparisPaneli);
    silinmisSiparisPaneli.setLayout(silinmisSiparisPaneliLayout);
    silinmisSiparisPaneliLayout.setHorizontalGroup(
        silinmisSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(silinmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
    );
    silinmisSiparisPaneliLayout.setVerticalGroup(
        silinmisSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(silinmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("<html><body><table width='120'><tr><td><font color=\"red\">Silinmiş Siparişler</font></td></tr></table></body></html>", silinmisSiparisPaneli);

    Dosya.setText("Dosya");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
    jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/exit.png"))); // NOI18N
    jMenuItem1.setLabel("Çıkış");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    Dosya.add(jMenuItem1);

    jMenuBar1.add(Dosya);

    Kullanicilar.setText("Kullanıcılar");

    jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/add_user.png"))); // NOI18N
    jMenuItem5.setText("Kullanıcı Ekle");
    Kullanicilar.add(jMenuItem5);

    jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/edit_user.png"))); // NOI18N
    jMenuItem6.setText("Kullanıcı Düzenle");
    Kullanicilar.add(jMenuItem6);

    jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/mevcut_kullanici.png"))); // NOI18N
    jMenuItem7.setText("Mevcut Kullanıcı");
    Kullanicilar.add(jMenuItem7);

    jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/kullanici_istatistikleri.png"))); // NOI18N
    jMenuItem8.setText("Kullanıcı İstatistikleri");
    Kullanicilar.add(jMenuItem8);

    jMenuBar1.add(Kullanicilar);

    Ayarlar.setText("Ayarlar");

    siparisFrameAlwaysOnTop.setSelected(true);
    siparisFrameAlwaysOnTop.setText("En Önde Tut");
    siparisFrameAlwaysOnTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/alwaysOnTop_True.png"))); // NOI18N
    siparisFrameAlwaysOnTop.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            siparisFrameAlwaysOnTopItemStateChanged(evt);
        }
    });
    Ayarlar.add(siparisFrameAlwaysOnTop);
    siparisFrameAlwaysOnTop.getAccessibleContext().setAccessibleName("siparisFrameAlwaysOnTop");

    jMenuBar1.add(Ayarlar);

    Yardim.setText("Yardım");

    jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/yardim.png"))); // NOI18N
    jMenuItem2.setText("Yardım");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    Yardim.add(jMenuItem2);

    jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/favicon.png"))); // NOI18N
    jMenuItem3.setText("Hakkında");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem3ActionPerformed(evt);
        }
    });
    Yardim.add(jMenuItem3);

    jMenuBar1.add(Yardim);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(siparisSekmeleri)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(siparisSekmeleri)
    );

    siparisSekmeleri.getAccessibleContext().setAccessibleName("Siparişler");

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:

        Fumera_Hakkinda hakkinda = new Fumera_Hakkinda();
        hakkinda.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/fumera/icons/favicon.png")));
        hakkinda.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        hakkinda.removeNotify();
        hakkinda.setUndecorated(true);  
        hakkinda.addNotify();
        hakkinda.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        hakkinda.pack();
        hakkinda.setLocationRelativeTo( SiparisEkranı.this);
        hakkinda.setVisible( true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void aktifSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aktifSiparis_tablosuMouseClicked
        // TODO add your handling code here:
        siparisGoruntuleEnable( false);
        if( evt.getClickCount() >= 2) {
            siparisGoruntule( aktifSiparis_tablosu.getSelectedRow(), 1);
        }
    }//GEN-LAST:event_aktifSiparis_tablosuMouseClicked

    private void yeniSiparis_yeniUrunEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_yeniUrunEkleActionPerformed
        // TODO add your handling code here:
        String eksik = "";
        if( yeniSiparis_urunTablosu.isEditing()){
            yeniSiparis_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        boolean boslukVar = false;
        if ( yeniSiparis_urunTablosu.getRowCount() != 0) {
            
            int rows = yeniSiparis_urunTablosu.getRowCount() - 1;
            if( yeniSiparis_urunTablosu.getValueAt(rows, 0) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Adı";
            }
            if( yeniSiparis_urunTablosu.getValueAt(rows, 1) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Fiyatı";
            }
            if( yeniSiparis_urunTablosu.getValueAt(rows, 3) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Adedi";
            }
        }
        if( boslukVar) {
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( SiparisEkranı.this, "Ürün Bilgileri Eksik!" + eksik,
                            "Hata!", JOptionPane.WARNING_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            DefaultTableModel model = (DefaultTableModel) yeniSiparis_urunTablosu.getModel();
            model.insertRow(yeniSiparis_urunTablosu.getRowCount(), new Object[]{ null, null, urunComboBox.getItemAt(0), null, null, null});
            yeniSiparis_urunTablosu.revalidate();
        }
    }//GEN-LAST:event_yeniSiparis_yeniUrunEkleActionPerformed

    private void yeniSiparis_TemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_TemizleActionPerformed
        // TODO add your handling code here:
        
        if( yeniSiparis_urunTablosu.isEditing()){
            yeniSiparis_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        Object[] options = {"Evet, temizle!", "Hayır, temizleme!"};
        int result  = JOptionPane.showOptionDialog( SiparisEkranı.this, "Seçili Ürün Girdisini"
                + " 'Silmek' İstediğinize Emin misiniz?", "Seçili Ürün Girdisini Sil!", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        if( result == 0) {
            yeniSiparisTemizle();
        }
    }//GEN-LAST:event_yeniSiparis_TemizleActionPerformed

    private void yeniSiparis_UrunSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_UrunSilActionPerformed
        // TODO add your handling code here:
        
        if( yeniSiparis_urunTablosu.isEditing()){
            yeniSiparis_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        Object[] options = {"Evet, sil!", "Hayır, silme!"};
        int result  = JOptionPane.showOptionDialog( SiparisEkranı.this, "Yeni Sipariş Girdilerini"
                + " 'Temizlemek' İstediğinize Emin misiniz?", "Sipariş Girdilerini Temizle!", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        if( result == 0) {
            DefaultTableModel model = (DefaultTableModel) yeniSiparis_urunTablosu.getModel();
            if( yeniSiparis_urunTablosu.getSelectedRow() != -1)
                model.removeRow( yeniSiparis_urunTablosu.getSelectedRow());
            yeniSiparis_urunTablosu.revalidate();
        }
    }//GEN-LAST:event_yeniSiparis_UrunSilActionPerformed

    private void yeniSiparis_KaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_KaydetActionPerformed
        // TODO add your handling code here:
        if( yeniSiparis_urunTablosu.isEditing()){
            yeniSiparis_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        String eksik = "";
        boolean boslukVar = false;
        if ( yeniSiparis_urunTablosu.getRowCount() != 0) {
            
            int rows = yeniSiparis_urunTablosu.getRowCount() - 1;
            if( yeniSiparis_urunTablosu.getValueAt(rows, 0) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Adı";
            }
            if( yeniSiparis_urunTablosu.getValueAt(rows, 1) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Fiyatı";
            }
            if( yeniSiparis_urunTablosu.getValueAt(rows, 3) == null){
                boslukVar = true;
                eksik += "\n\r - Ürün Adedi";
            }
        }

        if( boslukVar) {
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( SiparisEkranı.this, "Ürün Bilgileri Eksik!" + eksik,
                            "Hata!", JOptionPane.WARNING_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            yeniSiparis_urunTablosu.revalidate();
            progressMonitor.setNote("Yeni Sipariş Alınıyor...");
            progressMonitor.setProgress(0);

            Siparis yeniSiparis;
            Firma yeniFirma;
            ArrayList<Urun> yeniUrunler = new ArrayList<>();

            double toplam = 0;
            yeniFirma = new Firma( yeniSiparis_firmaAdi.getText(), yeniSiparis_ilgiliAdi.getText(),
                    yeniSiparis_ePosta.getText(), yeniSiparis_telefon.getText(), yeniSiparis_gsm.getText(), yeniSiparis_fax.getText());


            //System.out.println( yeniFirma.toString());
            /////////////////////////////
            progressMonitor.setNote("Sipariş Oluşturuluyor...");
            progressMonitor.setProgress(15);

            //System.out.println( yeniSiparis_urunTablosu.getRowCount());

            for( int i = 0; i < yeniSiparis_urunTablosu.getRowCount(); i++){

                String urunAdi = (String) yeniSiparis_urunTablosu.getValueAt(i, 0);

                int urunAdedi;
                if( yeniSiparis_urunTablosu.getValueAt(i, 3) == null)
                    urunAdedi = 0;
                else
                    urunAdedi = (int) yeniSiparis_urunTablosu.getValueAt(i, 3);

                double urunFiyati;
                if( yeniSiparis_urunTablosu.getValueAt(i, 1) == null)
                    urunFiyati = 0;
                else
                    urunFiyati = (float) yeniSiparis_urunTablosu.getValueAt(i, 1);

                String urunDurumu = (String) yeniSiparis_urunTablosu.getValueAt(i, 2);
                String urunAciklamasi = (String) yeniSiparis_urunTablosu.getValueAt(i, 5);

                Urun urun = new Urun( urunAdi, urunFiyati, urunAdedi, urunDurumu, urunAciklamasi );
                yeniUrunler.add( urun);

                toplam += urunAdedi * urunFiyati;
                System.out.println(yeniUrunler.get(i).toString());
            }
            /////////////////////////////
            progressMonitor.setNote("Ürünler işleniyor...");
            progressMonitor.setProgress(35);

            int durum = 0;
            if( yeniSiparis_Siparis_Teklif.getText().equalsIgnoreCase("Teklif"))
                durum = 1;

            yeniSiparis = new Siparis(
                    0,
                    null,
                    yeniSiparis_siparisiIsteyen.getText(),
                    yeniSiparis_siparisiAlan.getText(), 
                    yeniSiparis_siparisAciklamasi.getText(),
                    yeniSiparis_siparisTarihi.getCurrent().getTime(),
                    yeniSiparis_siparisIstenenTarih.getCurrent().getTime(), 
                    null,
                    yeniFirma,
                    yeniUrunler,
                    toplam);

            //System.out.println( yeniSiparis);

            /////////////////////////////
            progressMonitor.setNote("Kaydediliyor...");
            progressMonitor.setProgress(50);
            JavaDBtoObj.insertYeniSiparisToDB(yeniSiparis, durum);

            /////////////////////////////
            progressMonitor.setNote("Tablolar güncelleniyor...");
            progressMonitor.setProgress(75);
            yeniSiparisTemizle();

            SiparisleriSifirla();
            SiparisleriAyir();
            UpdateTable();

            if( durum == 0)
                siparisSekmeleri.setSelectedIndex(2);
            else
                siparisSekmeleri.setSelectedIndex(1);
            /////////////////////////////
            progressMonitor.setProgress(100);
        }
    }//GEN-LAST:event_yeniSiparis_KaydetActionPerformed

    private void tamamlanmisSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tamamlanmisSiparis_tablosuMouseClicked
        // TODO add your handling code here:
        siparisGoruntuleEnable( false);
        if( evt.getClickCount() >= 2) {
            siparisGoruntule( tamamlanmisSiparis_tablosu.getSelectedRow(), 2);
        }
    }//GEN-LAST:event_tamamlanmisSiparis_tablosuMouseClicked

    private void silinmisSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_silinmisSiparis_tablosuMouseClicked
        // TODO add your handling code here:
        siparisGoruntuleEnable( false);
        if( evt.getClickCount() >= 2) {
            siparisGoruntule( silinmisSiparis_tablosu.getSelectedRow(), 3);
        }
    }//GEN-LAST:event_silinmisSiparis_tablosuMouseClicked

    private void siparisGoruntule_yeniUrunEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_yeniUrunEkleActionPerformed
        // TODO add your handling code here:
        String eksik = "";
        
        if( siparisGoruntule_urunTablosu.isEditing()){
            siparisGoruntule_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        boolean boslukVar = false;
        System.out.println( siparisGoruntule_urunTablosu.getRowCount());
        if( siparisGoruntule_urunTablosu.getRowCount() != 0) {
            int rows = siparisGoruntule_urunTablosu.getRowCount() - 1;
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 0) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Adı";
            }
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 1) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Fiyatı";
            }
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 3) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Adedi";
            }
        }
        
        if( boslukVar){
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( SiparisEkranı.this, "Ürün Bilgileri Eksik!" + eksik,
                            "Hata!", JOptionPane.WARNING_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            DefaultTableModel model = (DefaultTableModel) siparisGoruntule_urunTablosu.getModel();
            model.insertRow(siparisGoruntule_urunTablosu.getRowCount(), new Object[]{ null, null, urunComboBox.getItemAt(0), null, null, null});
            siparisGoruntule_urunTablosu.revalidate();
        }
    }//GEN-LAST:event_siparisGoruntule_yeniUrunEkleActionPerformed

    private void siparisGoruntule_UrunSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_UrunSilActionPerformed
        // TODO add your handling code here:
        
        if( siparisGoruntule_urunTablosu.isEditing()){
            siparisGoruntule_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        Object[] options = {"Evet, sil!", "Hayır, silme!"};
        int result  = JOptionPane.showOptionDialog( SiparisEkranı.this, "Yeni Sipariş Girdilerini"
                + " 'Temizlemek' İstediğinize Emin misiniz?", "Sipariş Girdilerini Temizle!", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        if( result == 0) {
            DefaultTableModel model = (DefaultTableModel) siparisGoruntule_urunTablosu.getModel();
            if( siparisGoruntule_urunTablosu.getSelectedRow() != -1)
                model.removeRow( siparisGoruntule_urunTablosu.getSelectedRow());
            siparisGoruntule_urunTablosu.revalidate();
        }
    }//GEN-LAST:event_siparisGoruntule_UrunSilActionPerformed

    private void siparisGoruntule_TemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_TemizleActionPerformed
        // TODO add your handling code here:
        Object[] options = {"Evet, temizle!", "Hayır, temizleme!"};
        int result  = JOptionPane.showOptionDialog( SiparisEkranı.this, "Yeni Sipariş Girdilerini"
                + " 'Temizlemek' İstediğinize Emin misiniz?", "Sipariş Girdilerini Temizle!", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        if( result == 0) {
            siparisGoruntuleTemizle();
        }
    }//GEN-LAST:event_siparisGoruntule_TemizleActionPerformed

    private void siparisGoruntule_KaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_KaydetActionPerformed
        // TODO add your handling code here:
        
        if( siparisGoruntule_urunTablosu.isEditing()){
            siparisGoruntule_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        String eksik = "";
        boolean boslukVar = false;
        if( siparisGoruntule_urunTablosu.getRowCount() != 0) {
            int rows = siparisGoruntule_urunTablosu.getRowCount() - 1;
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 0) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Adı";
            }
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 1) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Fiyatı";
            }
            if( siparisGoruntule_urunTablosu.getValueAt(rows, 3) == null) {
                boslukVar = true;
                eksik += "\n\r - Ürün Adedi";
            }
        }
        
        if( boslukVar){
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( SiparisEkranı.this, "Ürün Bilgileri Eksik!" + eksik,
                            "Hata!", JOptionPane.WARNING_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {

            progressMonitor.setProgress(0);
            progressMonitor.setNote("Sipariş Düzenleniyor...");

            Siparis siparisDuzenle;
            Firma firmaDuzenle;
            ArrayList<Urun> urunlerDuzenle = new ArrayList<Urun>();
            double toplamDuzenle = 0;
            firmaDuzenle = new Firma( siparisGoruntule_firmaAdi.getText(), siparisGoruntule_ilgiliAdi.getText(),
                    siparisGoruntule_ePosta.getText(), siparisGoruntule_telefon.getText(), siparisGoruntule_gsm.getText(), siparisGoruntule_fax.getText());

            //System.out.println( yeniFirma.toString());
            ///////////////////////////////////
            progressMonitor.setNote("Ürünler işleniyor...");
            progressMonitor.setProgress(15);
            for( int i = 0; i < siparisGoruntule_urunTablosu.getRowCount(); i++){
                urunlerDuzenle.add( new Urun( 
                        siparisGoruntule_urunTablosu.getValueAt(i, 0).toString(),
                        Double.parseDouble(siparisGoruntule_urunTablosu.getValueAt(i, 1).toString()), 
                        Integer.parseInt(siparisGoruntule_urunTablosu.getValueAt(i, 3).toString()),
                        siparisGoruntule_urunTablosu.getValueAt(i, 2).toString(), 
                        siparisGoruntule_urunTablosu.getValueAt(i, 5).toString()));
                        toplamDuzenle += Double.parseDouble(siparisGoruntule_urunTablosu.getValueAt(i, 4).toString());
            }

            String yeniDurum = null;
            switch( siparisGoruntule_Durum.getSelectedIndex()){
                case 0:
                    yeniDurum = "Hazırlanıyor";
                    break;
                case 1:
                    yeniDurum = "Tamamlandı";
                    break;
                case 2:
                    yeniDurum = "Silindi";
                    break;
                case 3:
                    yeniDurum = "Teklif";
                    break;
            }

            ///////////////////////////////////
            progressMonitor.setNote("İşleniyor...");
            progressMonitor.setProgress(15);

            if( siparisGoruntule_Durum.getSelectedIndex() == 1) {
                siparisDuzenle = new Siparis(
                        currentSiparis,
                        yeniDurum,
                        siparisGoruntule_siparisiIsteyen.getText(),
                        siparisGoruntule_siparisiAlan.getText(), 
                        siparisGoruntule_siparisAciklamasi.getText(),
                        siparisGoruntule_siparisTarihi.getCurrent().getTime(),
                        siparisGoruntule_siparisIstenenTarih.getCurrent().getTime(), 
                        siparisGoruntule_siparisTamamlanmaTarihi.getCurrent().getTime(),
                        firmaDuzenle,
                        urunlerDuzenle,
                        toplamDuzenle);
            } else {
                siparisDuzenle = new Siparis(
                        currentSiparis,
                        yeniDurum,
                        siparisGoruntule_siparisiIsteyen.getText(),
                        siparisGoruntule_siparisiAlan.getText(), 
                        siparisGoruntule_siparisAciklamasi.getText(),
                        siparisGoruntule_siparisTarihi.getCurrent().getTime(),
                        siparisGoruntule_siparisIstenenTarih.getCurrent().getTime(), 
                        null,
                        firmaDuzenle,
                        urunlerDuzenle,
                        toplamDuzenle);
            }
            ///////////////////////////////////
            progressMonitor.setNote("Kaydediliyor...");
            progressMonitor.setProgress(50);
            JavaDBtoObj.updateSiparisToDB(siparisDuzenle);

            ///////////////////////////////////
            progressMonitor.setNote("Tablolar güncelleniyor...");
            progressMonitor.setProgress(75);

            siparisGoruntuleTemizle();
            SiparisleriSifirla();
            SiparisleriAyir();
            UpdateTable();

            ///////////////////////////////////
            switch(siparisDuzenle.getDurumInt()){
                case 1:
                    siparisSekmeleri.setSelectedIndex(2);
                    break;
                case 2:
                    siparisSekmeleri.setSelectedIndex(3);
                    break;
                case 3:
                    siparisSekmeleri.setSelectedIndex(5);
                    break;
                case 4:
                    siparisSekmeleri.setSelectedIndex(1);
                    break;
            }
            siparisGoruntuleEnable( false);
            ///////////////////////////////////
            progressMonitor.setProgress(100);
        }
    }//GEN-LAST:event_siparisGoruntule_KaydetActionPerformed

    private void siparisGoruntule_SilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_SilActionPerformed
        // TODO add your handling code here:
        if( siparisGoruntule_urunTablosu.isEditing()){
            siparisGoruntule_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        if( currentSiparis != 0) {
            Object[] options = {"Evet, sil", "Hayır, silme"};
            int sonuc = JOptionPane.showOptionDialog( SiparisEkranı.this, "Siparişi Silmek İstediğinize Emin misiniz?",
                    "Sipariş Silme Onayı", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
            if( sonuc == 0){
                progressMonitor.setNote("Siliniyor...");
                progressMonitor.setProgress(0);
                JavaDBtoObj.SiparisiSil( currentSiparis);
                progressMonitor.setProgress(25);
                SiparisleriSifirla();
                SiparisleriAyir();
                progressMonitor.setProgress(50);
                progressMonitor.setNote("Tablolar güncelleniyor...");
                UpdateTable();
                siparisGoruntuleTemizle();
                progressMonitor.setNote("Arayüze işleniyor...");
                progressMonitor.setProgress(75);
                siparisSekmeleri.setSelectedIndex(5);
                progressMonitor.setProgress(100);
            }
        }
        else
            JOptionPane.showMessageDialog( SiparisEkranı.this, "Sipariş Seçmediniz!", "Hata!", JOptionPane.INFORMATION_MESSAGE, null);
    }//GEN-LAST:event_siparisGoruntule_SilActionPerformed

    private void siparisGoruntule_DuzenleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparisGoruntule_DuzenleActionPerformed
        // TODO add your handling code here:
        if( siparisGoruntule_urunTablosu.isEditing()){
            siparisGoruntule_urunTablosu.getCellEditor().stopCellEditing();
        }
        
        if( currentSiparis != 0)
            siparisGoruntuleEnable( true);
        else
            JOptionPane.showMessageDialog( SiparisEkranı.this, "Sipariş Seçmediniz!", "Hata!", JOptionPane.INFORMATION_MESSAGE, null);
    }//GEN-LAST:event_siparisGoruntule_DuzenleActionPerformed

    private void siparisGoruntule_DurumItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_siparisGoruntule_DurumItemStateChanged
        // TODO add your handling code here:
        if( siparisGoruntule_Durum.getSelectedIndex() == 1){
            siparisGoruntule_siparisTamamlanmaTarihi.setEnabled( true);
        } else {
            siparisGoruntule_siparisTamamlanmaTarihi.setEnabled( false);
        }
    }//GEN-LAST:event_siparisGoruntule_DurumItemStateChanged

    private void yeniSiparis_Siparis_TeklifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_Siparis_TeklifActionPerformed
        // TODO add your handling code here:
        if( yeniSiparis_Siparis_Teklif.getText().equalsIgnoreCase("Teklif"))
            yeniSiparis_Siparis_Teklif.setText("Yeni Sipariş");
        else
            yeniSiparis_Siparis_Teklif.setText("Teklif");
    }//GEN-LAST:event_yeniSiparis_Siparis_TeklifActionPerformed

    private void yeniSiparis_urunTablosuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_yeniSiparis_urunTablosuPropertyChange
        // TODO add your handling code here:
        if( yeniSiparis_urunTablosu.getEditingColumn()  == 1 || yeniSiparis_urunTablosu.getEditingColumn() == 3) {
            int adet = 0;
            float fiyat = 0;
            if( yeniSiparis_urunTablosu.getValueAt( yeniSiparis_urunTablosu.getSelectedRow(), 3) != null 
                    &&  yeniSiparis_urunTablosu.getValueAt( yeniSiparis_urunTablosu.getSelectedRow(), 1) != null ){
                float toplam = (int)yeniSiparis_urunTablosu.getValueAt( yeniSiparis_urunTablosu.getSelectedRow(), 3) 
                        * (float)yeniSiparis_urunTablosu.getValueAt( yeniSiparis_urunTablosu.getSelectedRow(), 1);
                yeniSiparis_urunTablosu.setValueAt( toplam, yeniSiparis_urunTablosu.getSelectedRow(), 4);
                yeniSiparis_urunTablosu.revalidate();
            }
        } 
    }//GEN-LAST:event_yeniSiparis_urunTablosuPropertyChange

    private void siparisGoruntule_urunTablosuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_siparisGoruntule_urunTablosuPropertyChange
        // TODO add your handling code here:
        if( siparisGoruntule_urunTablosu.getEditingColumn()  == 1 || siparisGoruntule_urunTablosu.getEditingColumn() == 3) {
            int adet = 0;
            float fiyat = 0;
            if( siparisGoruntule_urunTablosu.getValueAt( siparisGoruntule_urunTablosu.getSelectedRow(), 3) != null 
                    &&  siparisGoruntule_urunTablosu.getValueAt( siparisGoruntule_urunTablosu.getSelectedRow(), 1) != null ){
                float toplam = (int)siparisGoruntule_urunTablosu.getValueAt( siparisGoruntule_urunTablosu.getSelectedRow(), 3) 
                        * (float)siparisGoruntule_urunTablosu.getValueAt( siparisGoruntule_urunTablosu.getSelectedRow(), 1);
                siparisGoruntule_urunTablosu.setValueAt( toplam, siparisGoruntule_urunTablosu.getSelectedRow(), 4);
                siparisGoruntule_urunTablosu.revalidate();
            }
        }
    }//GEN-LAST:event_siparisGoruntule_urunTablosuPropertyChange

    private void teklif_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teklif_tablosuMouseClicked
        // TODO add your handling code here:
        siparisGoruntuleEnable( false);
        if( evt.getClickCount() >= 2) {
            siparisGoruntule( teklif_tablosu.getSelectedRow(), 4);
        }
    }//GEN-LAST:event_teklif_tablosuMouseClicked

    private void siparisFrameAlwaysOnTopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_siparisFrameAlwaysOnTopItemStateChanged
        // TODO add your handling code here:
        SiparisEkranı.this.setAlwaysOnTop( siparisFrameAlwaysOnTop.getState());
    }//GEN-LAST:event_siparisFrameAlwaysOnTopItemStateChanged

    private void siparisGoruntule( int id, int type){
        
        Siparis siparis = null;
        
        switch( type){
            case 1:
                siparis = aktifSiparisler.get(id);
                break;
            case 2:
                siparis = tamamlanmisSiparisler.get(id);
                break;
            case 3:
                siparis = silinmisSiparisler.get(id);
                break;
            case 4:
                siparis = teklifler.get(id);
                break;
        }
        
        currentSiparis = siparis.getSiparis_id();
        System.out.println("Current id: " + currentSiparis);
        
        siparisGoruntule_siparisTarihi.setEnabled( true);
        siparisGoruntule_siparisIstenenTarih.setEnabled( true);
        siparisGoruntule_siparisTamamlanmaTarihi.setEnabled( true);
        
        siparisGoruntule_firmaAdi.setText( siparis.getFirma().getFirma_adi());
        siparisGoruntule_ilgiliAdi.setText( siparis.getFirma().getIlgili_adi());
        siparisGoruntule_ePosta.setText( siparis.getFirma().getMail());
        siparisGoruntule_telefon.setText( siparis.getFirma().getTel());
        siparisGoruntule_gsm.setText( siparis.getFirma().getGsm());
        siparisGoruntule_fax.setText( siparis.getFirma().getFax());
        siparisGoruntule_siparisiIsteyen.setText( siparis.getSiparisi_isteyen());
        siparisGoruntule_siparisiAlan.setText( siparis.getSiparisi_alan());

            Calendar myCal1 = Calendar.getInstance();
            myCal1.setTime( siparis.getSiparis_tarih());
        siparisGoruntule_siparisTarihi.setSelectedDate(myCal1);
        
            Calendar myCal2 = Calendar.getInstance();
            myCal2.setTime( siparis.getSiparis_istenen_tarih());
        siparisGoruntule_siparisIstenenTarih.setSelectedDate(myCal2);
        
        if( siparis.getDurumInt() == 2){
            Calendar myCal3 = Calendar.getInstance();
            myCal3.setTime( siparis.getBitis_tarih());
            siparisGoruntule_siparisTamamlanmaTarihi.setSelectedDate(myCal3);
        }
        
        siparisGoruntule_Durum.setSelectedIndex( type-1);
        
        siparisGoruntule_siparisAciklamasi.setText( siparis.getAciklama());        
        
        DefaultTableModel urunGoruntule = (DefaultTableModel) siparisGoruntule_urunTablosu.getModel();
        urunGoruntule.setRowCount(0);
        for( int i = 0; i < siparis.getUrunler().size(); i++){
           
            urunGoruntule.insertRow( siparisGoruntule_urunTablosu.getRowCount(), new Object[]{
                siparis.getUrunler().get(i).getUrunAdi(),
                siparis.getUrunler().get(i).getUrunFiyati(),
                siparis.getUrunler().get(i).getUrunDurumu(),
                siparis.getUrunler().get(i).getUrunAdedi(),
                siparis.getUrunler().get(i).getUrunAdedi()*siparis.getUrunler().get(i).getUrunFiyati(),
                siparis.getUrunler().get(i).getUrunAciklamasi()});
        }
        siparisGoruntule_urunTablosu.revalidate();
        
        
        siparisGoruntule_siparisTarihi.setEnabled( false);
        siparisGoruntule_siparisIstenenTarih.setEnabled( false);
        siparisGoruntule_siparisTamamlanmaTarihi.setEnabled( false);
        siparisSekmeleri.setSelectedIndex(4);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            FileLogger.hata( ex.toString());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SiparisEkranı( user).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Ayarlar;
    private javax.swing.JMenu Dosya;
    private javax.swing.JMenu Kullanicilar;
    private javax.swing.JMenu Yardim;
    private javax.swing.JScrollPane aktifSiparisScrollPane;
    private javax.swing.JTable aktifSiparis_tablosu;
    private javax.swing.JPanel aktifSiparislerPaneli;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel sg_aciklama2;
    private javax.swing.JLabel sg_aciklama3;
    private javax.swing.JLabel sg_aciklama4;
    private javax.swing.JPanel silinmisSiparisPaneli;
    private javax.swing.JScrollPane silinmisSiparisScrollPane;
    private javax.swing.JTable silinmisSiparis_tablosu;
    private javax.swing.JCheckBoxMenuItem siparisFrameAlwaysOnTop;
    private javax.swing.JComboBox siparisGoruntule_Durum;
    private javax.swing.JButton siparisGoruntule_Duzenle;
    private javax.swing.JPanel siparisGoruntule_FirmaPaneli;
    private javax.swing.JButton siparisGoruntule_Kaydet;
    private javax.swing.JButton siparisGoruntule_Sil;
    private javax.swing.JButton siparisGoruntule_Temizle;
    private javax.swing.JButton siparisGoruntule_UrunSil;
    private javax.swing.JPanel siparisGoruntule_butons;
    private javax.swing.JTextField siparisGoruntule_ePosta;
    private javax.swing.JTextField siparisGoruntule_fax;
    private javax.swing.JTextField siparisGoruntule_firmaAdi;
    private javax.swing.JTextField siparisGoruntule_gsm;
    private javax.swing.JTextField siparisGoruntule_ilgiliAdi;
    private javax.swing.JTextArea siparisGoruntule_siparisAciklamasi;
    private datechooser.beans.DateChooserCombo siparisGoruntule_siparisIstenenTarih;
    private datechooser.beans.DateChooserCombo siparisGoruntule_siparisTamamlanmaTarihi;
    private datechooser.beans.DateChooserCombo siparisGoruntule_siparisTarihi;
    private javax.swing.JTextField siparisGoruntule_siparisiAlan;
    private javax.swing.JTextField siparisGoruntule_siparisiIsteyen;
    private javax.swing.JTextField siparisGoruntule_telefon;
    private javax.swing.JTable siparisGoruntule_urunTablosu;
    private javax.swing.JPanel siparisGoruntule_urunlerPaneli;
    private javax.swing.JButton siparisGoruntule_yeniUrunEkle;
    private javax.swing.JPanel siparisGoruntulemePaneli;
    private javax.swing.JTabbedPane siparisSekmeleri;
    private javax.swing.JScrollPane tamamlanmisSiparisScrollPane;
    private javax.swing.JTable tamamlanmisSiparis_tablosu;
    private javax.swing.JPanel tamamlanmısSiparisPaneli;
    private javax.swing.JScrollPane tekflifScrollPane;
    private javax.swing.JTable teklif_tablosu;
    private javax.swing.JPanel tekliflerPaneli;
    private javax.swing.JPanel yeniSiparisGirisPaneli;
    private javax.swing.JPanel yeniSiparis_FirmaPaneli;
    private javax.swing.JButton yeniSiparis_Kaydet;
    private javax.swing.JToggleButton yeniSiparis_Siparis_Teklif;
    private javax.swing.JButton yeniSiparis_Temizle;
    private javax.swing.JButton yeniSiparis_UrunSil;
    private javax.swing.JPanel yeniSiparis_butons;
    private javax.swing.JTextField yeniSiparis_ePosta;
    private javax.swing.JTextField yeniSiparis_fax;
    private javax.swing.JTextField yeniSiparis_firmaAdi;
    private javax.swing.JTextField yeniSiparis_gsm;
    private javax.swing.JTextField yeniSiparis_ilgiliAdi;
    private javax.swing.JTextArea yeniSiparis_siparisAciklamasi;
    private datechooser.beans.DateChooserCombo yeniSiparis_siparisIstenenTarih;
    private datechooser.beans.DateChooserCombo yeniSiparis_siparisTarihi;
    private javax.swing.JTextField yeniSiparis_siparisiAlan;
    private javax.swing.JTextField yeniSiparis_siparisiIsteyen;
    private javax.swing.JTextField yeniSiparis_telefon;
    private javax.swing.JTable yeniSiparis_urunTablosu;
    private javax.swing.JPanel yeniSiparis_urunlerPaneli;
    private javax.swing.JButton yeniSiparis_yeniUrunEkle;
    // End of variables declaration//GEN-END:variables
}