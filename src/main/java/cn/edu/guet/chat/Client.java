package cn.edu.guet.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        try {
            socket = new Socket("10.34.45.157", 8888);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            // 读一行
            br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("收到消息：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
