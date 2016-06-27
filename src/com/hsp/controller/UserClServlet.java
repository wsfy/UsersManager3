package com.hsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;
import com.hsp.service.UsersService;

public class UserClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UsersService usersService = new UsersService();
		// 先接收type
		String type = request.getParameter("type");
		if ("del".equals(type)) {// 说明这个控制器可以响应多个请求，可以避免控制器过多。
			// 接收id
			String id = request.getParameter("id");
			// 调用UserService完成删除
			if (usersService.delUser(id)) {
				// Ok forward
				request.setAttribute("info", "删除成功！");
				request.getRequestDispatcher("/Ok").forward(request, response);
			} else {// 如果删除失败。可以让域对象带信息过去。
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		} else if ("gotoUpdView".equals(type)) {
			// 要去修改界面
			// 得到id
			String id = request.getParameter("id");
			// 通过id获取一个UserBean。在控制器中拿到了一个user对象，但这个对象并非给这个servlet用，而是给下一个servlet（是个界面用。
			User user = usersService.getUserById(id);
			// 为了让下一个Servlet（界面）使用我们的user对象，我们可以把该
			// user对象放入到request对象中
			request.setAttribute("userinfo", user);
			
			// 请求转发。request对象得到request的分派器。
			request.getRequestDispatcher("/UpdateUserView")
			.forward(request, response);
			
		} else if ("update".equals(type)) {
			// 接收用户新的信息。当用户从表单提交过来以后，不管表单中是数还是字母，发到服务器的全是字符串，所以全用字符串接收。如果用户提交的数据格式不对，有一个验证。
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String passwd = request.getParameter("passwd");
					
			// 把接收到的信息，封装成一个User类的对象
			User user = new User(Integer.parseInt(id), username, email, Integer.parseInt(grade), passwd);
			// 修改用户
			if (usersService.updUser(user)) {
				request.setAttribute("info", "修改成功！");
				request.getRequestDispatcher("/Ok").forward(request, response);
			} else {
				request.setAttribute("info", "修改失败！");
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		
		} else if ("gotoAddUser".equals(type)) {
			// 这里没有什么要处理的
			request.getRequestDispatcher("AddUserView").forward
			(request, response);
		} else if ("add".equals(type)) {
			addUser(request, response, usersService);
		}
		
		
		
	}

	// 添加用户的成员方法
	private void addUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// 接收用户信息。
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");
		
		// 构建一个user对象。
		// 把接收到的信息，封装成一个User类的对象。
		User user = new User();
		user.setName(username);
		user.setEmail(email);
		user.setGrade(Integer.parseInt(grade));
		user.setPwd(passwd);
		
		if (usersService.addUser(user)) {
			request.setAttribute("info", "添加成功！");
			request.getRequestDispatcher("/Ok").forward(request, response);
		} else {
			request.setAttribute("info", "添加失败！");
			request.getRequestDispatcher("/Err").forward(request, response);
		}
	} 

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
