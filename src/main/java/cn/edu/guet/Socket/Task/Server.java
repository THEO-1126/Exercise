package cn.edu.guet.Socket.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author �����
 * @Date 2022/11/18 15:59
 * @Version 1.0
 * ����ˣ������ļ�
 */

public class Server implements Runnable {
    File file;
    Socket socket;
    byte buff[];

    Server(File file,Socket socket,byte buff[]){
        this.file=file;
        this.socket=socket;
        this.buff=buff;
    }

    public static void main(String[] args) {
        try {
            String filePath="D:\\program\\LanQiaoJava\\Exercise\\src\\main\\resources\\sourceTest\\������_��ϲ���յ����ļ�.mp4";
            File file = new File(filePath);
            ServerSocket ss = new ServerSocket(8888);
            byte buff[] = new byte[1024];
            int i=0;
            while (true) {
                Socket socket = ss.accept();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
                System.out.println("��"+(++i)+"�����û��������� ʱ�䣺"+formatter.format(calendar.getTime()));
                Thread thread = new Thread(new Server(file,socket,buff)); // �������󣬻��Զ�����run����
                thread.start();// �����߳�
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        FileInputStream fis = null;
        DataOutputStream dos = null;
        OutputStream os=null;
        try {
            int a=0;
            fis = new FileInputStream(file);
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);
            String fileName = file.getName();
            long fileSize = file.length();
            dos.writeLong(fileSize);
            dos.writeUTF(fileName);
            while ((a = fis.read(buff)) != -1) {
                dos.write(buff, 0, a);
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
}
