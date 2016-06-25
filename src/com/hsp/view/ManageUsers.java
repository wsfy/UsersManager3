package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;
import com.hsp.service.UsersService;

public class ManageUsers extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPageNow() {" +
				"var pageNow = document.getElementById('pageNow');" +
				"window.open('/UsersManager3/ManageUsers?pageNow='+pageNow.value,'_self');}" +
				"function confirmOper() {return window.confirm('真的要删除该用户吗？');}");
		
		out.println("</script>"); 
		out.println("<img src='imgs/1.gif'/>欢迎**登录 <a href='/UsersManager3/LoginServlet'>返回主界面</a> <a href='/UsersManager3/LoginServlet'>安全退出</a><hr/>");
		out.println("<h1>管理用户</h1>");
	
		
		// 定义分页需要的变量
		
		int pageNow = 1;// 当前页
		int pageCount = 0;
		
		// 接受用户的pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if (sPageNow != null) {
			pageNow = Integer.parseInt(sPageNow);
		}
		
		int pageSize = 3;// 指定每页显示3条记录
		
		try {// 该界面通过调用Service类中的方法显示数据
			
			UsersService usersService = new UsersService();
			
			pageCount = usersService.getPageCount(pageSize);
			
			
			
				
			
			
			ArrayList<User> al = usersService.getUsersByPage(pageNow, pageSize);
			
			out.println("<table border=1px bordercolor=green cellspacing=0 width=500px>");
			out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>删除用户</th><th>修改用户</th></tr>");
			// 循环显示所有用户信息
			for (User u : al) {
				out.println("<tr><td>" + u.getId() + 
						"</td><td>" + u.getName() + 
						"</td><td>" + u.getEmail() + 
						"</td><td>" + u.getGrade() + 
						"</td><td><a onClick='return confirmOper();' href='/UsersManager3/UserClServlet?type=del&id=" + u.getId() + "'>删除用户</a></td><td><a href='#'>修改用户</a></td></tr>");
			}
			out.println("</table><br/>");
			
			// 显示上一页
			if (pageNow != 1) {
				out.println("<a href='/UsersManager3/ManageUsers?pageNow="+ (pageNow - 1) +"'>上一页</a>");
			}
			
			
			// 显示分页
			for (int i = 1; i <= pageCount; i++) {
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + i + "'><" + i + "></a>");
			}
			
			// 显示下一页
			if (pageNow != pageCount) {
				out.println("<a href='/UsersManager3/ManageUsers?pageNow="+ (pageNow + 1) +"'>下一页</a>");
			}
			
			// 显示分页信息
			out.println("&nbsp;&nbsp;&nbsp;当前页" + pageNow + "/总页数" + pageCount + "<br/>");
			out.println("跳转到<input type='text' id='pageNow' name='pageNow'/> <input type='button' onClick='gotoPageNow()' value='跳转'/>");
		} catch (Exception e) {
			// TODO: handle exception
		} 
			
		out.println("<hr/><img src='imgs/mylogo.gif'/>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
