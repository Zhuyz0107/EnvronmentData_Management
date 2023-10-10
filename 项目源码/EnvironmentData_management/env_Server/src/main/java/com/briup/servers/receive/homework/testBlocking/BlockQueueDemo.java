package com.briup.servers.receive.homework.testBlocking;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/26-09-26-9:43
 * @Description：com.briup.servers.receive.test
 */
public class BlockQueueDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(){
            @Override
            public void run() {
                myData.consume();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                myData.produce();
            }
        }.start();
    }
}
// 生产线程 消费线程
class MyData{
    ArrayBlockingQueue abq = new ArrayBlockingQueue(3);
    // 消费
    public void consume(){
        try {
            while(true){
                System.out.println("消费了 " + abq.take());
//                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 生产
    public void produce(){
        int i = 0;
        try {
            while(true){
                Thread.sleep(5000);
                abq.put(++i);
                System.out.println("生产了 " + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
