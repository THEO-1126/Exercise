package cn.edu.guet.IO;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class bufferdemo {
    public static void main(String[] args) {
        FileOutputStream fos=null;
        BufferedOutputStream bos=null;
        try {
            fos=new FileOutputStream("buffer.txt");
            // 当大于或等于8192时，不用flsuh就自动写入硬盘
            for(int i=0;i<8193;i++){
                bos.write(62);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
