package cn.edu.guet.Object.tranfer;


import java.io.*;
// 输出重定向 ：改变输出的目的地
// 需求：把println的内容，输出到一个文件，而不是控制台
public class test2 {
    public static void main(String[] args) {
        PrintStream out=System.out;
        try {
            FileWriter fw=new FileWriter("test2");
            OutputStreamWriter osw=new OutputStreamWriter(out);
            PrintWriter pw=new PrintWriter(fw);
            pw.println("广西桂林");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
