//package cn.edu.guet.threadpool.Task;
//
//import org.apache.commons.io.FilenameUtils;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//public class test3 {
//    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,
//                5, TimeUnit.MINUTES,
//                new ArrayBlockingQueue<>(10));
//
//        if (args.length == 0) {
//            System.out.println("运行失败，请给出网址");
//        }
//        String url = args[0];
//        HttpURLConnection[] urlConnection = {null};
//        try {
//            urlConnection[0] = (HttpURLConnection) new URL(url)
//                    .openConnection();// 创建连接
//            int fileSize = urlConnection[0].getContentLength();
//            System.out.println("要下载的文件大小：" + fileSize);
//
//            int index = url.lastIndexOf("/");
//            String fileName = url.substring(index + 1);
//            String baseName = FilenameUtils.getBaseName(fileName);
//            String extensionName = FilenameUtils.getExtension(fileName);
//            // 计算每个线程下载的大小
//            int size = fileSize / 5;
//            // 根据size得出每个线程下载的起始位置和结束位置
//            int startPos = 0;
//            int endPos = 0;
//            /*
//            循环得出每个线程的下载区间，然后启动一个线程去下载
//             */
//            for (int i = 0; i < 5; i++) {
//                // 如果是第1个线程，起始位置0
//                if (i == 0) {
//                    startPos = 0;
//                } else {
//                    startPos = (i * size) + 1;
//                }
//                // 如果是最后一个线程，不需要给结束位置，直接赋值0
//                if (i != 5 - 1) {
//                    endPos = (i + 1) * size;
//                } else {
//                    endPos = 0;
//                }
//                System.out.println("第" + (i + 1) + "个线程下载区间：" + startPos + "--" + endPos);
//                String fileName_i = baseName + "[" + String.valueOf(i) + "]." + extensionName;
//
//
//                int finalEndPos = endPos;
//                int finalStartPos = startPos;
//                int finalI = i;
//                Runnable r = ()->{
//                    System.out.println("开始下载第" + finalI + "个文件: "+fileName_i);
//                    InputStream in = null;
//                    FileOutputStream fos = null;
//                    try {
//                        urlConnection[0] = (HttpURLConnection) new URL(url)
//                                .openConnection();// 创建连接
//                        if (finalEndPos == 0) {
//                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-");
//                        } else {
//                            urlConnection[0].setRequestProperty("Range", "bytes=" + finalStartPos + "-" + finalEndPos);
//                        }
//                        in = urlConnection[0].getInputStream();//请求下载的区间
//                        byte buff[] = new byte[8192];
//                        int a = 0;
//                        fos = new FileOutputStream(fileName_i);
//                        while ((a = in.read(buff)) != -1) {
//                            //如果是第1个线程，则保存为：QQ_6.8.8.6244[0].dmg
//                            //如果是第2个线程，则保存为：QQ_6.8.8.6244[1].dmg
//                            //....
//                            ////如果是最后线程，则保存为：QQ_6.8.8.6244[4].dmg
//                            fos.write(buff,0,a);
//                        }
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        //计数减1
//                        System.out.println("第" + finalI + "个文件下载结束");
//                        executor.shutdown();
//                    }
//                };
//                executor.execute(r);
//
//            }
//            //等待所有线程下载完毕
//            while (true){
//                if (executor.isTerminated()){
//                    break;
//                }
//            }
//            System.out.println("所有线程下载完毕");
//
//            //文件合并
//            BufferedOutputStream out = new BufferedOutputStream(
//                    new FileOutputStream(new File("QQ9.6.9.28856.exe")));
//
//            for (int i = 0; i < 5; i++) {
//                File file = new File("QQ9.6.9.28856[" + i + "].exe");
//                FileInputStream inputStream = new FileInputStream(file);
//                BufferedInputStream in = new BufferedInputStream(inputStream);
//
//                int len = -1;
//                byte[] bt = new byte[1024 * 1024];//每次读取1M,数组大小不能太大，会内存溢出
//                while ((len = in.read(bt)) != -1) {
//
//                    out.write(bt, 0, len);
//                }
//
//                in.close();
//                inputStream.close();
//                out.flush();
//
//                System.out.println("文件已完成：" + file.getName());
//            }
//            System.out.println("文件已完成合并====");
//
////            for (int i=0;i<5;i++){
////                File file = new File("QQ9.6.9.28856[" + i + "].exe");
////                file.delete();
////            }
////            System.out.println("文件已删除");
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
