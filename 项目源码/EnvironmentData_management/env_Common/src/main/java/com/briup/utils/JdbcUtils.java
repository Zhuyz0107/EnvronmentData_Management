package com.briup.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/22-09-22-15:38
 * @Description：com.briup.utils
 * 返回一个连接
 */
public class JdbcUtils {
    //默认调用信息无参构造器
    public void JdbcUtils(){}
    public static DataSource dataSource;

    //准备数据源
    static {
        InputStream in=null;
        try {
            in = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties properties=new Properties();
            properties.load(in);
            dataSource= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
}

    public static void closeAll(Connection conn, Statement ps, ResultSet rs){
        if (conn!=null){
            try {
                conn.close();//归还连接池
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
