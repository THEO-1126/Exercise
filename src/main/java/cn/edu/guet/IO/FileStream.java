package cn.edu.guet.IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStream {
    public static void main(String[] args) {
        FileInputStream fis=null;
        FileOutputStream fos=null;
        byte by[]=new byte[1602800]; // 40秒，（每秒40070个字节）
        System.out.println(by);
        try {
            fis=new FileInputStream("C:\\Users\\asus\\Desktop\\秒针.mp3");
            fis.skip(4407700); // 从输入流中跳过并丢弃 n 个字节的数据
            fis.read(by);
            // 开始写操作
            fos=new FileOutputStream("ring.mp3");
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
