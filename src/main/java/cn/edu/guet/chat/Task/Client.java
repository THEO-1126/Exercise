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
            // ��ȡ������
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            // ��ȡ�����
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
            bw=new BufferedWriter(osw);
            // ��������
            bw.write("Hello ���ͻ���");
            bw.newLine();
            bw.flush();
            // ��������
            String message = br.readLine();
            System.out.println("�յ���Ϣ��" + message);
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
