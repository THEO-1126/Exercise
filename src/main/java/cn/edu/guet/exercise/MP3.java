package cn.edu.guet.exercise;

//import org.jaudiotagger.audio.AudioFileIO;
//import org.jaudiotagger.audio.mp3.MP3AudioHeader;
//import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MP3 {
    public static void main(String[] args) {
        String mp3Path="C:\\Users\\asus\\Desktop\\秒针.mp3";
        File f=new File(mp3Path);
        System.out.println("文件大小"+f.length());
        ringmp3(mp3Path,1,41);
    }
    static void ringmp3(String mp3Path,int startSecond,int endSecond){
        // 给出开始和结束的秒数
        FileInputStream fis=null;
        FileOutputStream fos=null;
        byte[] bytes;
        try {
            fis=new FileInputStream("C:\\Users\\asus\\Desktop\\秒针.mp3");
            int bps=getBytes(fis)/getDuration(mp3Path); // 计算每秒的字节数
            bytes=new byte[(int) ((endSecond-startSecond)*bps)]; // 计算持续的秒数 所占的字节数
            fis.skip(((long) startSecond *bps)); // 从输入流中跳过并丢弃 n 个字节的数据
            fis.read(bytes);
            fos=new FileOutputStream("newring.mp3");//输出
            fos.write(bytes);
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
    // 获取mp3的总时长
    static int getDuration(String mp3Path){
        int length = 0;
        try {
//            MP3File mp3File = (MP3File) AudioFileIO.read(new File(mp3Path));
//            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
//            length = audioHeader.getTrackLength();//单位为秒
            return length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
    // 计算每字节总数
    static int  getBytes(FileInputStream fis){
        int bytes = 0;
        try {
            bytes=fis.available();//字节总数
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
