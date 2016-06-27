package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;

public class UpdateUserView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 获取从控制器传递的user对象
		User user = (User) request.getAttribute("userinfo");
		
		// 显示
		out.println("<img src='imgs/1.gif'/><a href='/UsersManager3/LoginServlet'>返回主界面</a><a href='/UsersManager3/LoginServlet'>安全退出</a><hr/>");
		out.println("<h1>修改用户</h1>");
		out.println("<form action='/UsersManager3/UserClServlet?type=update' method='post'>");
		out.println("<table border=1px bordercolor=green cellspacing=0 width=250px>");
		out.println("<tr><td>id</td><td><input type='text' name='id' readonly value='" + user.getId() + "'></td></tr>");
		out.println("<tr><td>用户名</td><td><input type='text' name='username' value='" + user.getName() + "'></td></tr>");
		out.println("<tr><td>email</td><td><input type='text' name='email' value='" + user.getEmail() + "'></td></tr>");
		out.println("<tr><td>级别</td><td><input type='text' name='grade' value='" + user.getGrade() + "'></td></tr>");
		out.println("<tr><td>密码</td><td><input type='text' name='passwd' value='" + user.getPwd() + "'></td></tr>");
		out.println("<tr><td><input type='submit' value='修改用户'></td><td><input type='reset' value='重新填写'></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<hr/><img src='imgs/mylogo.gif'/>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
