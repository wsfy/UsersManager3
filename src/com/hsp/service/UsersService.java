package com.hsp.service;

import com.hsp.domain.User;
import com.hsp.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
public class UsersService {// 该类主要完成对Users类的操作，即对users表进行管理。

	
	// 获取pageCount
	public int getPageCount(int pageSize) {
		
		
		String sql = "select count(*) from users";
		int rowCount = 0;
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());// 反向引用
		}
		
		
		return (rowCount - 1) / pageSize + 1;
		
	}
	
	// 按照分页来获取用户
	// 公司 把 ResultSet -> User对象 -> ArrayList()（即顺序表，在Java中理解为集合的一种）
	public ArrayList getUsersByPage(int pageNow, int pageSize) {
		
		
		ArrayList al = new ArrayList<User>();
		
		// 查询sql
		String sql = "select * from (select t.*, rownum rn from " +
				"(select * from users order by id) t where rownum<=" + pageSize * pageNow + ") where rn>=" + (pageSize * (pageNow - 1) + 1); 
		
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		// 二次封装 把 ResultSet -> User对象 -> ArrayList()（即顺序表，在Java中理解为集合的一种）
	
		try {
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setGrade(rs.getInt(4));
				// 千万不要王佳 u->ArrayList，即把对象封装到集合（顺序表）中。
				al.add(u);// 把User类的对象添加到集合对象中
				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());// 反向引用
		}
			
			
		
		return al;
	}
	
	
	
	
	// 通过id获取用户数据
	public User getUserById(String id) {
		
		User user = new User();
		String sql = "select * from users where id=?";
		String parameters[] = {id};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		try {
			if (rs.next()) {
				// 二次封装，二次封装的目的：用起来方便
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				user.setPwd(rs.getString(5));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());// 典型：反向引用
		}
		return user;
	}
	
	// 添加用户
	
	public boolean addUser(User user) {
		boolean b = true;
		String  sql = "insert into users values(users_seq.nextval,?,?,?,?)";
		String parameters[] = {user.getName(), user.getEmail(), user.getGrade() + "", user.getPwd()};
		try {
			SqlHelper.executeQuery(sql, parameters);
		} catch (Exception e) {
			b = false;
			// TODO: handle exception
		}
		return b;
	}
	// 修改用户（即修改数据）。这个成员方法接收的是user对象。
	public boolean updUser(User user) {
		boolean b = true;
		String sql = "update users set username=?,email=?,grade=?,passwd=? where id=?";
		String parameters[] = {user.getId() + "", user.getName(), user.getEmail(), user.getGrade() + "", user.getPwd()};
		
		try {// 因为就一条sql语句，所以不涉及事务不事务。
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			b = false;
			// TODO: handle exception
		}
		return b;
		
	}
	// 删除用户
	public boolean delUser(String id) {
		boolean b = true;
		String sql = "delete from users where id=?";
		String parameters[] = {id};
		try {
			SqlHelper.executeQuery(sql, parameters);
		} catch (Exception e) {
			b = false;
			// TODO Auto-generated catch block
		}
		
		return b;
		
	}
	
	// 写一个验证用户是否合法的函数
	public boolean checkUser(User user) {
		
		boolean b = false;
		
		// 使用SqlHelper来完成数据查询任务
		String sql = "select * from users where id=? and passwd=?";
		String parameters[] = {user.getId() + "", user.getPwd()};// 该参数是一个字符数组
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		// 根据rs来判断该用户是否存在
		try {
			if (rs.next()) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());// 典型：反向引用
		}
		return b;
	}
}
