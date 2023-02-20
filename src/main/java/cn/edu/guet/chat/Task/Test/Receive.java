package cn.edu.guet.chat.Task.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;

    public Receive(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            System.out.println("连接成功");
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            BufferedReader br = null;
            try {
                // 获取输入流 用于获取客户端发送的消息
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                br = new BufferedReader(isr);
                String receiver = br.readLine();
                System.out.println(receiver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}