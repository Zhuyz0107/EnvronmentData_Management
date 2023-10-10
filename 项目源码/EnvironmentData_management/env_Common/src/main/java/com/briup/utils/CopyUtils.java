package com.briup.utils;



import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/23-09-23-22:58
 * @Description：com.briup.utils
 *
 *static final修饰 不能使用反射复制信息
 */
public class CopyUtils {
    //书写一个克隆对象的方法，由于不知道对象类型
    //所以使用泛型进行数据处理
    //使用反射的三种方式中的1.对象.getClass来获取对象类型
    //2.任何类型.class
    //3.Class.forname("完整类名")
    public static <T> T copy(T obj,String...args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> aClass = Class.forName(obj.getClass().getName());
        Object ss = aClass.newInstance();
        //遍历属性
        Field[] fields = aClass.getDeclaredFields();
        for (Field field:fields) {
            //添加条件筛选数据
            //static final不能复制信息
            //static 可以 final 可以
            boolean flag = true;
            String modifier = Modifier.toString(field.getModifiers());
//            System.out.println("modifier = " + modifier);
//            Logger logger = Logger.getLogger(CopyUtils.class);
//            logger.debug(modifier);

            if(modifier.contains("static final")){
                continue;
            }
            for (String s : args) {
                if (field.getName().equals(s)) {
                    flag = false;
                }
            }
            //处理复制数据
            if (flag) {
                field.setAccessible(true);
                field.set(ss, field.get(obj));//后面的元素是oldValues
            }
        }
        return (T)ss;
    }
}
