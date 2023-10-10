package com.briup.servers;



import com.briup.Configuration;
import com.briup.ConfigurationAware;
import com.briup.PropertiesAware;
import com.briup.joint.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/27-09-27-15:15
 * @Description：com.briup.server
 */
public class ConfigurationImpl implements Configuration {
    // 文件只会加载一次 对象应该是单例
    /**
     * 单例模式的规则：
     * 1.构造器私有
     * 2.提前创建
     * 3.属性被别人调用，公有方法
     */
    private static ConfigurationImpl configuration=new ConfigurationImpl();
    public ConfigurationImpl(){}
    public static ConfigurationImpl getConfiguration(){
        return configuration;
    }

    static Map<String,Object> map = new HashMap<>();
    static Properties properties = new Properties();
    /**
     * 解析server下的config.xml文件 把对象获取
     * @return
     * @throws Exception
     */
    static {
        try {
            //打包使用jre运行不能有以src根路径加载文件
            // 1.加载config文件
            SAXReader saxReader = new SAXReader();
            //使用反射类加载流使用
            InputStream in = ConfigurationImpl.class.getClassLoader().getResourceAsStream("server-config.xml");
            Document document = saxReader.read(in);//使用SAX读取流
            // 1.1 根节点
            Element rootElement = document.getRootElement();
            // 1.2 其他节点
            List<Element> firsetElements = rootElement.elements();
            firsetElements.forEach(firstElement -> {
                String name = firstElement.getName();
                String aClass = firstElement.attribute("class").getValue();
                try {
                    // 2.把每个节点的名字和class类属性
                    map.put(name,Class.forName(aClass).newInstance());
                    List<Element> sedElements = firstElement.elements();
                    sedElements.forEach(secondElement -> {
                        String name1 = secondElement.getName();
                        String value = secondElement.getStringValue();
                        properties.put(name1, value);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            map.forEach((k,v) -> System.out.println(k+"="+v));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // 4.遍历map中的所有值（所有对象）
        for (Object obj: map.values()) {
            //向下转型，知道使用该接口的类，传过去 的这个类可以对象进行配置操作
            if(obj instanceof ConfigurationAware){
                ConfigurationAware configurationAware = (ConfigurationAware) obj;
                try {
                    configurationAware.setConfiguration(configuration);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if(obj instanceof PropertiesAware){
                PropertiesAware propertiesAware = (PropertiesAware) obj;
                try {
                    propertiesAware.init(properties);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        for (Object obj : map.values()) { // 接收 入库对象
//            if(obj instanceof ConfigurationAware){
//                ConfigurationAware configurationAware = (ConfigurationAware) obj;
//                configurationAware.setConfiguration(configuration);
//            }
//            if(obj instanceof PropertiesAware){
//                PropertiesAware propertiesAware = (PropertiesAware) obj;
//                propertiesAware.init(properties);
//            }
//        }
    }
    public static void main(String[] args) throws Exception {
        // 1.加载config文件
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("env_Server/src/main/resources/server-config.xml");
        // 1.1 根节点
        Element rootElement = document.getRootElement();
        // 1.2 其他节点
        List<Element> firsetElements = rootElement.elements();
        firsetElements.forEach(firstElement -> {
            String name = firstElement.getName();
            String aClass = firstElement.attribute("class").getValue();
            try {
                // 2.把每个节点的名字和class属性
                map.put(name,Class.forName(aClass).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        map.forEach((k,v) -> System.out.println(k+"="+v));
    }
    @Override
    public Receive getServer() throws Exception {
        return (Receive) map.get("server");
    }

    @Override
    public Send getClient() throws Exception {
        return null;
    }

    @Override
    public DBStore getDbStore() throws Exception {
        return (DBStore) map.get("dbStore");
    }

    @Override
    public Gather getGather() throws Exception {
        return null;
    }

    @Override
    public Backup getBackup() throws Exception {
        return null;
    }
}
