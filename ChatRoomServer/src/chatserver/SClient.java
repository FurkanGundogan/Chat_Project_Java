/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import chatmsg.Message;
import chatmsg.PrivateMsg;
import static chatmsg.Message.Message_Type.Selected;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INSECT
 */
public class SClient {

    int id;
    public String name = "NoName";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    //clientten gelenleri dinleme threadi
    Listen listenThread;
    //cilent eşleştirme thredi
    PairingThread pairThread;
    //rakip client
    SClient rival;
    //eşleşme durumu
    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);

    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //client dinleme threadi
    //her clientin ayrı bir dinleme thredi var
    class Listen extends Thread {

        SClient TheClient;

        //thread nesne alması için yapıcı metod
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı olduğu sürece dönsün
            while (TheClient.soket.isConnected()) {
                try {
                    //mesajı bekleyen kod satırı
                    Message received = (Message) (TheClient.sInput.readObject());
                    //mesaj gelirse bu satıra geçer
                    //mesaj tipine göre işlemlere ayır
                    switch (received.type) {
                        case Name:
                            // User ilk girdigindeki mesaj
                            // User adı kontrollü bir şekilde belirlenir.
                            String uname = received.content.toString();
                            uname = Server.checkName(TheClient, uname);
                            TheClient.name = uname;
                            System.out.println(TheClient.name + "is connected.");
                            Server.sendUpdateUserList();
                            Server.SendAllRooms();
                            TheClient.pairThread.start();
                            break;
                        case Disconnect:
                            // Bir user ayrılırken gönderdiği mesaj
                            // Bu mesajla diğer kullanıcıların listesinden çıkartılır.
                            Server.Clients.remove(TheClient);
                            Server.sendUpdateUserList();
                            TheClient.listenThread.stop();
                            TheClient.pairThread.stop();
                            TheClient.soket.close();
                            break;
                        case Text:
                            //gelen global mesaj metni tüm kullanıcılara gider
                            Server.sendGlobal(received);
                            break;
                        case PrivateMsg:
                            Server.sendPrivate(received);
                            break;
                        case CreateNewRoom:
                            Server.addNewRoom(received);
                            break; 
                        case RequestJoinRoom:
                            Server.ControlRoomJoin(received);
                            break;
                        case RoomMSG:
                            Server.SendRoomMSG(received);
                            break;
                        case RoomUserLeft:
                            Server.SendUserLeftRoom(received);
                            break;
                        case FileTransfer:
                            Server.SendReceivedFile(received);
                            break;
                        case Selected:
                            Server.Send(TheClient.rival, received);
                            break;
                        case Bitis:
                            break;

                    }

                } catch (EOFException ex) {
                    System.out.println("hata");
                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);
                }
            }

        }
    }

    //eşleştirme threadi
    //her clientin ayrı bir eşleştirme thredi var
    class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı ve eşleşmemiş olduğu durumda dön
            while (TheClient.soket.isConnected()) {

            }
        }
    }

}
