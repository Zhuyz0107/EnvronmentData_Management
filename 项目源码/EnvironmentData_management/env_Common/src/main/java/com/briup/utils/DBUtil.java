package com.briup.utils;



import java.sql.*;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/13-09-13-11:30
 * @Description：com.zyz.study.Utils
 */
public class DBUtil {
    /*
    工具类中的数据构造方法都是私有的，
    因为工具类中的方法都是静态的
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private DBUtil() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/env?useSeverPrepStmts=true",//批处理减少耗时
                "root",
                "0107");//返回的是conn的连接
    }
    public static void closeAll(Connection conn, Statement ps, ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
