package cn.edu.guet.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket ss=null;
        try {
            ss=new ServerSocket(8888);
            Socket socket=ss.accept();
            // �����ڲ��� ��ȡsocket��������Ҫ����
            Thread sendMessageThread =new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            OutputStream os = socket.getOutputStream();
                            out = new PrintWriter(os);
                            out.println("���");
                            out.flush();
                            socket.getOutputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            );
            sendMessageThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
