package cn.edu.guet.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author liwei
 * @Date 2022/11/21 15:07
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        // ����ͨ�� java Test ����
        // ���磺java Test https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe
        String url;
        if(args.length==0){
            System.out.println("����ʧ�ܣ��������ַ");
            return;
        }
        else{
            url = args[0];
        }
        HttpURLConnection urlConnection = null;
        RandomAccessFile randomAccessFile = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// ��������
            int fileSize = urlConnection.getContentLength();
            System.out.println("Ҫ���ص��ļ���С��" + fileSize);


            int index = url.lastIndexOf("/");
            System.out.println("���һ��б�ܵ�������" + index);
            String fileName = url.substring(index + 1);
            System.out.println("�ļ�����" + fileName);

            randomAccessFile = new RandomAccessFile(fileName, "rwd");
            randomAccessFile.setLength(fileSize);// �������ʹӵͣ�int 32��--->�ߣ�long 64�����Զ�ת��

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
