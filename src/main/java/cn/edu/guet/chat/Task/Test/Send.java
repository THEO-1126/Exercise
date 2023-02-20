package cn.edu.guet.chat.Task.Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Send implements Runnable {
    private String address;
    private int port;
    private String send;
    private Socket socket;

    public Send(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = null;
    }

    @Override
    public void run() {
        try {
            while (socket == null) {
                socket = new Socket(address, port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            BufferedWriter bw=null;
            try {
                // 获取输出流  用于向客户端传数据
                OutputStream os=socket.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os);
                bw =new BufferedWriter(osw);
                Scanner scanner=new Scanner(System.in);//键盘输入
                send = scanner.nextLine();
                if (send != null) {
                    bw.write(send);
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}