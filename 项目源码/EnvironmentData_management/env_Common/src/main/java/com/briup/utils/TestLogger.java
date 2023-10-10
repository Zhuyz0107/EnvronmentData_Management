package com.briup.utils;

import org.apache.log4j.Logger;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/26-09-26-14:25
 * @Description：com.briup.utils
 */
public class TestLogger {
    public static void main(String[] args) {
        //获取日志对象
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.info("测试");
        Logger logger = Logger.getLogger(TestLogger.class);
        //调整配置文件级别，只关注(输出)该级别以上的信息，，下面是从低到高的级别，一般是debug和info
        //off关闭输出
        logger.debug("debug日志");
        logger.info("info测试日志");
        logger.warn("warn测试日志");
        logger.error("error测试日志");
        logger.fatal("fatal测试日志");
    }
}
