package Messenger;

import java.io.IOException;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

    DatagramSocket sock;
    DatagramPacket pack;
    private final Thread thr;
    String server_name;
    public Hashtable<String, String> table;

    public ServerThread(Hashtable<String, String> table, String name) {
        this.table = table;
        this.server_name = name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void send_and_receive() throws SocketException, IOException {

        byte data[] = new byte[1000];
        pack = new DatagramPacket(data, data.length);
        try {
            sock = new DatagramSocket(5000);
        } catch (SocketException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sock.receive(pack);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = new String(pack.getData());
        StringTokenizer s = new StringTokenizer(str, "/");
        String[] msg = new String[4];
        int i = 0;

        while (s.hasMoreTokens()) {
            msg[i] = s.nextToken();
            i++;
        }

        if (!msg[0].equals(server_name)) {
            System.out.println("Warning: Server name mismatch. Message dropped.");
            sock.close();
        } else {
            if (msg[1].equals(server_name)) {
                String ip_port = pack.getAddress().toString() + "|" + msg[3];
                table.put(msg[2], ip_port);
            } else if (table.containsKey(msg[1])) {
                String ip_port = table.get(msg[1]);
                StringTokenizer st = new StringTokenizer(ip_port, "|");
                String ip, port;
                ip = st.nextToken();
                port = st.nextToken();
                int Port = Integer.parseInt(port);
                System.out.println(port + "  " + Port);
                //InetAddress add = InetAddress.getByName(ip);
                InetAddress add = InetAddress.getLocalHost();
                pack.setAddress(add);
                pack.setPort(Port);
                sock = new DatagramSocket();
                sock.send(pack);
                sock.close();
            } else {
                System.out.println("Warning: Unknown recipient. Message dropped.");
                sock.close();
            }
            sock.close();
        }
        sock.close();
        System.out.println(table);
    }

    @Override
    public void run() {
        while (true) {
            try {
                send_and_receive();
            } catch (NumberFormatException | IOException e) {
                System.out.println(e);
            }
        }
    }
}
