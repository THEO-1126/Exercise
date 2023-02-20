package cn.edu.guet.IO.dataStream;

import java.io.*;

public class Server {
    public static void main(String[] args) {
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        BufferedOutputStream bos=null;
        try {
            fos=new FileOutputStream("student.txt");
            dos=new DataOutputStream(fos);
            bos=new BufferedOutputStream(dos);
            bos.write(88);//存进内存
            bos.write(123);
            bos.flush();//由于是buffer类，需要flush才能存进硬盘
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
