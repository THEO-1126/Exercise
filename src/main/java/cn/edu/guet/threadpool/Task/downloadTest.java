package cn.edu.guet.threadpool.Task;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class downloadTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,
                5, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10));
        String url ="https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection[] urlConnection = {null};
        try {
            urlConnection[0] = (HttpURLConnection) new URL(url)
                    .openConnection();// ��������
            int fileSize = urlConnection[0].getContentLength();
            System.out.println("Ҫ���ص��ļ���С��" + fileSize);

            int index = url.lastIndexOf("/");
            String fileName = url.substring(index + 1);
            // ����ÿ���߳����صĴ�С
            int threadNum=5;
            int size = fileSize / threadNum;
            // ����size�ó�ÿ���߳����ص���ʼλ�úͽ���λ��
            int startPos = 0;
            int endPos = 0;
            /*
            ѭ���ó�ÿ���̵߳��������䣬Ȼ������һ���߳�ȥ����
             */
            for (int i = 0; i < 5; i++) {
                // ����ǵ�1���̣߳���ʼλ��0
                if (i == 0) {
                    startPos = 0;
                } else {
                    startPos = (i * size) + 1;
                }
                // ��������һ���̣߳�����Ҫ������λ�ã�ֱ�Ӹ�ֵ0
                if (i != 5 - 1) {
                    endPos = (i + 1) * size;
                } else {
                    endPos = 0;
                }
                String newfilename=fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe";
                System.out.println("��" + (i + 1) + "���߳��������䣺" + startPos + "--" + endPos);
                int finalEndPos = endPos;
                int finalStartPos = startPos;
                int finalI = i;
                Runnable r = ()->{
                    System.out.println("��ʼ���ص�" + finalI + "���ļ�: "+newfilename);
                    InputStream in = null;
                    FileOutputStream fos = null;
                    try {
                        urlConnection[0] = (HttpURLConnection) new URL(url)
                                .openConnection();// ��������
                        if (finalEndPos == 0) {
                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-");
                        } else {
                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-" + finalEndPos);
                        }
                        in = urlConnection[0].getInputStream();//�������ص�����
                        byte buff[] = new byte[8192];
                        int a = 0;
                        fos = new FileOutputStream(newfilename);
                        while ((a = in.read(buff)) != -1) {
                            fos.write(buff,0,a);
                        }
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //������1
                        System.out.println("��" + finalI + "���ļ����ؽ���");
                        executor.shutdown();
                    }
                };
                executor.execute(r);
            }
            //�ȴ������߳��������
            while (true){
                if (executor.isTerminated()){
                    break;
                }
            }
            System.out.println("�����߳��������");

            //�ļ��ϲ�
            File file[] = new File[threadNum];
            for(int i=0;i<threadNum;i++){
                file[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
            }
            mercg(file,fileName);
            System.out.println("�ļ�����ɺϲ�====");
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