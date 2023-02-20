package cn.edu.guet.exercise;

import java.io.*;

/*
    要合并的文件名要自动用程序过滤出来（显示某个目录下的某类型文件，例如，扩展名为jpeg的所有文件）
    过滤出文件名字后，保存早img.txt中，然后使用BufferedReader按行读取来依次合并
 */
public class Mergefiles {
    public static void main(String[] args) {
        getpicname();
        readpic();
    }
    // 将 img.txt中存储的图片 读取并进行合并
    static void readpic(){
        FileReader fr=null;
        BufferedReader bufferedReader=null;
        FileOutputStream fos=null;
        FileInputStream fis=null;
        int temp=0;
        try {
            fr=new FileReader("img.txt"); //读取文件
            bufferedReader=new BufferedReader(fr);
            fos=new FileOutputStream("Mergepic.jpg"); // 合并后的图片名称
            String str =null;
            //合并图片文件
            while((str=bufferedReader.readLine())!=null){
                System.out.println(str);
                fis=new FileInputStream(str);
                while((temp=fis.read())!=-1){
                    fos.write(temp);
                }
            }
        } catch (IOException e) {
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
    // 获取某个目录中 后缀是.pic 的文件名字
    static void getpicname(){
        File filedir=new File("D:\\program\\LanQiaoJava\\Exercise\\src\\main\\resources\\Testpic");
        File[] files =filedir.listFiles();
        FileWriter fw=null;
        try {
            fw=new FileWriter("img.txt");
            for(File f:files){
                if(f.isFile()&&f.getName().endsWith(".jpg")){
                    fw.write(String.valueOf(f)); // 写入图片文件的路径（绝对路径）
                    fw.write(10);//写入换行
                }
            }
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
