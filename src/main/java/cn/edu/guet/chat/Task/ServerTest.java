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
            // �����ڲ��� ��ȡsocket��������Ҫ����
            Thread MessageThread =new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = null;
                            BufferedWriter bw=null;
                            try {
                                // ��ȡ������ ���ڻ�ȡ�ͻ��˷��͵���Ϣ
                                InputStream is = socket.getInputStream();
                                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                                br = new BufferedReader(isr);
                                // ��ȡ�����  ������ͻ��˴�����
                                OutputStream os=socket.getOutputStream();
                                OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
                                bw =new BufferedWriter(osw);
                                Scanner scanner=new Scanner(System.in);//��������
                                bw.write("���ӳɹ���");
                                bw.newLine();
                                bw.flush();
                                while(true){
                                    // ������Ϣ
                                    String sendMessage=scanner.next();
                                    bw.write(sendMessage);
                                    bw.newLine();
                                    bw.flush();
                                    // ��������
                                    String message = br.readLine();
                                    System.out.println("�ͻ��ˣ�" + message);
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
