/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.viewer;

import fumera.controller.JavaDBtoObj;
import fumera.model.Firma;
import fumera.model.Siparis;
import fumera.model.Urun;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Tansel
 */
public class SiparisEkranı extends javax.swing.JFrame {

    // DATA LAYER
    private ArrayList<Siparis> siparisler = new ArrayList<>();
    private ArrayList<Siparis> aktifSiparisler = new ArrayList<>();
    private ArrayList<Siparis> tamamlanmisSiparisler = new ArrayList<>();
    private ArrayList<Siparis> silinmisSiparisler = new ArrayList<>();
    
    //================================================
    // GUI VARIABLES
    TableColumn urunDurumColumn;
    JComboBox urunComboBox = new JComboBox();
    //================================================
    /**
     * Creates new form SiparisEkranı
     */
    
    // CONSTRUCTOR
    public SiparisEkranı() {
        initComponents();
        SiparisleriAyir();
        
        urunDurumColumn = yeniSiparis_urunTablosu.getColumnModel().getColumn(2);
        urunComboBox.addItem("Hazırlanıyor");
        urunComboBox.addItem("Tamamlandı");
        urunDurumColumn.setCellEditor( new DefaultCellEditor(urunComboBox));
        
        yeniSiparis_urunTablosu.setShowVerticalLines( true);
        yeniSiparis_urunTablosu.setShowHorizontalLines( true);
        
        aktifSiparis_tablosu.setShowHorizontalLines( true);
        aktifSiparis_tablosu.setShowVerticalLines( true);
        aktifSiparis_tablosu.setRowSelectionAllowed( true);
        tamamlanmisSiparis_tablosu.setShowHorizontalLines( true);
        tamamlanmisSiparis_tablosu.setShowVerticalLines( true);
        tamamlanmisSiparis_tablosu.setRowSelectionAllowed(true);
        silinmisSiparis_tablosu.setShowHorizontalLines( true);
        silinmisSiparis_tablosu.setShowVerticalLines( true);
        silinmisSiparis_tablosu.setRowSelectionAllowed( true);
        
        UpdateTable();
    }
    
    private void SiparisleriAyir(){
        
        JavaDBtoObj dbTOobj = new JavaDBtoObj();
        siparisler = dbTOobj.fetchDB();
        
        for( int i = 0; i < siparisler.size(); i++){
            if( siparisler.get(i).getDurum().equals("Hazırlanıyor")){
                aktifSiparisler.add( siparisler.get(i));
            } else if( siparisler.get(i).getDurum().equals("Tamamlandı")){
                tamamlanmisSiparisler.add( siparisler.get(i));
            } else if( siparisler.get(i).getDurum().equals("Silindi")){
                silinmisSiparisler.add( siparisler.get(i));
            }
        }
    }
    
