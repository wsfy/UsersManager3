package com.hsp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 
 * @author user
 * 这是一个操作数据库的工具类。
 */
public class SqlHelper {
	
	// 定义需要的变量
	private static Connection ct = null;
	// 在大多数情况下，我们使用的是PreparedStatement来替代Statement
	// 这样可以防止sql注入。
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	// 连接数据库参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	// 读配置文件
	private static Properties pp = null;
	private static InputStream fis = null;
	
	// 加载驱动，只需要一次
	static {
		try {
			// 从dbinfo.propertis文件中读取配置信息
			pp = new Properties();
			//fis = new FileInputStream("dbinfo.properties");
			// 当我们使用JavaWeb的时候，读取文件要使用类加载器[因为类加载器在读取资源的视乎，默认的主目录是src] 
			fis = SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			pp.load(fis); 						  // 装载数据库，装载完后开始读取。
			url = pp.getProperty("url");          // 读取配置文件中的信息
			username = pp.getProperty("username");// 读取配置文件中的信息
			driver = pp.getProperty("driver");    // 读取配置文件中的信息
			password = pp.getProperty("password");// 读取配置文件中的信息
			Class.forName(driver);				  // 加载驱动
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fis = null;
		}
	}
	
	// 获取连接对象
	public static Connection getConnection() {
		
		try {
			ct = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
	
	// 分页问题
	public static ResultSet executeQuery2() {
		return null;
	}
	
	// 调动存储过程，有返回值Result的存储过程。
	// sql call 过程(?,?,?)
	public static CallableStatement callPro2 
	(String sql, String[] inparameters, Integer[] outparameters) {
		
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if (inparameters != null) {
				for (int i = 0; i < inparameters.length; i++) {
					cs.setObject(i + 1, inparameters[i]);
				}
			}
			
			// 给out参数赋值
			if (outparameters != null) {
				for (int i = 0; i < outparameters.length; i++) {
					cs.registerOutParameter(inparameters.length + 1 + i, outparameters[i]);
				}
			}
			
			cs.execute();
			
		} catch(Exception e) {
	        e.printStackTrace(); 
	        throw new RuntimeException(e.getMessage());
			// TODO Auto-generated catch block
	    } finally {
	    	// 不需要关闭
	    }
	    return cs;
	    
	}
	
	// 调用存储过程，没有返回参数的存储过程。
	// sql 对象 {call 过程 (?,?,?)}
	public static void callPro1(String sql,String[] parameters) {
        
		try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            
            // ?号赋值
            if (parameters != null) {
                for(int i=0;i<parameters.length;i++) {
                    ps.setString(i+1,parameters[i]);
                }
            }
            cs.execute();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
			// TODO Auto-generated catch block
        } finally {
           
        	close(rs, cs, ct);
        }
    }
	
	// 统一的select 
	// ResultSet->ArrayList
	public static ResultSet executeQuery(String sql, String[] parameters) {
        
		try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            
            // ?号赋值
            if (parameters != null && !parameters.equals("")) {
                for(int i = 0; i < parameters.length; i++) {
                    ps.setString(i+1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
			// TODO Auto-generated catch block
        } finally {
        	//close(rs, cs, ct);
        }
		
		return rs;
		
    }
	
	// 如果有多个 update/delete/insert[需要考虑事务]
	public static void executeUpdate2(String[] sql,String[][] parameters) {
   
		try {
			
			// 核心
			// 1、获取连接对象
            ct = getConnection();
            
            // 因为这时，用户传入的可能是多个sql语句
            ct.setAutoCommit(false);
            //....
            for(int i=0; i < sql.length; i++) {
                if (null != parameters[i]) {
                    ps = ct.prepareStatement(sql[i]);
                    for(int j=0;j<parameters[i].length;j++) {
                    	
                        ps.setString(j+1,parameters[i][j]);
                    }
                    ps.executeUpdate();
                }
            }
            
            
            ct.commit();
           
           
        } catch (Exception e) {
            e.printStackTrace();
            
            // 回滚
            try {
                ct.rollback();
            } catch (SQLException e1) {
    			// TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            throw new RuntimeException(e.getMessage());
			// TODO: handle exception
        } finally {
        	
            close(rs,ps,ct);
        }
       
		
    }
	//先写一个update、delete、insert。该方法是不考虑事务的数据更新，即不考虑事务的增删改查。
    //sql格式：update 表名 set 字段名 =？where 字段=？
    //parameters 应该是{"abc",23}
    public static void executeUpdate(String sql,String[] parameters) {
    	
    	// 1、创建一个ps
        try {
        	
            ct=getConnection();
            ps = ct.prepareStatement(sql);
            // 给？赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i+1, parameters[i]);
                }
            }
            
            // 执行
            ps.executeUpdate();

        
        } catch (Exception e) {
            e.printStackTrace();//开发阶段
            //抛出异常，抛出运异常，可以给调用该函数的函数一个选择。
            //可以处理，也可以放弃处理。
            throw new RuntimeException(e.getMessage());
        } finally {
        	
        	// 关闭资源
            close(rs,ps,ct);
        }
        
    }
	
    // 关闭资源的成员方法
    public static void close(ResultSet rs,Statement ps,Connection ct) {
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
    			// TODO: handle exception
            }
        }
        
        // 关闭资源[先开后闭]
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
    			// TODO: handle exception
                e.printStackTrace();
            }
            ps=null;// 使用垃圾回收
        }
        if (ct != null) {
            try {
                ct.close();
            } catch (SQLException e) {
    			// TODO: Auto-generated catch block
                e.printStackTrace();
            }
            ct=null;
        }
        
    }
    
    
    public static Connection getCt() {
        return ct;
    }
    
    
    public static PreparedStatement getPs() {
        return ps;
    }
    
    
    public static ResultSet getRs() {
        return rs;
    }
   
   public static CallableStatement getCs() {
	   return cs;
   }

    
}