package com.briup.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/22-09-22-15:12
 * @Description：com.briup
 */
@Data
@NoArgsConstructor //无参构造器
@AllArgsConstructor //全参构造器
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        private int age;
}
