package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;

public class MainFrame extends HttpServlet {// 用户管理主界面

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<img src='imgs/1.gif'/>欢迎**登录 <a href='/UsersManager3/LoginServlet'>返回重新登录</a><hr/>");
		
		out.println("<h3>请选择您要进行的操作</h3>");
		out.println("<a href='/UsersManager3/ManageUsers'>管理用户</a><br/>");
		out.println("<a href='/UsersManager3/UserClServlet?type=gotoAddUser'>添加用户</a><br/>");
		out.println("<a href=''>查找用户</a><br/>");
		out.println("<a href=''>退出系统</a><br/>");
		out.println("<hr/><img src='imgs/mylogo.gif'/>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
