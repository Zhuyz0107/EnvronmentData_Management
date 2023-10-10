package com.briup.study;

import com.briup.utils.DBUtil;
import com.briup.utils.JdbcUtils;
import org.junit.Test;

import java.sql.*;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/24-09-24-13:26
 * @Description：com.briup.study
 */
public class TestJdbc {
    @Test
    public void test(){
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn= JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("select * from env_detail_1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("rs = " + rs);
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                }
            }
            throw new RuntimeException(e);
        }finally {
            JdbcUtils.closeAll(conn, ps, null);
        }
    }
}
