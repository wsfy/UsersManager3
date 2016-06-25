package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();//第5种流：Print流，可以把基本数据类型的数据被格式化后形成的字符串输出。PrintWriter类是Writer类的子类。
		// 返回一个界面（html技术）
		out.println("<img src='imgs/1.gif'/><hr/>");
		out.println("<h1>用户登录</h1>");
		// action应该这样写：/web应用名/Servlet的url
		out.println("<form action='/UsersManager3/LoginClServlet' method='post'>");
		out.println("用户id：<input type='text' name='id'/><br/>");
		out.println("密    码：<input type='password' name='password'/><br/>");
		out.println("<input type='submit' value='登录'/><br/>");
		out.println("</form>");
		String errInfo = (String) request.getAttribute("err");
		if (errInfo != null) {
			out.println("<font color='red'>" + errInfo + "</font>");
		}
		out.println("<hr/><img src='imgs/mylogo.gif'/>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response);
	}

}
