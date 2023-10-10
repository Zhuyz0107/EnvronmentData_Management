package com.briup.servers.receive.homework.testBlocking;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/26-09-26-9:11
 * @Description：com.briup.servers.receive.test
 *
 * 队列存储，阻塞队列测试
 */
public class TestBlockingQueue {
    public static void main(String[] args) throws Exception {
//        new TestBlockingQueue().testQueue();
        new TestBlockingQueue().testBlockingQueue();
    }
    //阻塞队列
    public void testBlockingQueue() throws Exception {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.take());//取值
        queue.put(1);//存值
        System.out.println(queue.take());

    }
    //队列先进先出原则
    public void testQueue(){
        Queue queue=new ArrayDeque();
        queue.offer("hello");
        queue.offer("good morning");
        queue.offer("hello again");
        System.out.println(queue.element());//取元素头（没有循环就只能取第一个值）
        System.out.println(queue.poll());//取值
        System.out.println(queue.poll());//取值
        System.out.println(queue.poll());//取值
        System.out.println(queue.poll());//没有值就取空值

        System.out.println(queue);
    }
}
