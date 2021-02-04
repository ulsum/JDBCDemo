package com.ulsum.jdbc.demo1;

import java.sql.*;

/**
 * 标准JDBC操作五步骤
 */
public class StandardJdbcSample {

    public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai";
    public static final String USER = "root";
    public static final String PASSWORD = "1qaz!QAZ";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 加载并注册JDBC驱动
            Class.forName(DBDRIVER);
            // 创建数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 创建Statement对象
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            // 便利查询结果
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
            try {
                if (conn != null && conn.isClosed() == false)
                // 关闭连接，释放资源
                {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
