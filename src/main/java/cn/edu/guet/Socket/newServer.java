package cn.edu.guet.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传文件给Client
 *
 * @Author 李冰冰
 * @Date 2022/11/18 15:53
 * @Version 1.0
 */
public class newServer {
    public static void main(String[] args) {
        File file = null;
        try {
            file = new File("C:\\Users\\asus\\Desktop\\要发送的文件.mp");
            long fileSize = file.length();
            String fileName = file.getName();
            ServerSocket ss = new ServerSocket(8888);
            byte buff[] = new byte[1024];
            while (true) {
                Socket socket = ss.accept();
                System.out.println("新用户进来了");
                FileInputStream fis = new FileInputStream(file);
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                new Thread(
                        // lambda表达式
                        () -> {
                            int a1 = 0;
                            try {
                                dos.writeUTF(fileName);
                                while ((a1 = fis.read(buff)) != -1) {
                                    dos.write(buff, 0, a1);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fis.close();
                                    dos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
