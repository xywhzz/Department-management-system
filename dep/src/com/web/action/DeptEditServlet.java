package com.web.action;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hzzstart
 * @create 2023/7/5-17:37
 */
public class DeptEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("	<head>");
        out.print("		<meta charset='utf - 8'>");
        out.print("		<title>修改页面</title>");
        out.print("	</head>");
        out.print("	<body>");
        out.print("		<form action='"+ contextPath +"/dept/modify' method='get'>");
        out.print("		<h1>修改页面</h1>");


        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {

            conn = DBUtil.getConnection();
            String sql = "select dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);

            rs = ps.executeQuery();
            if(rs.next()){
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                // 输出动态网页
                out.print("                部门编号: <input type='text' name='number' value='"+ deptno +"' readonly><br>");
                out.print("                部门名称: <input type='text' name='name' value='"+ dname +"'><br>");
                out.print("                部门位置: <input type='text' name='loc' value='"+ loc +"'><br>");
//            conn = DBUtil.getConnection();
//            String sql = "update dept set deptno = ?,dname = ?,loc = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1,deptno);
//            ps.setString(2,dname);
//            ps.setString(3,loc);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        out.print("		");
        out.print("		<input type='submit' value='提交修改'>");
        out.print("		</form>");
        out.print("	</body>");
        out.print("</html>");


    }
}
