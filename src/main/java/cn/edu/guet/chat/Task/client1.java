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
            Socket s=new Socket("localhost",9999);//����˿ڣ�ǰ���Ƿ�������Ip���Լ�����һ����127.0.0.1,������Ƕ˿ڣ����������Ӧ
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);//IO������
            InputStreamReader isr=new InputStreamReader(System.in);//�ӿ���̨��������
            BufferedReader br=new BufferedReader(isr);//���뻺����
            InputStream isr2=s.getInputStream();//��ȡ����
            BufferedReader br2=new BufferedReader(new InputStreamReader(isr2));//�浽������
            while(true){
                System.out.println("�ҷ���: ");
                String fasong=br.readLine();//��ȡ����
                pw.println(fasong);
                String jieshou=br2.readLine();
                System.out.println("������˵��"+jieshou);//�������
            }
        }catch(Exception e){

        }
    }
}