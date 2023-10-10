package com.briup.main;

import com.briup.Configuration;
import com.briup.ConfigurationImpl;
import com.briup.client.SendImpl;
import com.briup.entity.Environment;
import com.briup.joint.Gather;
import com.briup.joint.Send;
import com.briup.resolveData.GatherImpl;

import java.util.List;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/23-09-23-10:10
 * @Description：com.briup
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurationImpl configuration=ConfigurationImpl.getConfiguration();
        Send client = configuration.getClient();

        Gather gather = configuration.getGather();
        List<Environment> data = gather.gatherData();

        if(data != null && data.size()>0){
            client.send(data);
        }
    }
}