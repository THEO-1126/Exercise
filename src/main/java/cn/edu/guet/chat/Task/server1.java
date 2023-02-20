package cn.edu.guet.chat.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server1 {
    public server1(){try{
        ServerSocket ss=new ServerSocket(9999);//设置服务器的端口
        System.out.println("服务器正在监听");
        Socket s=ss.accept();//只有服务器被客户连接之后，才会执行后面的语句
        InputStreamReader isr=new InputStreamReader(s.getInputStream());//接收客户端的数据
        BufferedReader br=new BufferedReader(isr);//存入缓存
        PrintWriter pw=new PrintWriter(s.getOutputStream(),true);//向客户端传数据
        InputStreamReader isr2=new InputStreamReader(System.in);//向客户端发送的数据是啥，由控制台输入
        BufferedReader br2=new BufferedReader(isr2);//将控制台输入的数据放入缓存
        while(true){
            String xinxi=br.readLine();//把缓存的数据读出来
            System.out.println("客户端说："+xinxi);//打印出来
            System.out.println("我说:");
            String fasong=br2.readLine();//把缓存的数据读出来
            pw.println(fasong);//传给客户端
        }

    }catch(Exception e){


    }}
    public static void main(String args[]){
        server1 a=new server1();
    }
}
