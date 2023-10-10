package com.briup.servers.thread;

import com.briup.Configuration;
import com.briup.ConfigurationAware;
import com.briup.entity.Environment;
import com.briup.servers.dbstore.DBStoreImpl;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/24-09-24-0:41
 * @Description：com.briup.servers.thread
 * 处理数据将数据存储到数据库当中去
 */

// 每个线程拥有1个socket
    @Log4j
public class ExecuteThread implements Runnable, ConfigurationAware {

    private Socket socket;
    Configuration con;

    public ExecuteThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        // 每个线程做的业务
        // 接收数据
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            List<Environment> list = (List<Environment>) ois.readObject();
            // 入库数据
//            DBStoreImpl dbStore = new DBStoreImpl();
            setConfiguration(con);//null指针
            //将存储替换
            DBStoreImpl dbStore = (DBStoreImpl) con.getDbStore();
            //线程里面的con是空的，没法调用set方法？
            //答：因为线程里面没有实现reserve接口，没有办法获取到getServer()，
            // 无法使用获取到的map里的属性集合，此处不能使用分离出来的线程，
            // 或者继承reserve接口重新配置一遍


            dbStore.insertData(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        log.info("使用配置文件获取:"+configuration);
        con=configuration;
    }
}
