package com.ulsum.jdbc.hrapp.command;

import com.ulsum.jdbc.common.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateCommand implements Command {

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入员工编号：");
        Integer eno = in.nextInt();
        System.out.println("请输入员工薪资：");
        Float salary = in.nextFloat();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "UPDATE employee SET salary = ? WHERE eno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, salary);
            pstmt.setInt(2, eno);
            Integer cnt = pstmt.executeUpdate();
            if (cnt == 1) {
                System.out.println("员工信息调整完毕");
            } else {
                System.out.println("未找到eno = " + eno + "的员工");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(null, pstmt, conn);
        }
    }

}
