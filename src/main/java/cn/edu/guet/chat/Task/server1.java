package cn.edu.guet.chat.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server1 {
    public server1(){try{
        ServerSocket ss=new ServerSocket(9999);//���÷������Ķ˿�
        System.out.println("���������ڼ���");
        Socket s=ss.accept();//ֻ�з��������ͻ�����֮�󣬲Ż�ִ�к�������
        InputStreamReader isr=new InputStreamReader(s.getInputStream());//���տͻ��˵�����
        BufferedReader br=new BufferedReader(isr);//���뻺��
        PrintWriter pw=new PrintWriter(s.getOutputStream(),true);//��ͻ��˴�����
        InputStreamReader isr2=new InputStreamReader(System.in);//��ͻ��˷��͵�������ɶ���ɿ���̨����
        BufferedReader br2=new BufferedReader(isr2);//������̨��������ݷ��뻺��
        while(true){
            String xinxi=br.readLine();//�ѻ�������ݶ�����
            System.out.println("�ͻ���˵��"+xinxi);//��ӡ����
            System.out.println("��˵:");
            String fasong=br2.readLine();//�ѻ�������ݶ�����
            pw.println(fasong);//�����ͻ���
        }

    }catch(Exception e){


    }}
    public static void main(String args[]){
        server1 a=new server1();
    }
}
