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
            // 匿名内部类 获取socket变量不需要传参
            Thread sendMessageThread =new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            OutputStream os = socket.getOutputStream();
                            out = new PrintWriter(os);
                            out.println("你好");
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
