package com.ulsum.jdbc.hrapp.command;

import com.ulsum.jdbc.common.DbUtils;
import com.ulsum.jdbc.hrapp.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaginationCommand implements Command {

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入页号：");
        Integer pageNo = in.nextInt();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Employee> list = new ArrayList<>();
        try {
            conn = DbUtils.getConnection();
            String sql = "SELECT * FROM employee LIMIT ?, 10";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (pageNo - 1) * 10);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer eno = rs.getInt("eno");
                String ename = rs.getString("ename");
                Float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                Date hireDate = rs.getDate("hiredate");     // java.util.Date接收java.sql.Date
                Employee e = new Employee();
                e.setEno(eno);
                e.setEname(ename);
                e.setSalary(salary);
                e.setDname(dname);
                e.setHireDate(hireDate);
                list.add(e);
            }
            System.out.println(list.size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(rs, pstmt, conn);
        }
    }

}
