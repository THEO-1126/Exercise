package cn.edu.guet.Socket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        DataOutputStream dos=null;
        File file=null;
        try {
            file=new File("D:\\program\\LanQiaoJava\\Exercise\\ring.mp3");
            long fileLength=file.length();

            ServerSocket serverSocket=new ServerSocket(8888);
            Socket socket=serverSocket.accept();
            OutputStream os=socket.getOutputStream();
            dos=new DataOutputStream(os);
            dos.writeLong(fileLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