    // TABLE UPDATE
    private void UpdateTable(){
        
        DefaultTableModel modelAktif = (DefaultTableModel) aktifSiparis_tablosu.getModel();
        for( int i = 0; i < aktifSiparisler.size(); i++){
            
            String urunStr = "";
            for( int j = 0; j < aktifSiparisler.get(i).getUrunler().size(); j++){
                urunStr += aktifSiparisler.get(i).getUrunler().get(j).getUrunAdi() + "\n";
            }
            
            modelAktif.insertRow(aktifSiparis_tablosu.getRowCount(), new Object[]{
            aktifSiparisler.get(i).getFirma().getFirma_adi(),
            aktifSiparisler.get(i).getSiparisi_isteyen(),
            aktifSiparisler.get(i).getSiparisi_alan(),
            aktifSiparisler.get(i).getSiparis_tarih(),
            aktifSiparisler.get(i).getSiparis_istenen_tarih(),
            urunStr});
        }
        aktifSiparis_tablosu.revalidate();

        DefaultTableModel modelTamamlanmis = (DefaultTableModel) tamamlanmisSiparis_tablosu.getModel();
        for( int i = 0; i < tamamlanmisSiparisler.size(); i++){
            
            String urunStr = "";
            for( int j = 0; j < tamamlanmisSiparisler.get(i).getUrunler().size(); j++){
                urunStr += tamamlanmisSiparisler.get(i).getUrunler().get(j).getUrunAdi() + "\n";
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
                urunStr += silinmisSiparisler.get(i).getUrunler().get(j).getUrunAdi() + "\n";
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
        
        //System.out.println("Aktif: " + aktifSiparisler.size() + " Bitmiş: " + tamamlanmisSiparisler.size() + " Silinmiş: " + silinmisSiparisler.size() + " Toplam:" + siparisler.size());
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
        yeniSiparis_urunlerPaneli = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        yeniSiparis_urunTablosu = new javax.swing.JTable();
        yeniSiparis_butons = new javax.swing.JPanel();
        yeniSiparis_yeniUrunEkle = new javax.swing.JButton();
        yeniSiparis_UrunSil = new javax.swing.JButton();
        yeniSiparis_Temizle = new javax.swing.JButton();
        yeniSiparis_Kaydet = new javax.swing.JButton();
        aktifSiparislerPaneli = new javax.swing.JPanel();
        aktifSiparisScrollPane = new javax.swing.JScrollPane();
        aktifSiparis_tablosu = new javax.swing.JTable();
        tamamlanmısSiparisPaneli = new javax.swing.JPanel();
        tamamlanmisSiparisScrollPane = new javax.swing.JScrollPane();
        tamamlanmisSiparis_tablosu = new javax.swing.JTable();
        siparisGoruntulemePaneli = new javax.swing.JPanel();
        silinmisSiparisPaneli = new javax.swing.JPanel();
        silinmisSiparisScrollPane = new javax.swing.JScrollPane();
        silinmisSiparis_tablosu = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Akarsu Sipariş Takip Sistemi");

        yeniSiparis_FirmaPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Sipariş Bilgileri"));

        yeniSiparis_siparisAciklamasi.setColumns(20);
        yeniSiparis_siparisAciklamasi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yeniSiparis_siparisAciklamasi.setRows(5);
        yeniSiparis_siparisAciklamasi.setMinimumSize(new java.awt.Dimension(200, 19));
        jScrollPane8.setViewportView(yeniSiparis_siparisAciklamasi);

        yeniSiparis_ilgiliAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniSiparis_ilgiliAdiActionPerformed(evt);
            }
        });

        jLabel19.setText("<html><b>Firma:</b></html>");

        sg_aciklama2.setText("<html><b>Açıklama</b><html>");

        jLabel20.setText("<html><b>Tel:</b></html>");

