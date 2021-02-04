package com.ulsum.jdbc.demo5;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ulsum.jdbc.common.DbUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DruidSample {

    public static void main(String[] args) {
        // 1. 加载属性文件
        Properties properties = new Properties();
        String propertyFile = DruidSample.class.getResource("/druid-config.properties").getPath();  // 配置文件放在src目录下
        // 经过上面的方法路径的空格会被转成%20
        // 经过下面的方法路径的%20会转回成空格
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
            properties.load(new FileInputStream(propertyFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 2. 获取DataSource数据源对象
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            // 3. 创建数据库连接
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM employee LIMIT 10");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer eno = rs.getInt(1);
                String ename = rs.getString("ename");
                Float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                System.out.println(dname + " - " + eno + " - " + ename + " - " + salary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 不使用连接池：conn.close()关闭连接
            // 使用连接池：conn.close()将连接回收至连接池
            DbUtils.closeConnection(rs, pstmt, conn);
        }
    }

}
