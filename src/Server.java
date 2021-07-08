package Messenger;

import java.util.Hashtable;
import Messenger.ServerThread;

public class Server {

    String servername;
    public Hashtable<String, String> table;

    Server(String servername) {
        this.servername = servername;
        table =new Hashtable<String,String>();
        ServerThread serverthread = new ServerThread(table,servername);
    }
    
    public static void main(String[] args) {
        Server server = new Server("CSE108_NetworkingAssignmentServer");    
    }
    
}
