package cn.edu.guet.Object.Random;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomDemo {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile=new RandomAccessFile("start","rwd");
            randomAccessFile.setLength(971711);
            randomAccessFile.seek(9205); // position位置
            FileInputStream fis=new FileInputStream("D:\\program\\LanQiaoJava\\Exercise\\src\\main\\resources\\Testpic\\4afef73d5ec8b96778f360327f940a21.jpg");
            byte buff[]=new byte[1024];
            int a=0;
            while((a=fis.read())!=-1){
                randomAccessFile.write(buff,0,a);
            }
            fis.close();
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
