package cn.edu.guet.IO;

import java.io.*;

public class FileCopy {
    public static void main(String[] args) {
        // ����ļ��ĸ���
        FileReader fr;
        FileWriter fw=null;
        BufferedReader bufferedReader;
        try {
            fr=new FileReader("C:\\Users\\asus\\Desktop\\Markdown\\Java\\Java�߼��̳�.md");
            fw=new FileWriter("copyfile.txt");
            bufferedReader=new BufferedReader(fr);
            String str=null;
            while (((str =bufferedReader.readLine()) !=null)) {
                fw.write(str);;
                fw.write(13);
            }
            fw.flush();//ˢ�¸����Ļ���
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
