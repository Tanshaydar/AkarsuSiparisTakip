/*
 * Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
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

package fumera.viewer;

import fumera.controller.FileLogger;
import fumera.controller.Information;
import fumera.controller.JavaConnector;
import fumera.controller.Settings;
import fumera.model.User;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.ProgressMonitor;

/**
 *
 * @author Tansel
 */
public class GirisFormu extends javax.swing.JFrame {

    Connection connection = null;
    ResultSet resultset = null;
    PreparedStatement statement = null;
    ProgressMonitor progressMonitor = new ProgressMonitor( GirisFormu.this, "Açılıyor...", " NOT! ", 0, 100);
    /**
     * Creates new form GirisFormu
     */
    public GirisFormu() {
        initComponents();
        
        connection = JavaConnector.ConnectDB();
        if( connection == null) {
            Object[] options = {"Tamam"};
            JOptionPane.showOptionDialog( GirisFormu.this, "Bağlantı Yok!", "Giriş Onayı",
                    JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
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

        jPanel1 = new javax.swing.JPanel();
        password_field = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        username_field = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmd_login = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        Dosya = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        Kullanicilar = new javax.swing.JMenu();
        KullaniciEkle = new javax.swing.JMenuItem();
        KullaniciDuzenle = new javax.swing.JMenuItem();
        MevcutKullanici = new javax.swing.JMenuItem();
        KullaniciStatistics = new javax.swing.JMenuItem();
        Ayarlar = new javax.swing.JMenu();
        girisFrameAlwaysOnTop = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        Yardim = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sipariş Takip Sistemi Giriş");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giriş", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 18), new java.awt.Color(51, 153, 255))); // NOI18N

