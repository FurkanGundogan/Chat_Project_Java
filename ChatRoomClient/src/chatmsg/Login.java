/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmsg;

import java.awt.Image;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import chatclient.Client;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author INSECT
 */
public class Login extends javax.swing.JFrame {

    //framedeki komponentlere erişim için satatik oyun değişkeni
    public static Login ThisGame;
    //ekrandaki resim değişimi için timer yerine thread
    public Thread tmr_slider;
    //karşı tarafın seçimi seçim -1 deyse seçilmemiş
    public int RivalSelection = -1;
    //benim seçimim seçim -1 deyse seçilmemiş
    public int myselection = -1;

    Random rand;

    /**
     * Creates new form Game
     */
    @SuppressWarnings("empty-statement")
    public Login() {
        initComponents();
        ThisGame = this;
        rand = new Random();

        // resimleri döndürmek için tread aynı zamanda oyun bitiminide takip ediyor
        tmr_slider = new Thread(() -> {
            //soket bağlıysa dönsün
            while (Client.socket.isConnected()) {
                try {
                    //
                    Thread.sleep(100);
                    //eğer ikisinden biri -1 ise resim dönmeye devam etsin sonucu göstermesin
                    if (RivalSelection == -1 || myselection == -1) {

                    }// eğer iki seçim yapılmışsa sonuç gösterilebilir.  
                    else {

                        Thread.sleep(4000);
                        tmr_slider.stop();
                        //7 saniye sonra oyun bitsin tekrar bağlansın
                        Thread.sleep(7000);

                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void updateMyUserList(DefaultListModel dlm) {
        // Aktif kullanıcıların listelenmesi
        list_all_users.setModel(dlm);
    }

    public void Reset() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txt_myusername = new javax.swing.JTextField();
        btn_connect = new javax.swing.JButton();
        pnl_gamer1 = new javax.swing.JPanel();
        btn_send_message = new javax.swing.JButton();
        txt_global_chat = new java.awt.TextArea();
        txt_msg_global = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_all_users = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        btn_dc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_myusername.setText("Name");
        getContentPane().add(txt_myusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 73, -1));

        btn_connect.setText("Connect");
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });
        getContentPane().add(btn_connect, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 160, -1));

        pnl_gamer1.setBackground(new java.awt.Color(255, 153, 153));
        pnl_gamer1.setForeground(new java.awt.Color(51, 255, 0));
        pnl_gamer1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(pnl_gamer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, 259));

        btn_send_message.setText("Send");
        btn_send_message.setEnabled(false);
        btn_send_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_send_messageActionPerformed(evt);
            }
        });
        getContentPane().add(btn_send_message, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 170, -1));

        txt_global_chat.setEditable(false);
        getContentPane().add(txt_global_chat, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 470, 230));

        txt_msg_global.setText("Selam");
        getContentPane().add(txt_msg_global, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 470, 30));

        list_all_users.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list_all_users);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 170, 240));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("GLOBAL CHAT");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        btn_dc.setText("Disconnect");
        btn_dc.setEnabled(false);
        btn_dc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dcActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dc, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 180, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Online Users");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed

        Client.Start("127.0.0.1", 2000);
        btn_connect.setEnabled(false);
        txt_myusername.setEnabled(false);
        btn_send_message.setEnabled(true);
        btn_dc.setEnabled(true);
    }//GEN-LAST:event_btn_connectActionPerformed

    private void btn_send_messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_send_messageActionPerformed

        //Global metin mesajı gönderme işlemi
        Message msg = new Message(Message.Message_Type.Text);
        String x = txt_myusername.getText() + ": " + txt_msg_global.getText();
        msg.content = x;
        Client.Send(msg);
        txt_msg_global.setText("");

    }//GEN-LAST:event_btn_send_messageActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //Eğer bağlıysa, form X ile kapanırken de Disconnect butonunda yapılan işlemleri yapar
        if (Client.socket != null) {
            Message msg = new Message(Message.Message_Type.Disconnect);
            String x = txt_myusername.getText();
            msg.content = x;
            Client.Send(msg);

            Client.Stop();
        }

    }//GEN-LAST:event_formWindowClosing

    private void btn_dcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dcActionPerformed
        // TODO add your handling code here:
        // Disconnect butonuna basıldığında
        // Server'a ayrılacağı bildirisini yapar.
        // Server onu kullanıcı listesinden çıkartır ve threadlerini durdurur.
        Message msg = new Message(Message.Message_Type.Disconnect);
        String x = txt_myusername.getText();
        msg.content = x;
        Client.Send(msg);

        Client.Stop();
        btn_dc.setEnabled(false);
        btn_send_message.setEnabled(false);
        btn_connect.setEnabled(true);
        txt_myusername.setEnabled(true);
    }//GEN-LAST:event_btn_dcActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_connect;
    private javax.swing.JButton btn_dc;
    public javax.swing.JButton btn_send_message;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> list_all_users;
    private javax.swing.JPanel pnl_gamer1;
    public java.awt.TextArea txt_global_chat;
    private javax.swing.JTextField txt_msg_global;
    public javax.swing.JTextField txt_myusername;
    // End of variables declaration//GEN-END:variables
}
