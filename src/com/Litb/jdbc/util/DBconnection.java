package com.Litb.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author litongbin
 *
 */
public abstract class DBconnection {
	//定义数据库的连接常量
	private static final String DRIVER_CLASS = PropersiesResolver.getValue("jdbc.driver");
	private static final String URL = PropersiesResolver.getValue("jdbc.url");
	private static final String USERNAME = PropersiesResolver.getValue("jdbc.username");
	private static final String PASSWORD = PropersiesResolver.getValue("jdbc.password");
	
	
	//创建一个本地的线程对象
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal();
	//静态块在类被加载的时候自动执行
	static{
		try {
			//加载mysql的驱动
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 定义获取数据库的连接方法
	 * @return
	 */
	protected static Connection getConnction() {
		//从本地线程中获取连接对象
		Connection conn = threadLocal.get();
		//判断从本地线程中获取的连接对象是否是null,如果为null就放到本地的线程中
		if(conn == null){
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				threadLocal.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	/**
	 * 定义关闭方法
	 */
	protected static void colseConnection(){
		//从本地获取连接对象
		Connection conn = threadLocal.get();
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
					threadLocal.remove();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
