package com.ulsum.jdbc.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSample {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "1qaz!QAZ";
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println(conn);
        } catch (ClassNotFoundException e) {    // 当JDBC驱动包没有引入的时候，会报这个错误
            e.printStackTrace();
        } catch (SQLException throwables) {     // 比较宽泛的异常，所有JDBC的异常都会抛这个异常
            throwables.printStackTrace();
        }
    }

}