        password_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_fieldKeyPressed(evt);
            }
        });

        jLabel1.setText("Kullanıcı Adı:");

        jLabel2.setText("Şifre:");

        cmd_login.setText("Giriş");
        cmd_login.setToolTipText("Kullanıcı adınızı ve şifrenizi girdikten sonra giriş yapabilirsiniz.");
        cmd_login.setSelected(true);
        cmd_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(username_field)
                            .addComponent(password_field, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cmd_login, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(username_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(password_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cmd_login)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/logo/logo.png"))); // NOI18N

        Dosya.setText("Dosya");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/exit.png"))); // NOI18N
        jMenuItem1.setText("Çıkış");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Dosya.add(jMenuItem1);

        jMenuBar1.add(Dosya);

        Kullanicilar.setText("Kullanıcılar");

        KullaniciEkle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/add_user.png"))); // NOI18N
        KullaniciEkle.setText("Kullanıcı Ekle");
        KullaniciEkle.setEnabled(false);
        Kullanicilar.add(KullaniciEkle);

        KullaniciDuzenle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/edit_user.png"))); // NOI18N
        KullaniciDuzenle.setText("Kullanıcı Düzenle");
        KullaniciDuzenle.setEnabled(false);
        Kullanicilar.add(KullaniciDuzenle);

        MevcutKullanici.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/mevcut_kullanici.png"))); // NOI18N
        MevcutKullanici.setText("Mevcut Kullanıcı");
        MevcutKullanici.setEnabled(false);
        Kullanicilar.add(MevcutKullanici);

        KullaniciStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/kullanici_istatistikleri.png"))); // NOI18N
        KullaniciStatistics.setText("Kullanıcı İstatistikleri");
        KullaniciStatistics.setEnabled(false);
        Kullanicilar.add(KullaniciStatistics);

        jMenuBar1.add(Kullanicilar);

        Ayarlar.setText("Ayarlar");

        girisFrameAlwaysOnTop.setSelected(true);
        girisFrameAlwaysOnTop.setText("En Önde Tut");
        girisFrameAlwaysOnTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/alwaysOnTop_True.png"))); // NOI18N
        girisFrameAlwaysOnTop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                girisFrameAlwaysOnTopItemStateChanged(evt);
            }
        });
        Ayarlar.add(girisFrameAlwaysOnTop);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/server.png"))); // NOI18N
        jMenu3.setText("Sunucu");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/settings_1.png"))); // NOI18N
        jMenuItem2.setText("Bağlantı Ayarları");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        Ayarlar.add(jMenu3);

        jMenuBar1.add(Ayarlar);

        Yardim.setText("Yardım");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/yardim.png"))); // NOI18N
        jMenuItem4.setText("Yardım");
        Yardim.add(jMenuItem4);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/guncelleme.png"))); // NOI18N
        jMenuItem3.setText("Güncellemeleri Denetle");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        Yardim.add(jMenuItem3);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/hatabildir.png"))); // NOI18N
        jMenuItem6.setText("Hata Bildir");
        Yardim.add(jMenuItem6);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fumera/icons/favicon.png"))); // NOI18N
        jMenuItem5.setText("Hakkında");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        Yardim.add(jMenuItem5);

        jMenuBar1.add(Yardim);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_loginActionPerformed
        // TODO add your handling code here:
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        
        try {
            connection = JavaConnector.ConnectDB();
            statement = connection.prepareStatement(sql);
            statement.setString(1, username_field.getText());
            statement.setString(2, password_field.getText());
            
            resultset = statement.executeQuery();
            if( resultset.next()){
                Object[] options = {"Tamam"};
                JOptionPane.showOptionDialog( GirisFormu.this, "Giriş Başarılı!", "Giriş Onayı", JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                User user = new User( resultset.getInt("user_id"), resultset.getString("userRealName"), resultset.getString("username"),
                        resultset.getString("password"), resultset.getString("level"), resultset.getInt("firma"));
                progressMonitor.setNote("Sistem Bilgileri Düzenleniyor...");
                progressMonitor.setProgress(10);
                Information.setUserLevel( user.getUserLevelInt());
                Information.setUserID( user.getUserID());
                progressMonitor.setProgress(75);
                close();
                SiparisEkrani se = new SiparisEkrani( user);
                se.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/fumera/icons/favicon.png")));
                this.setVisible(false);
                se.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE);
                se.setLocationRelativeTo( null);
                se.setVisible( true);
                progressMonitor.setProgress(100);
            }
        } catch (SQLException | HeadlessException e) {
            FileLogger.hata( e.toString());
            JOptionPane.showMessageDialog( GirisFormu.this, "Kullanıcı adı ya da şifre hatalı!");
        } finally {
            try {
                resultset.close();
                statement.close();
            } catch (SQLException e) {
                FileLogger.hata( e.toString());
            }
        }
    }//GEN-LAST:event_cmd_loginActionPerformed

    private void password_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_fieldKeyPressed
        // TODO add your handling code here:
        if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            cmd_loginActionPerformed(null);
        }
    }//GEN-LAST:event_password_fieldKeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ServerOptions options = new ServerOptions();
        options.setIconImage( Toolkit.getDefaultToolkit().getImage(getClass().getResource("/fumera/icons/settings.png")));
        options.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        /*options.removeNotify();
        options.setUndecorated(true);  
        options.addNotify();*/
        options.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        options.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        options.setAlwaysOnTop( true);
        options.pack();
        options.setLocationRelativeTo( GirisFormu.this);
        options.setVisible( true);
        options.setFields( Settings.getDBaddress(), Settings.getDBname(), Settings.getDBuser(), Settings.getDBpassword());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Fumera_Hakkinda hakkinda = new Fumera_Hakkinda();
        hakkinda.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/fumera/icons/favicon.png")));
        hakkinda.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
        /*hakkinda.removeNotify();
        hakkinda.setUndecorated(true);  
        hakkinda.addNotify();*/
        hakkinda.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        hakkinda.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        hakkinda.setAlwaysOnTop( true);
        hakkinda.pack();
        hakkinda.setLocationRelativeTo( GirisFormu.this);
        hakkinda.setVisible( true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void girisFrameAlwaysOnTopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_girisFrameAlwaysOnTopItemStateChanged
        GirisFormu.this.setAlwaysOnTop( girisFrameAlwaysOnTop.getState());
    }//GEN-LAST:event_girisFrameAlwaysOnTopItemStateChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String inputLine = "";
        try {
            URL update = new URL("http://www.fumera.com.tr/dosyalar/siparis_takip_1.txt");
            BufferedReader in = new BufferedReader( new InputStreamReader( update.openStream()));
            while( ( inputLine = in.readLine()) != null ) {
                System.out.println( inputLine);
            }
            in.close();
        } catch ( IOException e) {
            FileLogger.hata(e.toString());
        }
        
        if( Information.version.equalsIgnoreCase(inputLine)) {
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( GirisFormu.this, "Mevcut Sürümdesiniz!", "Güncelleme Kontrolü", JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            Object[] options = {"Tamam"};
                    JOptionPane.showOptionDialog( GirisFormu.this, "Daha Güncel Bir Sürüm Mevcut!", "Güncelleme Kontrolü", JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            try {
                Desktop.getDesktop().browse( new URI("http://fumera.com.tr/dosyalar/"));
            } catch (IOException | URISyntaxException e) {
                FileLogger.hata( e.toString());
            }
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    public void close(){
        WindowEvent windowClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosingEvent);
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GirisFormu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GirisFormu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GirisFormu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GirisFormu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GirisFormu girisFormu = new GirisFormu();
                girisFormu.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/fumera/icons/favicon.png")));
                girisFormu.setLocationRelativeTo( null);
                girisFormu.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Ayarlar;
    private javax.swing.JMenu Dosya;
    private javax.swing.JMenuItem KullaniciDuzenle;
    private javax.swing.JMenuItem KullaniciEkle;
    private javax.swing.JMenuItem KullaniciStatistics;
    private javax.swing.JMenu Kullanicilar;
    private javax.swing.JMenuItem MevcutKullanici;
    private javax.swing.JMenu Yardim;
    private javax.swing.JButton cmd_login;
    private javax.swing.JCheckBoxMenuItem girisFrameAlwaysOnTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField password_field;
    private javax.swing.JTextField username_field;
    // End of variables declaration//GEN-END:variables
}
