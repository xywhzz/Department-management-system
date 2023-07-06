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
 * @create 2023/7/5-9:26
 */
public class DeptDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resposne) throws ServletException, IOException {

        resposne.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resposne.getWriter();

        out.print("        <!DOCTYPE html>");
        out.print("<html>");
        out.print("	<head>");
        out.print("		<meta charset='utf-8'>");
        out.print("		<title>部门详情</title>");
        out.print("	</head>");
        out.print("   	<body>");


        // 获取部门编号
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "select dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,deptno);

            rs = ps.executeQuery();
            while (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("                		<h1>部门详情</h1>");
                out.print("		<hr>");
                out.print("                        部门编号："+ deptno +" <br>");
                out.print("                        部门名称："+ dname +" <br>");
                out.print("                部门位置："+ loc +" <br>");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        out.print("		<input type='button' value='后退' onclick='window.history.back()'>");
        out.print("	</body>");
        out.print("</html>");
    }
}
