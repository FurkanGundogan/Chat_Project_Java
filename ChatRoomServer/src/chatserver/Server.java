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
import java.util.HashMap;
import java.util.Hashtable;
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
    public static ArrayList<ChatRoom> chatRooms = new ArrayList<>();
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

    public static void sendPrivate(Message msg) {
        PrivateMsg pmsg = (PrivateMsg) msg.content;

        for (SClient c : Clients) {
            if (c.name.equals(pmsg.getTarget())) {
                Server.Send(c, msg);
                System.out.println(c.name);
                break;
            }

        }

    }

    public static void addNewRoom(Message msg) {
        String roomname = ((ArrayList<String>) msg.content).get(0);
        String roompass = ((ArrayList<String>) msg.content).get(1);
        String firstUser = ((ArrayList<String>) msg.content).get(2);

        boolean containControl = false;
        for (ChatRoom c : chatRooms) {
            if (c.name.equals(roomname)) {
                containControl = true;
                break;
            }
        }

        if (!containControl) {
            ChatRoom r = new ChatRoom(roomname, roompass, firstUser);

            chatRooms.add(r);
            SendAllRooms();
            SendCreationCompleteMsg(firstUser, r);

        } else {
            Message newmsg = new Message(Message.Message_Type.RoomNameExist);
            String text = "Room name is already exist";
            newmsg.content = text;

            for (SClient c : Clients) {
                if (c.name.equals(firstUser)) {
                    Server.Send(c, newmsg);
                    break;
                }
            }

        }
    }

    public static void SendCreationCompleteMsg(String name, ChatRoom r) {
        Message newmsg = new Message(Message.Message_Type.CompleteCreation);
        HashMap<String, ArrayList<String>> cnt = new HashMap<String, ArrayList<String>>();
        
        cnt.put(r.name, r.userListOfRoom);
        newmsg.content = cnt;
        for (SClient c : Clients) {
            if (c.name.equals(name)) {
                Server.Send(c, newmsg);
                break;
            }

        }
    }

    public static void SendAllRooms() {

        ArrayList<String> allroomnames = new ArrayList<>();

        for (ChatRoom c : chatRooms) {
            allroomnames.add(c.name);
        }

        Message newmsg = new Message(Message.Message_Type.SendAllRooms);
        newmsg.content = allroomnames;
        for (SClient c : Clients) {
            Server.Send(c, newmsg);
        }
    }

    public static void ControlRoomJoin(Message msg) {
        ArrayList<String> info = (ArrayList<String>) msg.content;
        String room = info.get(0);
        String pass = info.get(1);
        String user = info.get(2);

        int roomIndex = findRoom(room);

        System.out.println("girilen:" + pass + " sifre:" + chatRooms.get(roomIndex).password);
        if (chatRooms.get(roomIndex).password.equals(pass)) {
            //accept join

            Server.SendAcceptRoomJoin(user, chatRooms.get(roomIndex).name);
            //Son giren kullanici icin, eski uyeleri getirililmesi
            Server.SendLastUserListToJoined(user, chatRooms.get(roomIndex).name, chatRooms.get(roomIndex).userListOfRoom);

            chatRooms.get(roomIndex).userListOfRoom.add(user);
            Server.SendUpdateChatRoomUsers(chatRooms.get(roomIndex), user);

        } else {
            SendRejectRoomJoin(user);
        }

    }

    public static int findRoom(String roomName) {
        int index = -1;

        for (int i = 0; i < chatRooms.size(); i++) {
            if (chatRooms.get(i).name.equals(roomName)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public static void SendAcceptRoomJoin(String user, String roomname) {

        for (SClient c : Clients) {
            if (c.name.equals(user)) {
                Message newmsg = new Message(Message.Message_Type.PasswordAccepted);
                ArrayList<String> items = new ArrayList<String>();
                items.add(user);
                items.add(roomname);
                newmsg.content = items;
                Server.Send(c, newmsg);
                break;
            }
        }

    }

    public static void SendRejectRoomJoin(String user) {
        for (SClient c : Clients) {
            if (c.name.equals(user)) {
                Message newmsg = new Message(Message.Message_Type.PasswordRejected);
                String text = "Wrong password";
                newmsg.content = text;
                Server.Send(c, newmsg);
                break;
            }
        }
    }

    public static void SendUpdateChatRoomUsers(ChatRoom cr, String newUser) {
        for (SClient c : Clients) {
            if (cr.userListOfRoom.contains(c.name)) {
                Message newmsg = new Message(Message.Message_Type.UpdateChatRoomUserList);
                ArrayList<String> elements = new ArrayList<>();
                elements.add(cr.name);
                elements.add(newUser);
                // Burada bir sorun olusmustu
                // ArrayList burada doğru şekilde bulunmasına rağmen hedefe eksik gidiyordu
                // Tüm liste yerine, son eklenen kişiyi güncelleyerek sorunu çözdüm.

                newmsg.content = elements;
                Server.Send(c, newmsg);
            }
        }
    }

    public static void SendRoomMSG(Message msg) {

        ArrayList<String> elements = (ArrayList<String>) msg.content;
        String room = elements.get(0);
        String text = elements.get(1);

        for (ChatRoom cr : chatRooms) {
            if (cr.name.equals(room)) {

                for (SClient c : Clients) {

                    if (cr.userListOfRoom.contains(c.name)) {
                        Message newmsg = new Message(Message.Message_Type.RoomMSG);
                        newmsg.content = elements;
                        Server.Send(c, newmsg);
                    }
                }

                break;
            }

        }

    }

    public static void SendLastUserListToJoined(String user, String roomname, ArrayList<String> userList) {
        
        for (SClient c : Clients) {
            if (c.name.equals(user)) {
                Message newmsg = new Message(Message.Message_Type.GetOldRoomUsers);
                ArrayList elements = new ArrayList();
                elements.add(roomname);
                ArrayList<String>userListCopy=new ArrayList<String>();
                for (String u : userList) {
                    userListCopy.add(u);
                }
                elements.add(userListCopy);
                newmsg.content = elements;
                Server.Send(c, newmsg);
                break;
            }
        }
    }

    public static void SendUserLeftRoom(Message msg) {
        String uname = ((ArrayList<String>) msg.content).get(0);
         
        String room = ((ArrayList<String>) msg.content).get(1);
        int index=findRoom(room);
        chatRooms.get(index).userListOfRoom.remove(uname);
        ArrayList<String> arr=chatRooms.get(index).userListOfRoom;
        ArrayList<String> arr2= new ArrayList<String>();
        for (String el : arr) {
            arr2.add(el);
        }
        System.out.println("diziyeni: "+arr2);
        Message newmsg = new Message(Message.Message_Type.RoomUserLeft);
       
        
        ArrayList elements= new ArrayList<>();
        elements.add(room);
        elements.add(arr2);
        
        newmsg.content=elements;
        
        for (SClient c : Clients) {
            if(chatRooms.get(index).userListOfRoom.contains(c.name)){
                Server.Send(c, newmsg);
            }
        }
        
        for (SClient c : Clients) {
            if(c.name.equals(uname)){
                Message newmsg2 = new Message(Message.Message_Type.RemoveFromMyRoomList);
                newmsg2.content=room;
                Server.Send(c, newmsg2);
            }
        }
        
        
        

    }
    
    public static void SendReceivedFile(Message msg){
        ArrayList elements=(ArrayList)msg.content;
        int roomindex=findRoom(elements.get(0).toString());
        Message newmsg2 = new Message(Message.Message_Type.FileTransfer);
        newmsg2.content=elements;
        for (SClient clt : Clients) {
            if(chatRooms.get(roomindex).userListOfRoom.contains(clt.name)){
                Server.Send(clt, newmsg2);
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
