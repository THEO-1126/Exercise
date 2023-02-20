package cn.edu.guet.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author liwei
 * @Date 2022/11/21 15:07
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 运行通过 java Test 链接
        // 例如：java Test https://dldir1.qq.com/qqfile/qq/PCQQ9.6.9/QQ9.6.9.28856.exe
        String url;
        if(args.length==0){
            System.out.println("运行失败，请给出网址");
            return;
        }
        else{
            url = args[0];
        }
        HttpURLConnection urlConnection = null;
        RandomAccessFile randomAccessFile = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();// 创建连接
            int fileSize = urlConnection.getContentLength();
            System.out.println("要下载的文件大小：" + fileSize);


            int index = url.lastIndexOf("/");
            System.out.println("最后一个斜杠的索引：" + index);
            String fileName = url.substring(index + 1);
            System.out.println("文件名：" + fileName);

            randomAccessFile = new RandomAccessFile(fileName, "rwd");
            randomAccessFile.setLength(fileSize);// 数据类型从低（int 32）--->高（long 64）会自动转换

        } catch (MalformedURLException e) {
            e.printStackTrace();
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
