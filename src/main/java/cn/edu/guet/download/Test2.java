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

        int threadNum = 5;// 默认线程数量5

//        if (args.length == 0) {
//            System.out.println("运行失败，请给出网址");
//            return;
//        }

//        String url = args[0];
        String url="https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe";
        HttpURLConnection urlConnection = null;
        RandomAccessFile randomAccessFile = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();// 创建连接
            int fileSize = urlConnection.getContentLength();
            System.out.println("要下载的文件大小：" + fileSize);


            int index = url.lastIndexOf("/");
            System.out.println("最后一个斜杠的索引：" + index);
            String fileName = url.substring(index + 1);
            System.out.println("文件名：" + fileName);

            randomAccessFile = new RandomAccessFile(fileName, "rwd");
            randomAccessFile.setLength(fileSize);// 数据类型从低（int 32）--->高（long 64）会自动转换

            // 相当于买了一个300L的汽油桶

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
                    .openConnection();// 创建连接
            // 如果是
            if (endPos == 0) {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-");
            } else {
                urlConnection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            }
            in = urlConnection.getInputStream();

            byte buff[] = new byte[8192];
            /*
            边读：要设置好每个线程读的区间
            边写：RandomAccessFile
             */
            int a = 0;
            while ((a = in.read(buff)) != -1) {
                // 写入哪里？
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
