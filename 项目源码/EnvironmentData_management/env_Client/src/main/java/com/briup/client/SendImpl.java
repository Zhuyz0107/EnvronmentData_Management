package com.briup.client;

import com.briup.Configuration;
import com.briup.ConfigurationAware;
import com.briup.PropertiesAware;
import com.briup.entity.Environment;
import com.briup.joint.Send;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/21-09-21-14:03
 * @Description：com.briup.client
 */
@Log4j
public class SendImpl implements Send, PropertiesAware {
    /**
     * 发送数据
     * @param list 采集的数据
     */
    private  String ip;
    private int port;
    public void send(List<Environment> list){
        Socket socket = null;
        ObjectOutputStream oos = null;
        try {
//            socket = new Socket("localhost",9999);
            socket=new Socket(ip, port);
            // 对象流输出流
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(list);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (oos != null) {
                    oos.close();
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
    public void init(Properties properties) throws Exception {
        log.debug("SendImpl:属性："+properties);
        ip = properties.getProperty("client-ip");
        port = Integer.parseInt(properties.getProperty("client-port"));
    }
}
