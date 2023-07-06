package com.web.action;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hzzstart
 * @create 2023/7/5-15:37
 */
public class DeptAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String deptno = request.getParameter("number");
        String dname = request.getParameter("name");
        String loc = request.getParameter("loc");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            conn = DBUtil.getConnection();

            String sql = "insert into dept (deptno,dname,loc) values (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            ps.setString(2, dname);
            ps.setString(3, loc);
            i = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        if (i == 1) {
            // request.getRequestDispatcher("/dept/list").forward(request, response);

            // 使用重定向
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // request.getRequestDispatcher("/error.html").forward(request, response);

            // 使用重定向
            response.sendRedirect(request.getContextPath() + "/error.html");
        }

    }
}
