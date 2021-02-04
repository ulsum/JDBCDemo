package com.ulsum.jdbc.demo3;

import com.ulsum.jdbc.common.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionSample {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false);      // 关闭自动提交
            String sql = "INSERT INTO employee (eno, ename, salary, dname) VALUES(?,?,?,?)";
            for (int i = 1000; i < 2000; i++) {
                if (i == 1005) {
//                    throw new RuntimeException("操作失败");
                }
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, i);
                pstmt.setString(2, "员工" + i);
                pstmt.setFloat(3, 4000f);
                pstmt.setString(4, "市场部");
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null && !conn.isClosed())
                    conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            DbUtils.closeConnection(null, pstmt, conn);
        }
    }

}
