package cn.edu.guet.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class threadpool2 {
    public static void main(String[] args) {
        // 线程池最大的好处：代码可以重复，一个线程任务执行完毕后，会处于待命状态，依然在线程池中
        // corePooSzie 表示 线程池数 （核心线程数）
        // maximumPoolSize 表示最大线程数
        //  capatiy 表示容量
        // keepAliveTime 生存周期
        // maximumPoolSize-corePooSzie 为非核心线程数量
        // TimeUnit.MINUTES 为keepAliveTime 的单位
        int corePooSzie=2;
        int maximumPoolSize=3;
        int capatiy=2;
        int keepAliveTime=5;// 控制非核心线程生存和死亡的时间，核心线程执行完任务后，非核心线程等待5分钟后被销毁
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(corePooSzie,maximumPoolSize,keepAliveTime, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(capatiy),
                (r)->{
                    Thread t=new Thread(r);
                    return t;
                }); // 自己创建线程池
        Runnable r=()->{
            System.out.println("开始下载");
        };
        /*
        如果for执行3次，此时会创建1个非核心线程来执行第3个任务
        如果for执行4次，此时也会创建1个非核心线程，来执行第3个任务，但是第4个线程无执行，第4个线程放入队列中
         */
        for(int i=0;i<5;i++){
            threadPoolExecutor.execute(r);
        }
        threadPoolExecutor.shutdown();
    }
}
