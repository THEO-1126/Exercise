package cn.edu.guet.download.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

/**
 * @Author liwei
 * @Date 2022/11/21 15:07
 * @Version 1.0
 */
public class moredownload {
    public static void main(String[] args) {
        int threadNum = 5;// Ĭ���߳�����5
//        if (args.length == 0) {
//            System.out.println("����ʧ�ܣ��������ַ");
//        }
        String url = "https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection urlConnection = null;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,
                5, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10)); // �����н���У���Ҫ������
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
                //new Downloader(startPos, endPos, url, fileName,i).start();
                threadPoolExecutor.execute(new Downloader(startPos, endPos, url, fileName,i));
                /*
                �����ص��ļ�����Ϊ
                QQ_6.8.8.6244[0].exe
                QQ_6.8.8.6244[1].exe
                QQ_6.8.8.6244[2].exe
                QQ_6.8.8.6244[3].exe
                QQ_6.8.8.6244[4].exe
                1�����������̶߳�ִ����Ϻ󣬽�������Ӳ���ϴ�����5���ļ�
                2��������󣬻���ҪдI/O�����5���ļ����κϲ������Ǻϲ�֮ǰ�������ȷ���������̶߳��Ѿ�������ϡ�
                3����ԭ����5���ļ�ɾ��
                 */
            }
            threadPoolExecutor.shutdown();
            while(true){
                if (threadPoolExecutor.isTerminated()){
                    System.out.println("�߳̽����ˣ�");
                    File file[] = new File[threadNum];
                    for(int i=0;i<threadNum;i++){
                        file[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
                    }
                    mercg(file,fileName);
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // �ϲ��ļ�
    public static void mercg(File file[],String filename) throws IOException{
        FileInputStream fis = null;
        FileOutputStream fos = new FileOutputStream (filename);
        for(File f: file){
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
    }
}

class Downloader extends Thread {
    int startPos;
    int endPos;
    String url;
    String fileName;
    int threadId;
    Downloader(int startPos, int endPos, String url, String fileName,int threadId) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.url = url;
        this.fileName = fileName;
        this.threadId=threadId;
    }

    public void run() {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
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
                //����ǵ�1���̣߳��򱣴�Ϊ��QQ_6.8.8.6244[0].dmg
                //����ǵ�2���̣߳��򱣴�Ϊ��QQ_6.8.8.6244[1].dmg
                //....
                ////���������̣߳��򱣴�Ϊ��QQ_6.8.8.6244[4].dmg
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("��"+threadId+"���߳�������ϣ�");
        }
    }
}