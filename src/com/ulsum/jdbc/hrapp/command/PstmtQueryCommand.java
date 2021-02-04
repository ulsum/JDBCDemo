package com.ulsum.jdbc.hrapp.command;

import java.sql.*;
import java.util.Scanner;

public class PstmtQueryCommand implements Command {

    @Override
    public void execute() {
        System.out.print("请输入部门名称：");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "1qaz!QAZ";
            String sql = "SELECT * FROM employee WHERE dname = ?";
            conn = DriverManager.getConnection(url, username, password);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, s);   // 参数索引从1开始
            rs = pstmt.executeQuery();
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
                if (pstmt != null) {
                    pstmt.close();
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
