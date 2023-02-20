
package cn.edu.guet.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderFile {
    public static void main(String[] args) {
       // firereadline();
        firereadch();
    }
    static  void firereadline(){
        FileReader fr;
        BufferedReader bufferedReader=null;
        try {
            fr=new FileReader("C:\\Users\\asus\\Desktop\\Markdown\\Java\\Java高级教程.md");
            bufferedReader=new BufferedReader(fr);
            String line;
            while (((line =bufferedReader.readLine()) !=null)) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭I/O流，释放资源
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void firereadch(){
        try {
            FileReader fr=new FileReader("C:\\Users\\asus\\Desktop\\Markdown\\Java\\Java高级教程.md");
            int a=0;
            int b=fr.read();
            int c=fr.read();
            int d=fr.read();
            int f=fr.read();
            int g=fr.read();
            int h=fr.read();
            int i=fr.read();
            int j=fr.read();
            System.out.println(g);
            System.out.println(h);
            while (((a =fr.read()) !=-1)) {
//                System.out.print((char)a);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
