package cn.edu.guet.download.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
// ���ö��߳����ض���ļ����ϲ�
public class task1 {
    public static void main(String[] args) {
        int threadNum = 5;// Ĭ���߳�����5
        CountDownLatch countDownLatch=new CountDownLatch(threadNum);
        String url = "https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// ��������
            int fileSize = urlConnection.getContentLength();
            System.out.println("Ҫ���ص��ļ���С��" + fileSize);
            int index = url.lastIndexOf("/");
            String fileName = url.substring(index + 1);
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
                new Downloader1(startPos, endPos, url, fileName,countDownLatch,i).start();// �����߳�
            }
            try {
                countDownLatch.await();// �ȴ���ǰ�߳�������ϣ�main�߳�
                System.out.println("�߳�������ϣ�");
                File files[] = new File[threadNum];
                for(int i=0;i<threadNum;i++){
                    files[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
                }
                mercg(files,fileName);  // �ϲ��ļ����ϲ�����ļ���ΪfileName
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // �ϲ��ļ�
    public static void mercg(File files[],String fileName) throws IOException{
        System.out.println("���ںϲ��ļ�....");
        FileInputStream fis;
        FileOutputStream fos = new FileOutputStream (fileName);
        for(File f: files){
            fis = new FileInputStream (f);
            byte[] buff = new byte[8192];
            int a=0;
            while ((a=fis.read (buff)) != -1) {
                fos.write(buff, 0, a);
            }
            fis.close();
            //���Ѿ��ϲ������ļ�ɾ��
            f.delete();
        }
        fos.close();
        System.out.println("�ϲ��ļ���ɣ�");
    }
}

class Downloader1 extends Thread {
    int startPos;
    int endPos;
    String url;
    String fileName;
    CountDownLatch countDownLatch;
    int threadId;
    Downloader1(int startPos, int endPos, String url, String fileName,CountDownLatch countDownLatch,int threadId) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.url = url;
        this.fileName = fileName;
        this.countDownLatch=countDownLatch;
        this.threadId=threadId;
    }

    public void run() {
        HttpURLConnection urlConnection;
        InputStream in;
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// ��������
            if (endPos == 0) {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-");
            } else {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            }
            in = urlConnection.getInputStream();
            byte buff[] = new byte[8192];
            int a = 0;
            String newfilename=fileName.substring(0,fileName.lastIndexOf("."))+"["+threadId+"].exe";
            FileOutputStream fos = new FileOutputStream (newfilename);
            while ((a = in.read(buff)) != -1) {
                fos.write (buff,0,a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown(); // �ݼ��������ļ�����������������㣬���ͷ����еȴ����߳�
            System.out.println("��"+countDownLatch.getCount()+"���߳����سɹ���");
        }
    }
}