package cn.edu.guet.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// ���߳�����
public class downloadTest {
    public static void main(String[] args) {
        String url="https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe";
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// ��������
            int fileSize = urlConnection.getContentLength();
            System.out.println("Ҫ���ص��ļ���С��" + fileSize);
            int index = url.lastIndexOf("/");
            System.out.println("���һ��б�ܵ�������" + index);
            String fileName = url.substring(index + 1);
            System.out.println("�ļ�����" + fileName);

            // �����̵߳�����
            int threadNum = 5;// �����̸߳���
            int size=fileSize/threadNum;// ����size�ó�ÿ���̵߳���ʼλ�úͽ���λ��
            int startPos;
            int endPos;
            // ѭ���ó�ÿ���߳��������䣬�����߳�ȥ����
            for (int i=0;i<threadNum;i++){
                // i��ʾ��i���߳�
                if(i==0){
                    startPos=0;
                }else{
                    startPos=i*size+1;
                }
                if(i!=threadNum-1){ // û�����һ���߳�
                    endPos=(i+1)*size;//����λ��
                }else{
                    endPos=0; // ��������һ���̣߳�����Ҫ��������λ��
                }
                System.out.println("��"+(i+1)+"���߳��������䣺"+startPos+"--"+endPos);
                new Download(startPos,endPos,url,fileName).start();// �����߳�
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Download extends Thread{
    int startPos;
    int endPos;
    String url;
    String filename;
    Download(int startPos,int endPos,String url,String filename){
        this.startPos=startPos;
        this.endPos=endPos;
        this.url=url;
        this.filename=filename;
    }

    @Override
    public void run() {
        RandomAccessFile randomAccessFile=null;
        HttpURLConnection urlConnection = null;
        InputStream in=null;
        try {
            randomAccessFile=new RandomAccessFile(filename,"rwd");
            randomAccessFile.seek(startPos);// ���õ����ļ���ͷ���������ļ�ָ��ƫ�������ڸ�λ�÷�����һ����ȡ��д�������
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// ��������
            if(endPos==0){
                urlConnection.setRequestProperty("Range","bytes="+startPos+"-");
            }else {
                urlConnection.setRequestProperty("Range","bytes="+startPos+"-"+endPos);//������������
            }
            in =urlConnection.getInputStream();// �õ�������
            byte[] buff =new byte[8192];// һ�ζ�8k
            // �߶���д�����ú�ÿ���̶߳�������
            int a=0;
            while((a=in.read(buff))!=-1){
                randomAccessFile.write(buff,0,a);
            }
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
