package com.Litb.jdbc.util;

import java.sql.Connection;

public class DBconnection {
	
	//����һ�����ص��̶߳���
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	static{
		try {
			//����mysql������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ȡ���ݿ�����ӷ���
	 * @return
	 */
	public Connection getConnction() {
		//�ӱ����߳��л�ȡ���Ӷ���
		Connection conn = threadLocal.get();
		return null;
		
	}
	
}
