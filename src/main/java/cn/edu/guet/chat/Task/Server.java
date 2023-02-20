package cn.edu.guet.chat.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket ss=null;
        try {
            ss=new ServerSocket(8888);
            Socket socket=ss.accept();
            // 匿名内部类 获取socket变量不需要传参
            Thread sendMessageThread =new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = null;
                            BufferedWriter bw=null;
                            try {
                                // 获取输入流
                                InputStream is = socket.getInputStream();
                                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                                br = new BufferedReader(isr);
                                // 获取输出流
                                OutputStream os=socket.getOutputStream();
                                OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
                                bw=new BufferedWriter(osw);
                                // 发送数据
                                bw.write("Hello ！梁金梅服务器");
                                bw.newLine();
                                bw.flush();
                                // 接收数据
                                String message = br.readLine();
                                System.out.println("收到消息：" + message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            sendMessageThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
