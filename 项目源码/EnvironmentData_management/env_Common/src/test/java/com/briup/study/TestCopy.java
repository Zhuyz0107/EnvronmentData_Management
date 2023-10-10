package com.briup.study;

import com.briup.utils.CopyUtils;
import org.junit.Test;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/22-09-22-15:05
 * @Description：com.briup
 */
public class TestCopy {
    @Test
    public void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Student student=new Student(1, "zyz",23);
        Student copy = CopyUtils.copy(student,"age" );
        System.out.println("copy = "+copy);
    }
}