        yeniSiparis_ePosta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniSiparis_ePostaActionPerformed(evt);
            }
        });

        jLabel21.setText("<html><b>E-Posta:</b></html>");

        jLabel22.setText("<html><b>Fax:</b></html>");

        jLabel23.setText("<html><b>İlgili:</b></html>");

        jLabel24.setText("<html><b>GSM:</b></html>");

        yeniSiparis_firmaAdi.setMinimumSize(new java.awt.Dimension(200, 20));
        yeniSiparis_firmaAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniSiparis_firmaAdiActionPerformed(evt);
            }
        });

        jLabel27.setText("<html><b>Siparişi İsteyen:</b></html>");

        jLabel28.setText("<html><b>Siparişi Alan:</b></html>");

        jLabel29.setText("<html><b>Sipariş Tarihi:</b></html>");

        jLabel30.setText("<html><b>İstenen Teslim Tarihi:</b></html>");

        yeniSiparis_siparisiIsteyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniSiparis_siparisiIsteyenActionPerformed(evt);
            }
        });

        yeniSiparis_siparisiAlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniSiparis_siparisiAlanActionPerformed(evt);
            }
        });

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
    yeniSiparis_siparisTarihi.setCalendarPreferredSize(new java.awt.Dimension(300, 300));
    yeniSiparis_siparisTarihi.setLocale(new java.util.Locale("tr", "TR", ""));
    yeniSiparis_siparisTarihi.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    yeniSiparis_siparisTarihi.setShowOneMonth(true);

    yeniSiparis_siparisIstenenTarih.setCalendarPreferredSize(new java.awt.Dimension(300, 300));
    yeniSiparis_siparisIstenenTarih.setLocale(new java.util.Locale("tr", "TR", ""));
    yeniSiparis_siparisIstenenTarih.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    yeniSiparis_siparisIstenenTarih.setShowOneMonth(true);

    javax.swing.GroupLayout yeniSiparis_FirmaPaneliLayout = new javax.swing.GroupLayout(yeniSiparis_FirmaPaneli);
    yeniSiparis_FirmaPaneli.setLayout(yeniSiparis_FirmaPaneliLayout);
    yeniSiparis_FirmaPaneliLayout.setHorizontalGroup(
        yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(sg_aciklama2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(yeniSiparis_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addComponent(yeniSiparis_siparisiAlan)
                .addComponent(yeniSiparis_siparisiIsteyen)
                .addComponent(yeniSiparis_fax)
                .addComponent(yeniSiparis_gsm)
                .addComponent(yeniSiparis_telefon)
                .addComponent(yeniSiparis_ePosta)
                .addComponent(yeniSiparis_ilgiliAdi)
                .addComponent(yeniSiparis_firmaAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    yeniSiparis_FirmaPaneliLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel19, jLabel20, jLabel21, jLabel22, jLabel23, jLabel24, jLabel27, jLabel28, jLabel29, jLabel30, sg_aciklama2});

    yeniSiparis_FirmaPaneliLayout.setVerticalGroup(
        yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(yeniSiparis_FirmaPaneliLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_firmaAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_ilgiliAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_ePosta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_gsm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(yeniSiparis_siparisiIsteyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_siparisiAlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yeniSiparis_siparisTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(yeniSiparis_siparisIstenenTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(yeniSiparis_FirmaPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(sg_aciklama2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    yeniSiparis_FirmaPaneliLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel19, jLabel20, jLabel21, jLabel22, jLabel23, jLabel24, jLabel27, jLabel28, jLabel29, jLabel30, sg_aciklama2});

    yeniSiparis_urunlerPaneli.setBorder(javax.swing.BorderFactory.createTitledBorder("Ürünler"));

    yeniSiparis_urunTablosu.setAutoCreateRowSorter(true);
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
    yeniSiparis_urunTablosu.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane9.setViewportView(yeniSiparis_urunTablosu);

    javax.swing.GroupLayout yeniSiparis_urunlerPaneliLayout = new javax.swing.GroupLayout(yeniSiparis_urunlerPaneli);
    yeniSiparis_urunlerPaneli.setLayout(yeniSiparis_urunlerPaneliLayout);
    yeniSiparis_urunlerPaneliLayout.setHorizontalGroup(
        yeniSiparis_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
    );
    yeniSiparis_urunlerPaneliLayout.setVerticalGroup(
        yeniSiparis_urunlerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
    );

    yeniSiparis_yeniUrunEkle.setText("Yeni Ürün Ekle");
    yeniSiparis_yeniUrunEkle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_yeniUrunEkleActionPerformed(evt);
        }
    });

    yeniSiparis_UrunSil.setText("Ürünü Sil");
    yeniSiparis_UrunSil.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_UrunSilActionPerformed(evt);
        }
    });

    yeniSiparis_Temizle.setText("Temizle");
    yeniSiparis_Temizle.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            yeniSiparis_TemizleActionPerformed(evt);
        }
    });

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

    siparisSekmeleri.addTab("Yeni Sipariş Girişi", yeniSiparisGirisPaneli);

    aktifSiparis_tablosu.setAutoCreateRowSorter(true);
    aktifSiparis_tablosu.setModel(new javax.swing.table.DefaultTableModel(
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
        .addComponent(aktifSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
    );
    aktifSiparislerPaneliLayout.setVerticalGroup(
        aktifSiparislerPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(aktifSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("Aktif Siparişler", aktifSiparislerPaneli);

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
        .addComponent(tamamlanmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
    );
    tamamlanmısSiparisPaneliLayout.setVerticalGroup(
        tamamlanmısSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(tamamlanmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("Tamamlanmış Siparişler", tamamlanmısSiparisPaneli);

    javax.swing.GroupLayout siparisGoruntulemePaneliLayout = new javax.swing.GroupLayout(siparisGoruntulemePaneli);
    siparisGoruntulemePaneli.setLayout(siparisGoruntulemePaneliLayout);
    siparisGoruntulemePaneliLayout.setHorizontalGroup(
        siparisGoruntulemePaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 870, Short.MAX_VALUE)
    );
    siparisGoruntulemePaneliLayout.setVerticalGroup(
        siparisGoruntulemePaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 823, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("Sipariş Görüntüle", siparisGoruntulemePaneli);

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
        .addComponent(silinmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
    );
    silinmisSiparisPaneliLayout.setVerticalGroup(
        silinmisSiparisPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(silinmisSiparisScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
    );

    siparisSekmeleri.addTab("Silinmiş Siparişler", silinmisSiparisPaneli);

    jMenu1.setText("Dosya");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
    jMenuItem1.setLabel("Çıkış");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Kullanıcılar");
    jMenuBar1.add(jMenu2);

    jMenu3.setText("Ayarlar");
    jMenuBar1.add(jMenu3);

    jMenu4.setText("Yardım");

    jMenuItem2.setText("Yardım");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    jMenu4.add(jMenuItem2);

    jMenuItem3.setText("Hakkında");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem3ActionPerformed(evt);
        }
    });
    jMenu4.add(jMenuItem3);

    jMenuBar1.add(jMenu4);

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

    siparisSekmeleri.getAccessibleContext().setAccessibleName("Aktif Siparişler");

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
        JOptionPane.showMessageDialog(null, "Fumera Ar-Ge Yazılım Ltd. Şti. \n Yazılım Departmanı\nTansel Altınel\naltinel@fumera.com.tr", "Hakkında", JOptionPane.INFORMATION_MESSAGE, null);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void aktifSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aktifSiparis_tablosuMouseClicked
        // TODO add your handling code here:
        if( evt.getClickCount() >= 2) {
            String siparis_id_temp = "0";
            try {
                int row = aktifSiparis_tablosu.getSelectedRow();
                siparis_id_temp = aktifSiparis_tablosu.getModel().getValueAt( row, 0).toString();
                System.out.println( siparis_id_temp);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            siparisGoruntule( siparis_id_temp, 1);
        }
    }//GEN-LAST:event_aktifSiparis_tablosuMouseClicked

    private void yeniSiparis_ilgiliAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_ilgiliAdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniSiparis_ilgiliAdiActionPerformed

    private void yeniSiparis_firmaAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_firmaAdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniSiparis_firmaAdiActionPerformed

    private void yeniSiparis_siparisiIsteyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_siparisiIsteyenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniSiparis_siparisiIsteyenActionPerformed

    private void yeniSiparis_siparisiAlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_siparisiAlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniSiparis_siparisiAlanActionPerformed

    private void yeniSiparis_yeniUrunEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_yeniUrunEkleActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) yeniSiparis_urunTablosu.getModel();
        model.insertRow(yeniSiparis_urunTablosu.getRowCount(), new Object[]{ "", 0, urunComboBox.getItemAt(0), 0, 0, ""});
        yeniSiparis_urunTablosu.revalidate();
    }//GEN-LAST:event_yeniSiparis_yeniUrunEkleActionPerformed

    private void yeniSiparis_TemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_TemizleActionPerformed
        // TODO add your handling code here:
        yeniSiparis_firmaAdi.setText(null);
        yeniSiparis_ilgiliAdi.setText(null);
        yeniSiparis_ePosta.setText(null);
        yeniSiparis_telefon.setText(null);
        yeniSiparis_gsm.setText(null);
        yeniSiparis_fax.setText(null);
        yeniSiparis_siparisiIsteyen.setText(null);
        yeniSiparis_siparisiAlan.setText(null);
        yeniSiparis_siparisAciklamasi.setText(null);
    }//GEN-LAST:event_yeniSiparis_TemizleActionPerformed

    private void yeniSiparis_ePostaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_ePostaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniSiparis_ePostaActionPerformed

    private void yeniSiparis_UrunSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_UrunSilActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) yeniSiparis_urunTablosu.getModel();
        if( yeniSiparis_urunTablosu.getSelectedRow() != -1)
            model.removeRow( yeniSiparis_urunTablosu.getSelectedRow());
        yeniSiparis_urunTablosu.revalidate();
    }//GEN-LAST:event_yeniSiparis_UrunSilActionPerformed

    private void yeniSiparis_KaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniSiparis_KaydetActionPerformed
        // TODO add your handling code here:
        Siparis yeniSiparis;
        Firma yeniFirma;
        ArrayList<Urun> yeniUrunler = new ArrayList<Urun>();
        double toplam = 0;
        yeniFirma = new Firma( yeniSiparis_firmaAdi.getText(), yeniSiparis_ilgiliAdi.getText(),
                yeniSiparis_ePosta.getText(), yeniSiparis_telefon.getText(), yeniSiparis_gsm.getText(), yeniSiparis_fax.getText());
        
        //System.out.println( yeniFirma.toString());
        
        for( int i = 0; i < yeniSiparis_urunTablosu.getRowCount(); i++){
            yeniUrunler.add( new Urun( 
                    yeniSiparis_urunTablosu.getValueAt(i, 0).toString(),
                    Double.parseDouble(yeniSiparis_urunTablosu.getValueAt(i, 1).toString()), 
                    Integer.parseInt(yeniSiparis_urunTablosu.getValueAt(i, 3).toString()),
                    yeniSiparis_urunTablosu.getValueAt(i, 2).toString(), 
                    yeniSiparis_urunTablosu.getValueAt(i, 5).toString()));
                    toplam += Double.parseDouble(yeniSiparis_urunTablosu.getValueAt(i, 4).toString());
            //System.out.println(yeniUrunler.get(i).toString());
        }
        
        /*
         * Siparis(int siparis_id, String durum, String siparisi_isteyen, String siparisi_alan, String aciklama, Date siparis_tarih, Date siparis_istenen_tarih,
            Date bitis_tarih, Firma firma, ArrayList<Urun> urunler, double toplam)
         */
        yeniSiparis = new Siparis(
                new JavaDBtoObj().getNewID(),
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
        
        System.out.println( yeniSiparis);
        new JavaDBtoObj().InsertDB(yeniSiparis);
    }//GEN-LAST:event_yeniSiparis_KaydetActionPerformed

    private void tamamlanmisSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tamamlanmisSiparis_tablosuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tamamlanmisSiparis_tablosuMouseClicked

    private void silinmisSiparis_tablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_silinmisSiparis_tablosuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_silinmisSiparis_tablosuMouseClicked

    private void siparisGoruntule( String id, int type){
        /*int i = Integer.parseInt(id);
        Siparis siparis = null;

        if( aktif) {
            for( int k = 0; k < siparisler.size(); k++){
                if( siparisler.get(k).getSiparis_id() == i) {
                    //siparis = siparisler.get(k);
                    //System.out.println(k);
                    sg_firmaField.setText( siparisler.get(k).getFirma().getFirma_adi());
                    System.out.println(siparisler.get(k).getFirma().getFirma_adi());
                    sg_ilgiliField.setText( siparisler.get(k).getFirma().getIlgili_adi());
                    sg_mailField.setText( siparisler.get(k).getFirma().getMail());
                    sg_telField.setText( siparisler.get(k).getFirma().getTel());
                    sg_gsmField.setText( siparisler.get(k).getFirma().getGsm());
                    sg_faxField.setText( siparisler.get(k).getFirma().getFax());
                    sg_aciklamaField.setText( siparisler.get(k).getAciklama());
                }
            }
        } else {
            for( int k = 0; k < tamamlanmisSiparisler.size(); k++){
                if( tamamlanmisSiparisler.get(k).getSiparis_id() == i) {
                    //siparis = tamamlanmisSiparisler.get(k);
                    //System.out.println(k);
                    sg_firmaField.setText( tamamlanmisSiparisler.get(k).getFirma().getFirma_adi());
                    sg_ilgiliField.setText( tamamlanmisSiparisler.get(k).getFirma().getIlgili_adi());
                    sg_mailField.setText( tamamlanmisSiparisler.get(k).getFirma().getMail());
                    sg_telField.setText( tamamlanmisSiparisler.get(k).getFirma().getTel());
                    sg_gsmField.setText( tamamlanmisSiparisler.get(k).getFirma().getGsm());
                    sg_faxField.setText( tamamlanmisSiparisler.get(k).getFirma().getFax());
                    sg_aciklamaField.setText( tamamlanmisSiparisler.get(k).getAciklama());
                }
            }

        }
        siparisSekmeleri.setSelectedIndex(3);*/
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
            java.util.logging.Logger.getLogger(SiparisEkranı.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SiparisEkranı().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane aktifSiparisScrollPane;
    private javax.swing.JTable aktifSiparis_tablosu;
    private javax.swing.JPanel aktifSiparislerPaneli;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel sg_aciklama2;
    private javax.swing.JPanel silinmisSiparisPaneli;
    private javax.swing.JScrollPane silinmisSiparisScrollPane;
    private javax.swing.JTable silinmisSiparis_tablosu;
    private javax.swing.JPanel siparisGoruntulemePaneli;
    private javax.swing.JTabbedPane siparisSekmeleri;
    private javax.swing.JScrollPane tamamlanmisSiparisScrollPane;
    private javax.swing.JTable tamamlanmisSiparis_tablosu;
    private javax.swing.JPanel tamamlanmısSiparisPaneli;
    private javax.swing.JPanel yeniSiparisGirisPaneli;
    private javax.swing.JPanel yeniSiparis_FirmaPaneli;
    private javax.swing.JButton yeniSiparis_Kaydet;
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