package com.web.action;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author hzzstart
 * @create 2023/7/3-18:18
 */

public class DeptListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取应用的根路径
        String contextPath = request.getContextPath();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("	<head>");
        out.print("		<meta charset='utf-8'>");
        out.print("		<title>部门列表</title>");
        out.print("	</head>");
        out.print("	<body>");

        out.print("        <script type='text/javascript'>");
        out.print("            function del(deptno){");
        out.print("                var ok = window.confirm('真的要删除吗');");
        out.print("                if(ok){");
        out.print("                    window.location = '"+ contextPath +"/dept/delete?deptno=' + deptno;");
        out.print("                }");
        out.print("            }");
        out.print("        </script>");

        out.print("		<h1 align = 'center'>部门列表</h1>");
        out.print("		<table border='1px'' align='center'>");
        out.print("			");
        out.print("			<tr>");
        out.print("				<th>序号</th>");
        out.print("				<th>部门编号</th>");
        out.print(" 				<th>部门名称</th>");
        out.print("				<th>操作</th>");
        out.print("			</tr>");


        // 连接数据库，查询所有部门
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "select deptno,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("                			<tr>");
                out.print("				<td>" + ++i + "</td>");
                out.print("				<td>" + deptno + "</td>");
                out.print("				<td>" + dname + "</td>");
                out.print("				<td>");
                out.print("					<a href='javascript:void(0)' onclick='del(" + deptno + ")'>删除</a>");
                out.print("					<a href='"+ contextPath + "/dept/edit?deptno=" + deptno + "'>修改</a>");
                out.print("					<a href='" + contextPath + "/dept/detail?deptno=" + deptno + "'>详情</a>");
                out.print("				</td>");
                out.print("			</tr>");

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }


        out.print("</table>");
        out.print("		<hr>");
        out.print("		<a href='"+ contextPath +"/add.html'>新增部门</a>");
        out.print("	</body>");
        out.print("</html>");

    }
}
