package com.ulsum.jdbc.hrapp.command;

import java.sql.*;
import java.util.Scanner;

public class QueryCommand implements Command {

    @Override
    public void execute() {
        System.out.print("请输入部门名称：");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "1qaz!QAZ";
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            System.out.println("SELECT * FROM employee WHERE dname = '" + s + "'");     // 当传入字符串是 ' or 1 = 1 or 1 = ' 时存在注入攻击
            rs = stmt.executeQuery("SELECT * FROM employee WHERE dname = '" + s + "'");
            while (rs.next()) {
                Integer eno = rs.getInt(1);
                String ename = rs.getString("ename");
                Float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                System.out.println(dname + " - " + eno + " - " + ename + " - " + salary);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null && !conn.isClosed())   // 其实只要关闭了conn，rs和stmt就自动关闭了
                {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
