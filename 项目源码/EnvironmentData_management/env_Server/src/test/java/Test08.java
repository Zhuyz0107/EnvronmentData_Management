import com.briup.entity.Environment;
import com.briup.utils.JdbcUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/28-09-28-9:18
 * @Description：PACKAGE_NAME
 */
public class Test08 {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/env?useSeverPrepStmts=true";
    String username = "root";
    String password = "0107";

    List<Environment> environmentList = new ArrayList<>();
    @Before
    public void testQuery() throws Exception {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("select name, src_id, des_id, dev_id, sensor_address, count, cmd, status, data, gather_date from environment");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            // name, src_id, des_id, dev_id, sensor_address, count, cmd, status, data, gather_date
            // 将查出的每行数据 -》 Environment -》 集合
            Environment environment = new Environment();
            environment.setName(rs.getString("name"));
            environment.setSrcId(rs.getString("src_id"));
            environment.setDevId(rs.getString("dev_id"));
            environment.setSensorAddress(rs.getString("sensor_address"));
            environment.setCount(rs.getInt("count"));
            environment.setCmd(rs.getString("cmd"));
            environment.setStatus(rs.getInt("status"));
            environment.setData(rs.getFloat("data"));
            environment.setGatherDate(rs.getTimestamp("gather_date"));
            environmentList.add(environment);
        }
        System.out.println("environmentList = " + environmentList.size());
        rs.close();
        ps.close();
        connection.close();
    }






    @Test
    public void need1(){
//        for (int i = 0; i < 10; i++) {
//            System.out.println(environmentList.get(i));
//        }
        environmentList.stream().limit(10).forEach(
                environment -> {
                    System.out.println(environment.getGatherDate().getTime());
                }
        );
    }
    @Test
    public void need02(){
//        for (int i = 0; i < 10; i++) {
//            System.out.println(environmentList.get(i));
//        }
        environmentList.stream()
                .limit(100)
                .forEach(environment -> {
                    System.out.println(environment.getGatherDate().getTime());
                });
    }
    @Test
    public void need01() {
//        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd ");//只根据日期就只需要前面的
//        System.out.println("sdf.format(date) = " + sdf.format(date));
        Map<String, List<Environment>> collect = environmentList
                .stream()
                .collect(Collectors.groupingBy(
                        new Function<Environment, String>() {
                            @Override
                            public String apply(Environment environment) {
                                return sdf.format(environment.getGatherDate());
                            }
                        }
                ));
        TreeSet<String> treeSet=new TreeSet<>(collect.keySet());
        treeSet.forEach(key ->{
            System.out.println(key + "->"+collect.get(key).size());
        });
    }

    @Test
    public void need04(){
        int day = new Date().getDate()-2;
        Map<Integer, Double> map = environmentList
                .stream()
                // 先筛选数据
                .filter(new Predicate<Environment>() {
                    @Override
                    public boolean test(Environment environment) {
                        return environment.getName().equals("温度") && day == environment.getGatherDate().getDate();
                    }
                })
                // 按小时分组
                .collect(Collectors.groupingBy(new Function<Environment, Integer>() {
                    @Override
                    public Integer apply(Environment environment) {
                        return environment.getGatherDate().getHours();
                    }
                    // 分组后求温度平均值
                }, Collectors.averagingDouble(Environment::getData)));
        TreeSet<Integer> treeSet = new TreeSet<>(map.keySet());
        treeSet.forEach(k -> System.out.println(k + "=" + map.get(k)));
//        map.forEach((k,v) -> System.out.println(k+"="+v));
    }
}










