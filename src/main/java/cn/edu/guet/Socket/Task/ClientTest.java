package cn.edu.guet.Socket.Task;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) {
        Socket socket=null;
        DataInputStream dis=null;
        FileOutputStream fos=null;
        try {
            //loaclhost
            socket = new Socket("10.34.22.149",8888);
            InputStream is=socket.getInputStream();
            dis= new DataInputStream(is);
            String fileName=dis.readUTF();
            System.out.println("文件名字为：" + fileName);

            fos=new FileOutputStream(fileName);
            int a;
            byte buff[]=new byte[1024];
            while((a=dis.read()) != -1){
                fos.write(a);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
