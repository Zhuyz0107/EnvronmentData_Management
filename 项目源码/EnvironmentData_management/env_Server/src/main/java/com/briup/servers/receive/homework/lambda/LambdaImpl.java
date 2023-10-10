package com.briup.servers.receive.homework.lambda;

import com.briup.entity.Environment;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/25-09-25-22:08
 * @Description：com.briup.servers.receive
 * jdk8新特性 划分 day -> List
 *
// * 需求1： 查找数据，并按年月日划分组2022-1-1 list 2022-1-2 遍历
 * 需求2： 环境对象 前100个环境
 * 			timestamp -> 时间戳
 *
 * 需求3： 只查询温湿度数据
 *
 * 需求4： 温湿度各按小时平均值统计
 * 		1  2  3  4  5   6  7
 *
 * 	java: @Log4j不支持非静态嵌套类。
 */
public class LambdaImpl {
    public void run(){
        try(ServerSocket socket=new ServerSocket(9999);
        ) {
//            System.out.println("服务器已启动...");
            Logger logger = Logger.getLogger(LambdaImpl.class);
            logger.info("服务器已启动...");
            Socket accept = socket.accept();
//            System.out.println("客户端"+accept.getRemoteSocketAddress() + " 已连接");

            logger.info("客户端"+accept.getRemoteSocketAddress() + " 已连接");

            new TestThread4(accept).start();
//            new TestThread2(accept).start();
//            new TestThread3(accept).start();
//            new TestThread4(accept).start();
        } catch (Exception e) {
            throw new RuntimeException(e);

    }
}
@Log4j
static class TestThread1 extends Thread{
    private Socket accept;

    public TestThread1(Socket accept) {
        this.accept = accept;
    }//    使用新特性
//     * 需求1： 查找数据，并按年月日划分组2022-1-1 list 2022-1-2 遍历
    @Override
    public void run() {
        ObjectInputStream ois=null;
        Map<String, List<Environment>> listMap=new HashMap<>();
        try {
            ois=new ObjectInputStream(accept.getInputStream());
            List<Environment> environments =(List<Environment>) ois.readObject();
            List<Environment> sameDay1= new ArrayList<>();
            List<Environment> sameDay2= new ArrayList<>();
            for (Environment environment:environments) {
                String key = environment.getGatherDate().toString().substring(0, 10);                sameDay1.add(environment);
                listMap.put(key, sameDay1);
                if (!listMap.containsKey(key)) {
                    String key1 = environment.getGatherDate().toString().substring(0, 10);
                    sameDay2.add(environment);
                    listMap.put(key1, sameDay2);
                }
            }
            //遍历map集合
            for (Map.Entry<String, List<Environment>> entry: listMap.entrySet()) {
                String key = entry.getKey();
                List<Environment> value = entry.getValue();
//                System.out.println("key="+key+" value = " + value);
                 log.info("key="+key+" value = " + value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    @Override
//    public void run() {
//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(accept.getInputStream());
//            List<Environment> list =(List<Environment>) ois.readObject();
//            Map<String, List<Environment>> collect = list.stream()
//                    .collect(Collectors.groupingBy(environment -> environment.getGatherDate().toString().substring(0, 10)));
//            collect.forEach((k, v) -> System.out.println("k:v = " + k + ":" + v));
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
@Log4j
static class TestThread2 extends Thread{
    private Socket accept;

    public TestThread2(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
//            需求2： 环境对象 前100个环境
// * 			timestamp -> 时间戳
            ois = new ObjectInputStream(accept.getInputStream());
            List<Environment> list =(List<Environment>) ois.readObject();

            list.stream().limit(100).forEach(
                    environment -> {
                     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date=sdf.parse(environment.getGatherDate().toString());
                            long time = date.getTime() / 1000;
//                            System.out.println("time = " + time);
                            log.info("time = "+time);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
class TestThread3 extends Thread{
    private Socket accept;

    public TestThread3(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(accept.getInputStream());
            List<Environment> list =(List<Environment>) ois.readObject();
            //创建集合存储温湿度数据
            list.stream().filter(
                    environment -> "16".equals(environment.getSensorAddress())).forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
@Log4j
 static class TestThread4 extends Thread {
    private Socket accept;
    private double wTime = 0;
    private double wStatus = 0;
    private double sTime = 0;
    private double sStatus = 0;

    public TestThread4(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {

        //    需求4： 温湿度各按小时平均值统计
        // * 		1  2  3  4  5   6  7
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(accept.getInputStream());
            List<Environment> list = (List<Environment>) ois.readObject();
//            Map<String, List<Environment>> collect = list.stream()
//                    .collect(Collectors.groupingBy(environment -> environment.getGatherDate().toString().substring(0, 10)));
//            collect.forEach((k, v) -> System.out.println("k:v = " + k + ":" + v));

            //1.先过滤温湿度的数据信息

            list.stream().forEach(environment -> {
                if ("温度".equals(environment.getName())) {
                    wTime += (double) Integer.valueOf(environment.getGatherDate().toString().substring(11, 13));

                    wStatus += environment.getStatus();
                } else {
                    sTime += (double) Integer.valueOf(environment.getGatherDate().toString().substring(11, 13));
                    sStatus += environment.getStatus();
                }
            });

            log.info("wStatus = " + wStatus);
            log.info("sStatus = " + sStatus);
            log.info("wTime = " + wTime);
            log.info("sTime = " + sTime);
            double wAvg = (wStatus / wTime);
            double sAvg = (sStatus / sTime);
            log.info("温度 avg = " + wAvg);
            log.info("湿度 avg = " + sAvg);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 }
}

