package cn.edu.guet.Socket;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Author 李冰冰
 * @Date 2022/11/18 15:59
 * @Version 1.0
 */
public class newClient {
    public static void main(String[] args) {

        //localhost：本机
        Socket socket = null;
        DataInputStream dis = null;

        FileOutputStream fos = null;
        try {
            socket = new Socket("10.33.59.139", 8888);
            InputStream is = socket.getInputStream();
            dis = new DataInputStream(is);
            String fileName = dis.readUTF();
            System.out.println("文件名字：" + fileName);

            fos = new FileOutputStream(fileName);

            int a = 0;
            byte buff[] = new byte[1024];
            while ((a = dis.read(buff)) != -1) {
                fos.write(buff, 0, a);
            }
            fos.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
