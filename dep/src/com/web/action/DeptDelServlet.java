package com.web.action;

import com.utils.DBUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hzzstart
 * @create 2023/7/5-15:19
 */
public class DeptDelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0;

        String deptno = request.getParameter("deptno");

        try {
            conn = DBUtil.getConnection();

            // 开启事务（自动提交事务关闭）
            conn.setAutoCommit(false);

            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            count = ps.executeUpdate();

            // 事务提交
            conn.commit();
        } catch (SQLException throwables) {

            // 遇到异常要回滚
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        if(count == 1){
            // 删除成功后 需要回到部门列表
            // request.getRequestDispatcher("/dept/list").forward(request,response);

            // 使用重定向
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else{
            // 使用重定向
            response.sendRedirect(request.getContextPath() + "/error.html");
        }

    }
}
