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
        String mp3Path="C:\\Users\\asus\\Desktop\\����.mp3";
        File f=new File(mp3Path);
        System.out.println("�ļ���С"+f.length());
        ringmp3(mp3Path,1,41);
    }
    static void ringmp3(String mp3Path,int startSecond,int endSecond){
        // ������ʼ�ͽ���������
        FileInputStream fis=null;
        FileOutputStream fos=null;
        byte[] bytes;
        try {
            fis=new FileInputStream("C:\\Users\\asus\\Desktop\\����.mp3");
            int bps=getBytes(fis)/getDuration(mp3Path); // ����ÿ����ֽ���
            bytes=new byte[(int) ((endSecond-startSecond)*bps)]; // ������������� ��ռ���ֽ���
            fis.skip(((long) startSecond *bps)); // �������������������� n ���ֽڵ�����
            fis.read(bytes);
            fos=new FileOutputStream("newring.mp3");//���
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
    // ��ȡmp3����ʱ��
    static int getDuration(String mp3Path){
        int length = 0;
        try {
//            MP3File mp3File = (MP3File) AudioFileIO.read(new File(mp3Path));
//            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
//            length = audioHeader.getTrackLength();//��λΪ��
            return length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
    // ����ÿ�ֽ�����
    static int  getBytes(FileInputStream fis){
        int bytes = 0;
        try {
            bytes=fis.available();//�ֽ�����
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
