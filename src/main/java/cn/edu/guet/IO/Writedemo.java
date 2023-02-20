package cn.edu.guet.IO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Writedemo {
    public static void main(String[] args) {
        FileWriter fw=null;
        try {
            fw=new FileWriter("user.txt");
            Scanner scanner=new Scanner(System.in);//键盘输入
            System.out.println("请从键盘输入姓名：");
            String namestr=scanner.next(); // str是键盘输入的内容 ，next是阻塞函数，等着输入
            fw.write(namestr); //写入后，并没有存入user.txt文件
            fw.write(10); //写入换行
            System.out.println("请从键盘输入学号：");
            String stunostr=scanner.next(); // str是键盘输入的内容 ，next是阻塞函数，等着输入
            fw.write(stunostr); //写入后，并没有存入user.txt文件
            fw.write(10); //写入换行
            System.out.println("输入完毕");
            fw.flush();//刷新该流的缓冲,把缓冲区里的内容存入user.txt文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fw.close(); //close前会flush
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
