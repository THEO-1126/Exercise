package cn.edu.guet.Socket.Task;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Author �����
 * @Date 2022/11/18 15:59
 * @Version 1.0
 * �ͻ��ˣ������ļ�
 */
public class Client {
    public static void main(String[] args) {

        Socket socket = null;
        DataInputStream dis = null;

        FileOutputStream fos = null;
        try {
            socket = new Socket("10.34.67.42", 8888);
            InputStream is = socket.getInputStream();
            dis = new DataInputStream(is);
            Long fileSize=dis.readLong(); // �ļ���С
            String fileName = dis.readUTF();
            System.out.println("�ļ���СΪ��"+fileSize);
            System.out.println("�ļ����֣�" + fileName);
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