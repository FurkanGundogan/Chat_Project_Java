/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import chatmsg.Message;
import chatmsg.PrivateMsg;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author INSECT
 */
//client gelişini dinleme threadi
class ServerThread extends Thread {

    public void run() {
        //server kapanana kadar dinle
        while (!Server.serverSocket.isClosed()) {
            try {
                Server.Display("Client Bekleniyor...");
                // clienti bekleyen satır
                //bir client gelene kadar bekler
                Socket clientSocket = Server.serverSocket.accept();
                //client gelirse bu satıra geçer
                Server.Display("Client Geldi...");
                //gelen client soketinden bir sclient nesnesi oluştur
                //bir adet id de kendimiz verdik
                SClient nclient = new SClient(clientSocket, Server.IdClient);

                Server.IdClient++;
                //clienti listeye ekle.
                Server.Clients.add(nclient);
                //client mesaj dinlemesini başlat
                nclient.listenThread.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    //server soketi eklemeliyiz
    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    // Serverın dileyeceği port
    public static int port = 0;
    //Serverı sürekli dinlemede tutacak thread nesnesi
    public static ServerThread runThread;
    //public static PairingThread pairThread;

    public static ArrayList<SClient> Clients = new ArrayList<>();

    //semafor nesnesi
    public static Semaphore pairTwo = new Semaphore(1, true);

    // başlaşmak için sadece port numarası veriyoruz
    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    public static void sendUpdateUserList() {
        // Yeni user katıldığında, tüm userlara gönderilecek mesaj. Bu mesaj sonucu kullancıılardaki user listesi güncellenir.
        DefaultListModel model = new DefaultListModel();
        for (SClient c : Clients) {
            model.addElement(c.name);
        }
        Message msg = new Message(Message.Message_Type.UpdateUserList);
        msg.content = model;
        for (SClient c : Clients) {
            Server.Send(c, msg);
        }

    }

    public static String checkName(SClient c, String name) {
        // Yeni katılan user için isim kontrolü
        // İsim daha önce kayıtlıysa, isim(1), o da kayıtlıysa isim(1)(1) olarak değiştirir.

        boolean sendRename = false;

        for (int i = 0; i < Clients.size(); i++) {
            String cname = Clients.get(i).name;
            if (cname.toLowerCase().equals(name.toLowerCase())) {
                name += "(1)";
                i = 0;
                sendRename = true;
            }
        }
        // Daha sonra değiştirdiği bilgiyi geri Client'a da gönderir.
        if (sendRename) {
            Message msg2 = new Message(Message.Message_Type.Rename);
            msg2.content = name;
            Server.Send(c, msg2);
        }
        return name;
    }

    public static void sendGlobal(Message msg) {
        // Metni tüm kullanıcılara mesaj olarak gönderir.
        for (SClient c : Clients) {
            Server.Send(c, msg);
        }
    }
    
    public static void sendPrivate(Message msg){
        PrivateMsg pmsg=(PrivateMsg)msg.content;
        
        for (SClient c : Clients) {
            if(c.name.equals(pmsg.getTarget())){
                Server.Send(c, msg);
                System.out.println(c.name);
                break; 
            }
                 
        }
        
        
        
    }

    // serverdan clietlara mesaj gönderme
    //clieti alıyor ve mesaj olluyor
    public static void Send(SClient cl, Message msg) {

        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
