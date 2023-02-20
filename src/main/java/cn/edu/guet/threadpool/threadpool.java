package cn.edu.guet.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threadpool {
    public static void main(String[] args) {
        int threadNum=5; //线程数量
        // 1. 创建线程池对象
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        // 2. 创建任务
        Runnable runnable=()->{
            System.out.println("开始下载");
        };
        executorService.execute(runnable); // 执行线程任务
        executorService.shutdown();// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
    }
}
