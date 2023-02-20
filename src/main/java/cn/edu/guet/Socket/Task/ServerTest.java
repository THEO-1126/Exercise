package cn.edu.guet.Socket.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerTest implements Runnable {
    Socket socket;
    ServerTest(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        DataOutputStream dos = null;
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File("D:\\program\\LanQiaoJava\\Exercise\\src\\main\\resources\\sourceTest\\奥特曼_恭喜你收到了文件.mp4");
            long filesize = file.length();
            fis = new FileInputStream(file);
            int a;
            OutputStream os = socket.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeLong(filesize);
            dos.writeUTF(file.getName());
            while ((a = fis.read()) != -1) {
                dos.write(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) {
        //   byte buff[]=new byte[1024];
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            while(true) {
                Socket socket = ss.accept();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
                System.out.println("新用户进来啦！ 时间："+formatter.format(calendar.getTime()));
                ServerTest server =new ServerTest(socket);
                Thread thread = new Thread(server);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



