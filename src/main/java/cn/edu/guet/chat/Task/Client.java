package cn.edu.guet.chat.Task;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        BufferedWriter bw=null;
        try {
            socket = new Socket("localhost", 8888);
            // 获取输入流
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            // 获取输出流
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
            bw=new BufferedWriter(osw);
            // 发送数据
            bw.write("Hello ！客户端");
            bw.newLine();
            bw.flush();
            // 接收数据
            String message = br.readLine();
            System.out.println("收到消息：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
