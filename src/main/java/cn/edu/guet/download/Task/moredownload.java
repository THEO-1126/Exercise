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
        int threadNum = 5;// 默认线程数量5
//        if (args.length == 0) {
//            System.out.println("运行失败，请给出网址");
//        }
        String url = "https://xiazai.softbanks.net/sem/idm640_bdsem.exe";
        HttpURLConnection urlConnection = null;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,
                5, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10)); // 属于有界队列，需要有容量
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
                //new Downloader(startPos, endPos, url, fileName,i).start();
                threadPoolExecutor.execute(new Downloader(startPos, endPos, url, fileName,i));
                /*
                把下载的文件命名为
                QQ_6.8.8.6244[0].exe
                QQ_6.8.8.6244[1].exe
                QQ_6.8.8.6244[2].exe
                QQ_6.8.8.6244[3].exe
                QQ_6.8.8.6244[4].exe
                1、以上所有线程都执行完毕后，仅仅是在硬盘上创建了5个文件
                2、所以最后，还需要写I/O代码把5个文件依次合并，但是合并之前，你必须确定《所有线程都已经下载完毕》
                3、把原来的5个文件删除
                 */
            }
            threadPoolExecutor.shutdown();
            while(true){
                if (threadPoolExecutor.isTerminated()){
                    System.out.println("线程结束了！");
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
    // 合并文件
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
            //把已经合并过的文件删除
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
                //如果是第1个线程，则保存为：QQ_6.8.8.6244[0].dmg
                //如果是第2个线程，则保存为：QQ_6.8.8.6244[1].dmg
                //....
                ////如果是最后线程，则保存为：QQ_6.8.8.6244[4].dmg
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("第"+threadId+"个线程下载完毕！");
        }
    }
}