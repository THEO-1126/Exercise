package cn.edu.guet.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author liwei
 * @Date 2022/11/21 15:07
 * @Version 1.0
 */
public class Test2 {
    public static void main(String[] args) {

        int threadNum = 5;// Ĭ���߳�����5

//        if (args.length == 0) {
//            System.out.println("����ʧ�ܣ��������ַ");
//            return;
//        }

//        String url = args[0];
        String url="https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe";
        HttpURLConnection urlConnection = null;
        RandomAccessFile randomAccessFile = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// ��������
            int fileSize = urlConnection.getContentLength();
            System.out.println("Ҫ���ص��ļ���С��" + fileSize);


            int index = url.lastIndexOf("/");
            System.out.println("���һ��б�ܵ�������" + index);
            String fileName = url.substring(index + 1);
            System.out.println("�ļ�����" + fileName);

            randomAccessFile = new RandomAccessFile(fileName, "rwd");
            randomAccessFile.setLength(fileSize);// �������ʹӵͣ�int 32��--->�ߣ�long 64�����Զ�ת��

            // �൱������һ��300L������Ͱ

            // ����ÿ���߳����صĴ�С
            int size = fileSize / threadNum;
            // ����size�ó�ÿ���߳����ص���ʼλ�úͽ���λ��
            int startPos = 0;
            int endPos = 0;
            /*
            ѭ���ó�ÿ���̵߳��������䣬Ȼ������һ���߳�ȥ����
             */
            for (int i = 0; i < threadNum; i++) {
                // ����ǵ�1���̣߳���ʼλ��0
                if (i == 0) {
                    startPos = 0;
                } else {
                    startPos = (i * size) + 1;
                }
                // ��������һ���̣߳�����Ҫ������λ�ã�ֱ�Ӹ�ֵ0
                if (i != threadNum - 1) {
                    endPos = (i + 1) * size;
                } else {
                    endPos = 0;
                }
                System.out.println("��" + (i + 1) + "���߳��������䣺" + startPos + "--" + endPos);
                new Downloader(startPos, endPos, url, fileName).start();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Downloader extends Thread {
    int startPos;
    int endPos;
    String url;
    String fileName;

    Downloader(int startPos, int endPos, String url, String fileName) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.url = url;
        this.fileName = fileName;
    }

    public void run() {
        RandomAccessFile randomAccessFile = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rwd");
            randomAccessFile.seek(startPos);

            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// ��������
            // �����
            if (endPos == 0) {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-");
            } else {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            }
            in = urlConnection.getInputStream();

            byte buff[] = new byte[8192];
            /*
            �߶���Ҫ���ú�ÿ���̶߳�������
            ��д��RandomAccessFile
             */
            int a = 0;
            while ((a = in.read(buff)) != -1) {
                // д�����
                randomAccessFile.write(buff, 0, a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
