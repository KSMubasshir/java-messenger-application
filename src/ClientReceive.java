package Messenger;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ClientReceive implements Runnable {

    DatagramSocket sock;
    DatagramPacket pack;
    private final Thread thr;
    InetAddress ip;
    String username, server_ip, server_name;
    String  listening_port;

    public ClientReceive(String username, String listening_port, String server_ip, String server_name) {
        this.username = username;
        this.listening_port = listening_port;
        this.server_ip = server_ip;
        this.server_name = server_name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void receive() throws SocketException, IOException {
        byte data[] = new byte[1000];
        pack = new DatagramPacket(data, data.length);
       // sock = new DatagramSocket(Integer.parseInt(listeing_port));
        sock = new DatagramSocket(Integer.parseInt(listening_port));
        sock.receive(pack);
        String str =  new String(pack.getData());
        StringTokenizer s = new StringTokenizer(str, "/");
        String[] msg = new String[4];
        int i = 0;
        while (s.hasMoreTokens()) {
            msg[i] = s.nextToken();
            i++;
        }
        if (!msg[0].equals(server_name)) {
            sock.close();
        } else if (!msg[1].equals(username)) {
            sock.close();
        } else {
            System.out.println(msg[2] + " says:" + msg[3]);
        }
        sock.close();
    }

    @Override
    public void run() {

        while (true) {
            try {
                receive();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
