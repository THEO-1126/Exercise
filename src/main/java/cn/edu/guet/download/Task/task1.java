package cn.edu.guet.download.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
// 利用多线程下载多个文件并合并
public class task1 {
    public static void main(String[] args) {
        int threadNum = 5;// 默认线程数量5
        CountDownLatch countDownLatch=new CountDownLatch(threadNum);
        String url = "https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection urlConnection = null;
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
                new Downloader1(startPos, endPos, url, fileName,countDownLatch,i).start();// 启动线程
            }
            try {
                countDownLatch.await();// 等待当前线程下载完毕，main线程
                System.out.println("线程下载完毕！");
                File files[] = new File[threadNum];
                for(int i=0;i<threadNum;i++){
                    files[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
                }
                mercg(files,fileName);  // 合并文件，合并后的文件名为fileName
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            countDownLatch.countDown(); // 递减锁存器的计数，如果计数到达零，则释放所有等待的线程
            System.out.println("第"+countDownLatch.getCount()+"个线程下载成功！");
        }
    }
}