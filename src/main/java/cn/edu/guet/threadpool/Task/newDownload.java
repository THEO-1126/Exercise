package cn.edu.guet.threadpool.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// 利用多线程下载多个文件并合并
public class newDownload {
    public static void main(String[] args) {
        int threadNum = 5;// 默认线程数量5
        String url = "https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection urlConnection = null;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,
                5, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10)); // 属于有界队列，需要有容量
        List<Future> futureList=new ArrayList<>();
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// 创建连接
            int fileSize = urlConnection.getContentLength();
            System.out.println("要下载的文件大小：" + fileSize);
            int index = url.lastIndexOf("/");
            String fileName = url.substring(index + 1);
            // 计算每个线程下载的大小
            int size = fileSize / threadNum;
            // 根据size得出每个线程下载的起始位置和结束位置
            int startPos = 0;
            int endPos = 0;
            /*
            循环得出每个线程的下载区间，然后启动一个线程去下载
             */
            for (int i = 0; i < threadNum; i++) {
                // 如果是第1个线程，起始位置0
                if (i == 0) {
                    startPos = 0;
                } else {
                    startPos = (i * size) + 1;
                }
                // 如果是最后一个线程，不需要给结束位置，直接赋值0
                if (i != threadNum - 1) {
                    endPos = (i + 1) * size;
                } else {
                    endPos = 0;
                }
                System.out.println("第" + (i + 1) + "个线程下载区间：" + startPos + "--" + endPos);
                Future future=threadPoolExecutor.submit(new Downloader1(startPos, endPos, url, fileName,i));// 启动线程
                futureList.add(future);
            }
            futureList.forEach(future -> {
                try {
                    future.get();//会堵塞直到线程下载完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("线程已全部下载完毕！");
            // 合并文件
            File files[] = new File[threadNum];
            for(int i=0;i<threadNum;i++){
                files[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
            }
            mercg(files,fileName);  // 合并文件，合并后的文件名为fileName
            threadPoolExecutor.shutdown();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 合并文件
    public static void mercg(File files[],String fileName) throws IOException{
        System.out.println("正在合并文件....");
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
            //把已经合并过的文件删除
            f.delete();
        }
        fos.close();
        System.out.println("合并文件完成！");
    }
}

class Downloader1 implements Runnable{
    int startPos;
    int endPos;
    String url;
    String fileName;
    int threadId;
    Downloader1(int startPos, int endPos, String url, String fileName, int threadId) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.url = url;
        this.fileName = fileName;
        this.threadId=threadId;
    }
    public void run() {
        HttpURLConnection urlConnection;
        InputStream in;
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// 创建连接
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
            System.out.println("第"+threadId+"个线程下载完毕！");
        }
    }
}