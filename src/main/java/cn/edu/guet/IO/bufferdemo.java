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
            // �����ڻ����8192ʱ������flsuh���Զ�д��Ӳ��
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
