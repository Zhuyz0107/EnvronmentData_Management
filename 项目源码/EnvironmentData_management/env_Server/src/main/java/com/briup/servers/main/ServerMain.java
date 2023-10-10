package com.briup.servers.main;



import com.briup.joint.Receive;
import com.briup.servers.ConfigurationImpl;


/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/21-09-21-14:21
 * @Description：com.briup.main
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        ConfigurationImpl configuration = ConfigurationImpl.getConfiguration();
        Receive server1 = configuration.getServer();
        server1.receive();

//        ReceiveImpl server = new ReceiveImpl();
//        server.receive();

//        LambdaImpl lambda=new LambdaImpl();
//        lambda.run();

    }
}
