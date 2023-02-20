package cn.edu.guet.chat.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTest {
    public static void main(String[] args) {
        ServerSocket ss=null;
        try {
            ss=new ServerSocket(9999);
            Socket socket=ss.accept();
            // 匿名内部类 获取socket变量不需要传参
            Thread MessageThread =new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = null;
                            BufferedWriter bw=null;
                            try {
                                // 获取输入流 用于获取客户端发送的消息
                                InputStream is = socket.getInputStream();
                                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                                br = new BufferedReader(isr);
                                // 获取输出流  用于向客户端传数据
                                OutputStream os=socket.getOutputStream();
                                OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
                                bw =new BufferedWriter(osw);
                                Scanner scanner=new Scanner(System.in);//键盘输入
                                bw.write("连接成功！");
                                bw.newLine();
                                bw.flush();
                                while(true){
                                    // 发送信息
                                    String sendMessage=scanner.next();
                                    bw.write(sendMessage);
                                    bw.newLine();
                                    bw.flush();
                                    // 接收数据
                                    String message = br.readLine();
                                    System.out.println("客户端：" + message);
                                }
                            } catch (IOException e) {
                                System.out.println("Connection Close");
                                // e.printStackTrace();
                            }finally {
                                try {
                                    bw.close();
                                    br.close();
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
            MessageThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
