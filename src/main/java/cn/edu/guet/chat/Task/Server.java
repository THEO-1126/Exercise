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
            // �����ڲ��� ��ȡsocket��������Ҫ����
            Thread sendMessageThread =new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = null;
                            BufferedWriter bw=null;
                            try {
                                // ��ȡ������
                                InputStream is = socket.getInputStream();
                                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                                br = new BufferedReader(isr);
                                // ��ȡ�����
                                OutputStream os=socket.getOutputStream();
                                OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
                                bw=new BufferedWriter(osw);
                                // ��������
                                bw.write("Hello ������÷������");
                                bw.newLine();
                                bw.flush();
                                // ��������
                                String message = br.readLine();
                                System.out.println("�յ���Ϣ��" + message);
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
