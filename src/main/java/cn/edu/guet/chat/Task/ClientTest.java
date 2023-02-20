package cn.edu.guet.chat.Task;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        BufferedWriter bw=null;
        try {
            socket = new Socket("47.113.201.215", 6000);
            // 获取输入流 获取服务器发送的消息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            // 获取输出流  向服务器发送消息
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
            bw=new BufferedWriter(osw);
            Scanner scanner=new Scanner(System.in);//键盘输入
            while(true){
                // 发送数据
                String sendMessage=scanner.next();
                bw.write(sendMessage);
                bw.newLine();
                bw.flush();
                // 接收数据
                String message = br.readLine();
                System.out.println("服务端：" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
