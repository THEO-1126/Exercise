package cn.edu.guet.chat.Task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client1 {
    public static void main(String[] args){
        client1 a=new client1();
    }
    public client1(){
        try{
            Socket s=new Socket("localhost",9999);//进入端口，前面是服务器的Ip，自己电脑一般是127.0.0.1,后面的是端口，与服务器对应
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);//IO流发送
            InputStreamReader isr=new InputStreamReader(System.in);//从控制台输入数据
            BufferedReader br=new BufferedReader(isr);//存入缓存区
            InputStream isr2=s.getInputStream();//读取数据
            BufferedReader br2=new BufferedReader(new InputStreamReader(isr2));//存到缓存区
            while(true){
                System.out.println("我发言: ");
                String fasong=br.readLine();//获取数据
                pw.println(fasong);
                String jieshou=br2.readLine();
                System.out.println("服务器说："+jieshou);//输出数据
            }
        }catch(Exception e){

        }
    }
}