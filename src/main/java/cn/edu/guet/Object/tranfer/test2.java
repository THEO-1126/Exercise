package cn.edu.guet.Object.tranfer;


import java.io.*;
// ����ض��� ���ı������Ŀ�ĵ�
// ���󣺰�println�����ݣ������һ���ļ��������ǿ���̨
public class test2 {
    public static void main(String[] args) {
        PrintStream out=System.out;
        try {
            FileWriter fw=new FileWriter("test2");
            OutputStreamWriter osw=new OutputStreamWriter(out);
            PrintWriter pw=new PrintWriter(fw);
            pw.println("��������");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
