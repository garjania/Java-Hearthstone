package MultiPlayer;

import UI.GeneralClasses.GameResources;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Connector extends Thread{
    private String ip;
    private int port;
    private ServerSocket server;
    private Socket client;
    private ConnectionType type;

    public Connector(String ip, int port) {
        type = ConnectionType.CLIENT;
        this.ip = ip;
        this.port = port;
        try {
            server = new ServerSocket(port + 1);
            client = new Socket(ip, port);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connector(int port) {
        type = ConnectionType.HOST;
        this.port = port;
        try {
            server = new ServerSocket(port);
            client = server.accept();
            this.start();
            ip = client.getInetAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data){

        OutputStream os = null;
        try {
            if(type == ConnectionType.HOST)
                os = new Socket(ip , port + 1).getOutputStream();
            else
                os = new Socket(ip , port).getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataOutputStream dos = new DataOutputStream(os);
        String input = "";
        try {
            dos.writeBytes(data);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveData(){
        while (true) {
            InputStream is = null;
            try {
                is = server.accept().getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String input = "";
            Scanner scanner = new Scanner(is);

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                input += s;
            }
            if(!input.isEmpty()){
                if(input.startsWith("#"))
                    DataTransfer.getPlayer(input.replace("#", ""));
                else
                    DataTransfer.sendToTranslate(input);
            }
        }
    }

    public ConnectionType getType() {
        return type;
    }

    @Override
    public void run(){
        receiveData();
    }
}

enum ConnectionType{
    HOST,
    CLIENT
}
