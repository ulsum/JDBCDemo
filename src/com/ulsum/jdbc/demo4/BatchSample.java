package com.ulsum.jdbc.demo4;

import com.ulsum.jdbc.common.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchSample {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false);      // 关闭自动提交
            String sql = "INSERT INTO employee (eno, ename, salary, dname) VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            for (int i = 2000; i < 3000; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, "员工" + i);
                pstmt.setFloat(3, 4000f);
                pstmt.setString(4, "市场部");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
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
