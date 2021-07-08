package Messenger;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SendClient implements Runnable {

    DatagramPacket pack;
    DatagramSocket sock;
    private final Thread thr;
    String clientname, message, username,  server_ip, server_name, friendname;
    String listening_port;

    public SendClient(String clientname, String listening_port, String server_ip, String server_name) {
        this.clientname = clientname;
        this.listening_port = listening_port;
        this.server_ip = server_ip;
        this.server_name = server_name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void send() throws UnknownHostException, SocketException, IOException {
        Scanner input = new Scanner(System.in);
        while (true) {
            String s = input.nextLine();
            StringTokenizer name_msg = new StringTokenizer(s, "$");
            String[] msg = new String[2];
            int i = 0;
            while (name_msg.hasMoreTokens()) {
                msg[i] = name_msg.nextToken();
                i++;
            }
            friendname = msg[0];
            message = msg[1];

            String str = server_name + "/" + friendname + "/" + clientname + "/" + message;
            byte data[] = str.getBytes();

            pack = new DatagramPacket(data, data.length);
            //InetAddress add = InetAddress.getByName(server_ip);
            InetAddress add = InetAddress.getLocalHost();
            pack.setAddress(add);
            pack.setPort(5000);
            sock = new DatagramSocket();
            sock.send(pack);
            sock.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                send();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
