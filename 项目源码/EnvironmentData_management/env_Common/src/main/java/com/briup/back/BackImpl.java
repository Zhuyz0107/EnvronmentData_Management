package com.briup.back;

import com.briup.entity.Environment;
import com.briup.joint.Backup;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.List;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/26-09-26-16:38
 * @Description：com.briup.back
 */

public class BackImpl implements Backup {
    @Override
    public Object load(String filePath, boolean del) throws Exception {
        //读取出来文件里面的数据
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        //如果true删除文件
        if (del){
            File file=new File(filePath);
            file.delete();
        }

        fis.close();
        ois.close();
        return obj;
    }

    @Override
    public void store(String filePath, Object obj, boolean append) throws Exception {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath, append))) {
            oos.writeObject(obj);
            oos.flush();
        }
    }
}
