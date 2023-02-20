package cn.edu.guet.IO;
import java.io.File;

public class Filedemo {
    public static void main(String[] args) {
        File filedir = new File("D:\\program\\LanQiaoJava");
        filemethod(filedir,1);
    }
    static void filemethod(File filedir,int tab){
        if(filedir.isFile()) {}
        else{
            File[] files = filedir.listFiles();
            for (File file : files) {
                for (int j = 0; j < tab; j++) {
                    System.out.print("|---");
                }
                System.out.println(file.getName());
                if (file.isDirectory()) {
                    filemethod(file, tab++);
                }
            }
                //是一个目录对象
        }
    }
}

