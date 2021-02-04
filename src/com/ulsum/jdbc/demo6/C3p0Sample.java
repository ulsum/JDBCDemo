package com.ulsum.jdbc.demo6;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ulsum.jdbc.common.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3p0Sample {

    public static void main(String[] args) {
        // 1. 加载配置文件
        // 2. 创建DataSource
        DataSource dataSource = new ComboPooledDataSource();
        // 3. 得到数据库连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 不使用连接池：conn.close()关闭连接
            // 使用连接池：conn.close()将连接回收至连接池
            DbUtils.closeConnection(rs, pstmt, conn);
        }
    }

}
