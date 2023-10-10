package com.briup.servers.receive;

import com.briup.Configuration;
import com.briup.ConfigurationAware;
import com.briup.PropertiesAware;
import com.briup.entity.Environment;
import com.briup.joint.Receive;
import com.briup.servers.dbstore.DBStoreImpl;
import com.briup.servers.thread.ExecuteThread;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/21-09-21-14:05
 * @Description：com.briup.server
 *
 *该接口处理数据信息，将信息存储入库
 */
@Log4j
public class ReceiveImpl implements Receive,ConfigurationAware,PropertiesAware{
    private int port;
    Configuration con;
    public void receive() {
        ServerSocket serverSocket = null;
        ThreadPoolExecutor pool=new ThreadPoolExecutor(5,10,2000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        try {
            //一切需要new对象的地方都要被xml文件属性代替
            serverSocket = new ServerSocket(port);
            log.info("服务器："+port+"启动");
            // 服务端 不关闭
            while (true) {
                // accept方法 导致阻塞
                Socket socket = serverSocket.accept();
                pool.execute(()-> {
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(socket.getInputStream());
                        List<Environment> list = (List<Environment>) ois.readObject();
                        // 入库数据
//                        DBStoreImpl dbStore = new DBStoreImpl();
                        //将存储替换
                        DBStoreImpl dbStore = (DBStoreImpl) con.getDbStore();
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
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        con=configuration;
        log.debug("实现Receive接口的配置文件:"+con);
    }


    @Override
    public void init(Properties properties) throws Exception {
        port = Integer.valueOf(properties.getProperty("reciver-server-port"));
        log.debug("注入属性端口号："+port);
    }

}



