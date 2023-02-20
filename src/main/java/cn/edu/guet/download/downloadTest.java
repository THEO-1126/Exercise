package cn.edu.guet.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 多线程下载
public class downloadTest {
    public static void main(String[] args) {
        String url="https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe";
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// 创建连接
            int fileSize = urlConnection.getContentLength();
            System.out.println("要下载的文件大小：" + fileSize);
            int index = url.lastIndexOf("/");
            System.out.println("最后一个斜杠的索引：" + index);
            String fileName = url.substring(index + 1);
            System.out.println("文件名：" + fileName);

            // 计算线程的下载
            int threadNum = 5;// 设置线程个数
            int size=fileSize/threadNum;// 根据size得出每个线程的起始位置和结束位置
            int startPos;
            int endPos;
            // 循环得出每个线程下载区间，启动线程去下载
            for (int i=0;i<threadNum;i++){
                // i表示第i个线程
                if(i==0){
                    startPos=0;
                }else{
                    startPos=i*size+1;
                }
                if(i!=threadNum-1){ // 没到最后一个线程
                    endPos=(i+1)*size;//结束位置
                }else{
                    endPos=0; // 如果是最后一个线程，不需要给出结束位置
                }
                System.out.println("第"+(i+1)+"个线程下载区间："+startPos+"--"+endPos);
                new Download(startPos,endPos,url,fileName).start();// 启动线程
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
            randomAccessFile.seek(startPos);// 设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作。
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// 创建连接
            if(endPos==0){
                urlConnection.setRequestProperty("Range","bytes="+startPos+"-");
            }else {
                urlConnection.setRequestProperty("Range","bytes="+startPos+"-"+endPos);//设置请求属性
            }
            in =urlConnection.getInputStream();// 得到输入流
            byte[] buff =new byte[8192];// 一次读8k
            // 边读边写，设置好每个线程读的区间
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
