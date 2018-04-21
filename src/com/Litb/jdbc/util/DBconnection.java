package com.Litb.jdbc.util;

import java.sql.Connection;

public class DBconnection {
	
	//创建一个本地的线程对象
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	static{
		try {
			//加载mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 定义获取数据库的连接方法
	 * @return
	 */
	public Connection getConnction() {
		//从本地线程中获取连接对象
		Connection conn = threadLocal.get();
		return null;
		
	}
	
}
