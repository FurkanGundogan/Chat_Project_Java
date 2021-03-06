/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmsg;

import chatclient.Client;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

/**
 *
 * @author Furu
 */
public class Room extends javax.swing.JFrame {
    public DefaultListModel dlmChat;
    public DefaultListModel dlmparticipants;
    public String roomName="";
    public String username="";
    public File myFile;
    // oda uzerinden gonderilecek dosya
    /**
     * Creates new form Room
     */
    public Room() {
        initComponents();
        dlmChat=new DefaultListModel();
        dlmparticipants=new DefaultListModel();
        list_room_chat.setModel(dlmChat);
        list_participants.setModel(dlmparticipants);
        lblfname.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_msg_room = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_room_chat = new javax.swing.JList<>();
        btn_msg_send_room = new javax.swing.JButton();
        lbl_roomName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_participants = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        btn_LeaveRoom = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblfname = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txt_msg_room.setText("Selam");

        list_room_chat.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list_room_chat);

        btn_msg_send_room.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_msg_send_room.setForeground(new java.awt.Color(0, 204, 51));
        btn_msg_send_room.setText(">");
        btn_msg_send_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_msg_send_roomActionPerformed(evt);
            }
        });

        lbl_roomName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_roomName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_roomName.setText("Room Name");

        list_participants.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(list_participants);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Participants");

        btn_LeaveRoom.setText("Leave");
        btn_LeaveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LeaveRoomActionPerformed(evt);
            }
        });

        jButton1.setText("Choose File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblfname.setText("fname");

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_roomName, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_msg_room, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_msg_send_room, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblfname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_LeaveRoom)
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_roomName)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_msg_room, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_msg_send_room, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblfname)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_LeaveRoom)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updateParticipants(ArrayList<String> users){
        // gelen mesaj dogrultusunda listeyi bastan dizer
        // alttaki fonk icin bir nevi duzeltme
        dlmparticipants.removeAllElements();
        for (String user : users) {
            dlmparticipants.addElement(user);
            
       }
        list_participants.setModel(dlmparticipants);
    }
    public void updateRoomChat(String msg){
        // gelen mesajla listeye yeni user ekler
        dlmChat.addElement(msg);
        list_room_chat.setModel(dlmChat);
    }
    
    public static String getExt(String fname){
        int i = fname.lastIndexOf(".");
        if(i>0){
            return fname.substring(i, 1);
        }else{
            return "ext_not_found";
        }
    }
    private void btn_msg_send_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_msg_send_roomActionPerformed
        // oda ici mesaj
        // odaadi-gonderen seklinde servere gider
       String chatmsg=txt_msg_room.getText();
      
       
       Message msg = new Message(Message.Message_Type.RoomMSG);
       ArrayList<String> elements= new ArrayList<String>();
       elements.add(lbl_roomName.getText());
       elements.add(username+": "+chatmsg);
       msg.content=elements;
       Client.Send(msg);
       
    }//GEN-LAST:event_btn_msg_send_roomActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_formWindowClosing

    private void btn_LeaveRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LeaveRoomActionPerformed
        // TODO add your handling code here:
        // odadan ayrilirken, servere bunu bildirir
        // daha sonra diger kullanicilardan bu kisi, listelerinden kaldirilir
        dlmparticipants=new DefaultListModel();
        list_participants.setModel(dlmparticipants);
        Message msg = new Message(Message.Message_Type.RoomUserLeft);
        ArrayList<String> elements= new ArrayList<String>();
        elements.add(username);
        elements.add(lbl_roomName.getText());
        msg.content=elements;
        
        Client.Send(msg);
        this.dispose();
    }//GEN-LAST:event_btn_LeaveRoomActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //filechooser buttonu
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("Select File");
        if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            myFile=chooser.getSelectedFile();
            lblfname.setText(myFile.getName());
            lblfname.setVisible(true);
                    
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // dosya gonderme icerik+gonderen+odaadi
        if(myFile==null){
            lblfname.setText("Please select a file.");
            lblfname.setVisible(true);
        }else{
            try {
                Message msg = new Message(Message.Message_Type.FileTransfer);
                ArrayList items= new ArrayList<>();
                items.add(roomName);
                items.add(username);
                items.add(myFile.getName());
                byte[] content=Files.readAllBytes(myFile.toPath());
                items.add(content);
                
                msg.content=items;
                Client.Send(msg);
                myFile=null;
                lblfname.setText("");
                lblfname.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    
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
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Room().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_LeaveRoom;
    public javax.swing.JButton btn_msg_send_room;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lbl_roomName;
    private javax.swing.JLabel lblfname;
    public javax.swing.JList<String> list_participants;
    public javax.swing.JList<String> list_room_chat;
    private javax.swing.JTextField txt_msg_room;
    // End of variables declaration//GEN-END:variables
}
