package com.ulsum.jdbc.hrapp.command;

import com.ulsum.jdbc.common.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InsertCommand implements Command {

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入员工编号：");
        Integer eno = in.nextInt();
        System.out.println("请输入员工姓名：");
        String ename = in.next();
        System.out.println("请输入员工薪资：");
        Float salary = in.nextFloat();
        System.out.println("请输入隶属部门：");
        String dname = in.next();
        System.out.println("请输入入职日期");
        String strHireDate = in.next();
        // String到java.sql.Date
        // 1. String转java.util.Date
        java.util.Date udHireDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            udHireDate = sdf.parse(strHireDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 2. java.util.Date转java.sql.Date
        Long time = udHireDate.getTime();    // 获取自1970年到现在的毫秒数
        java.sql.Date sdHireDate = new java.sql.Date(time);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "INSERT INTO employee(eno,ename,salary,dname,hiredate) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eno);
            pstmt.setString(2, ename);
            pstmt.setFloat(3, salary);
            pstmt.setString(4, dname);
            pstmt.setDate(5, sdHireDate);
            int cnt = pstmt.executeUpdate();
            System.out.println("cnt = " + cnt);
            System.out.println("员工入职手续已办理，ename = " + ename);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(null, pstmt, conn);
        }
    }

}
