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
            // ��ȡ������ ��ȡ���������͵���Ϣ
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            // ��ȡ�����  �������������Ϣ
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
            bw=new BufferedWriter(osw);
            Scanner scanner=new Scanner(System.in);//��������
            while(true){
                // ��������
                String sendMessage=scanner.next();
                bw.write(sendMessage);
                bw.newLine();
                bw.flush();
                // ��������
                String message = br.readLine();
                System.out.println("����ˣ�" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
