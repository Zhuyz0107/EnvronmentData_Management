package com.zyz.test;



import com.briup.entity.Environment;
import com.briup.utils.CopyUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/23-09-23-21:53
 * @Description：com.briup.resolveData
 * 该类处理获取的数据进行封装成对象集合
 */
public class Test {
    @org.junit.Test
    public List<Environment> getData(){
        //设置流进行数据读取
        BufferedReader br=null;
        //设置集合准备接收处理后的数据集合
        List<Environment> list= new ArrayList<>();
        //try catch也是局部变量
        try {
           br=new BufferedReader(new FileReader("data-file-simple"));
            //读取数据将其装入集合当中
            String line=null;
            //循环提取数据
            while ((line=br.readLine())!=null){
                String[] arr = line.split("\\|");
                //接收输入的数据
                String srcId = arr[0];
                String desId=arr[1];
                String devId=arr[2];
                String sensorAddress=arr[3];
                int count=Integer.parseInt(arr[4]);
                String cmd=arr[5];
                int status = Integer.parseInt(arr[6]);

                Timestamp time = Timestamp.valueOf(arr[8]);
                //处理数据，进行姓名和数据的匹配存储
                String i=sensorAddress;
                //name是在循环外的数据才能被全局调研
                String name=null;
                String name1= null;
                int number = Integer.parseInt(arr[7].substring(0,4), 16);
                switch (i){
                    case "16"://该分类需要使用两个对象传递数据
                        name="温度";
                        Float data=(number*(0.00268127F))- 46.85F;
                        Environment e=new Environment(name,srcId,desId,devId,sensorAddress,
                                count,cmd,status,data, time);
                        name1="湿度";
                        //复制对象传入数据(使用克隆工具类)或者浅克隆
                        int number1=Integer.parseInt(arr[7].substring(4,8), 16);
                        Float data1=(number1*0.00190735F)-6;
                        Environment e1 = CopyUtils.copy(e, "name", "data");
                        e1.setName(name1);
                        e.setData(data1);
                        list.add(e1);
                        break;
                    case "128":
                    case "256":
                        Environment e2=new Environment(name,srcId,desId,devId,sensorAddress,
                                count,cmd,status,(float) number, time);
                        list.add(e2);
                        break;
                }
            }
            System.out.println(list.toArray());
            System.out.println(list.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
