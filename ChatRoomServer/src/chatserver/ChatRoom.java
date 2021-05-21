/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Furu
 */
public class ChatRoom {
    String name;
    String password;
    ArrayList<String> userListOfRoom = new ArrayList<>();
    DefaultListModel<String> messagesListModel = new DefaultListModel<>();

    public ChatRoom(String name, String password, String firstUser) {
        this.name = name;
        this.password = password;
        userListOfRoom.add(firstUser);
    }
}
