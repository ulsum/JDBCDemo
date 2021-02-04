package com.ulsum.jdbc.common;

import java.sql.*;

public class DbUtils {

    public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai";
    public static final String USER = "root";
    public static final String PASSWORD = "1qaz!QAZ";

    /**
     * 创建新的数据库连接
     *
     * @return Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DBDRIVER);
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    /**
     * 关闭连接，释放资源
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
