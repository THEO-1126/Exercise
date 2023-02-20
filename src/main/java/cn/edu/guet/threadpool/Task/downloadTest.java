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
                    .openConnection();// 创建连接
            int fileSize = urlConnection[0].getContentLength();
            System.out.println("要下载的文件大小：" + fileSize);

            int index = url.lastIndexOf("/");
            String fileName = url.substring(index + 1);
            // 计算每个线程下载的大小
            int threadNum=5;
            int size = fileSize / threadNum;
            // 根据size得出每个线程下载的起始位置和结束位置
            int startPos = 0;
            int endPos = 0;
            /*
            循环得出每个线程的下载区间，然后启动一个线程去下载
             */
            for (int i = 0; i < 5; i++) {
                // 如果是第1个线程，起始位置0
                if (i == 0) {
                    startPos = 0;
                } else {
                    startPos = (i * size) + 1;
                }
                // 如果是最后一个线程，不需要给结束位置，直接赋值0
                if (i != 5 - 1) {
                    endPos = (i + 1) * size;
                } else {
                    endPos = 0;
                }
                String newfilename=fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe";
                System.out.println("第" + (i + 1) + "个线程下载区间：" + startPos + "--" + endPos);
                int finalEndPos = endPos;
                int finalStartPos = startPos;
                int finalI = i;
                Runnable r = ()->{
                    System.out.println("开始下载第" + finalI + "个文件: "+newfilename);
                    InputStream in = null;
                    FileOutputStream fos = null;
                    try {
                        urlConnection[0] = (HttpURLConnection) new URL(url)
                                .openConnection();// 创建连接
                        if (finalEndPos == 0) {
                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-");
                        } else {
                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-" + finalEndPos);
                        }
                        in = urlConnection[0].getInputStream();//请求下载的区间
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
                        //计数减1
                        System.out.println("第" + finalI + "个文件下载结束");
                        executor.shutdown();
                    }
                };
                executor.execute(r);
            }
            //等待所有线程下载完毕
            while (true){
                if (executor.isTerminated()){
                    break;
                }
            }
            System.out.println("所有线程下载完毕");

            //文件合并
            File file[] = new File[threadNum];
            for(int i=0;i<threadNum;i++){
                file[i]=new File("D:\\program\\LanQiaoJava\\Exercise\\"+fileName.substring(0,fileName.lastIndexOf("."))+"["+i+"].exe");
            }
            mercg(file,fileName);
            System.out.println("文件已完成合并====");
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