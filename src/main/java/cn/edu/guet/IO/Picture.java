package cn.edu.guet.IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Picture {
    public static void main(String[] args) {
        String filename="D:\\program\\LanQiaoJava\\Exercise\\src\\main\\resources\\pic\\test.jpg";
        FileInputStream fis=null;
        FileOutputStream fos=null;
        byte by[]=new byte[10240];
        try {
            fis=new FileInputStream(filename);
            fis.skip(141097); // 从输入流中跳过并丢弃 n 个字节的数据
            fis.read(by);
            // 开始写操作
            fos=new FileOutputStream("newpic.jpg");
            fos.write(by);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
