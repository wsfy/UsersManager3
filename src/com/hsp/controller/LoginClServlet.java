package com.hsp.controller;

import com.hsp.domain.User;
import com.hsp.service.UsersService;
import com.test.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginClServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 接收用户提交的用户名和密码
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// 创建UsersService对象，完成到数据库的验证
		
		UsersService usersService = new UsersService();
		User user = new User();
		user.setId(Integer.parseInt(id));
		user.setPwd(password);
		// 5、根据结果做处理
		if (usersService.checkUser(user)) {
			// 说明该用户合法
			request.getRequestDispatcher("/MainFrame").forward(request, 
					response);
		} else {
			request.setAttribute("err", "用户id 或者 密码有误！");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
			
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			
			this.doGet(request, response);
		}
	
}
