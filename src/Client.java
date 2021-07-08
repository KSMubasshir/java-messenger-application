package Messenger;

import java.net.*;
import Messenger.ClientReceive;
import Messenger.SendClient;

public class Client{

    String username, server_ip, server_name;
    String listening_port;
    DatagramPacket pack;
    DatagramSocket sock;

    Client(String username, String listening_port, String server_ip, String server_name) throws Exception {
        this.username = username;
        this.listening_port = listening_port;
        this.server_ip = server_ip;
        this.server_name = server_name;
        
        String str;
        str =server_name + "/" + server_name+"/"+username + "/"+listening_port;
        byte data[] = str.getBytes();
        pack = new DatagramPacket(data, data.length);
        //InetAddress add = InetAddress.getByName(server_ip);
        InetAddress add = InetAddress.getLocalHost();
        
        pack.setAddress(add);
        pack.setPort(5000);
        sock = new DatagramSocket();
        sock.send(pack);
        sock.close(); 
        
        SendClient sendclient=new SendClient(username, listening_port, server_ip, server_name); 
        ClientReceive clientreceive=new ClientReceive(username, listening_port, server_ip, server_name);
        
    }


    public static void main(String[] args) throws Exception {
       //Client client= new Client(args[0], args[1], args[2], args[3]);
       Client client= new Client("Fawaz","11235", "127.0.0.1", "CSE108_NetworkingAssignmentServer");
    }
}
