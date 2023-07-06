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
import java.sql.SQLException;
import java.util.function.DoubleBinaryOperator;

/**
 * @author hzzstart
 * @create 2023/7/5-18:16
 */
public class DeptModifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String loc = request.getParameter("loc");
        String deptno = request.getParameter("number");

        Connection conn = null;
        PreparedStatement ps = null;

        int total = 0;
        try {
            conn = DBUtil.getConnection();

            String sql  = "update dept set dname=?,loc=? where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,loc);
            ps.setString(3,deptno);

            total = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally{
            DBUtil.close(conn,ps,null);
        }

        if(total == 1){
            // request.getRequestDispatcher("/dept/list").forward(request,response);

            // 使用重定向
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else{
            // request.getRequestDispatcher("/error.html").forward(request,response);

            response.sendRedirect(request.getContextPath() + "/error.html");
        }

    }
}
