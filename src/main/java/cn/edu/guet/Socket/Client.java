package cn.edu.guet.Socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //localhost：本机
        Socket socket = null;
        DataInputStream dis = null;
        try {
            socket = new Socket("10.33.59.139", 8888);//填写的ip地址

            InputStream is = socket.getInputStream();
            dis=new DataInputStream(is);
            long fileSize=dis.readLong();
            System.out.println("文件大小："+fileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
