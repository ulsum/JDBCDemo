package com.ulsum.jdbc.demo7;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ulsum.jdbc.hrapp.entity.Employee;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DbUtilsSample {

    public static void main(String[] args) {
        query();
        update();
    }

    public static void query() {
        Properties properties = new Properties();
        String propertyFile = DbUtilsSample.class.getResource("/druid-config.properties").getPath();
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
            properties.load(new FileInputStream(propertyFile));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            QueryRunner qr = new QueryRunner(dataSource);
            List<Employee> list = qr.query("SELECT * FROM employee LIMIT ?, 10",
                    new BeanListHandler<>(Employee.class),
                    new Object[]{10});
            for (Employee e : list) {
                System.out.println(e.getEname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 不需要手动关闭
    }

    public static void update(){
        Properties properties = new Properties();
        String propertyFile = DbUtilsSample.class.getResource("/druid-config.properties").getPath();
        Connection conn = null;
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
            properties.load(new FileInputStream(propertyFile));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "UPDATE employee SET salary = salary + 1000 WHERE eno = ?";
            String sql2 = "UPDATE employee SET salary = salary -500 WHERE eno = ?";
            QueryRunner qr = new QueryRunner();
            qr.update(conn,sql1,new Object[]{1000});
            qr.update(conn,sql2,new Object[]{1001});
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn!=null&&!conn.isClosed()){
                    conn.rollback();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try {
                if (conn!=null&&!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
