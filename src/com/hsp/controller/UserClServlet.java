package com.hsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.service.UsersService;

public class UserClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 先接收type
		String type = request.getParameter("type");
		if ("del".equals(type)) {// 说明这个控制器可以响应多个请求，可以避免控制器过多。
			// 接收id
			String id = request.getParameter("id");
			// 调用UserService完成删除
			if (new UsersService().delUser(id)) {
				// Ok forward
				request.getRequestDispatcher("/Ok").forward(request, response);
			} else {// 如果删除失败。可以让域对象带信息过去。
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}
		
		
			
	} 

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
