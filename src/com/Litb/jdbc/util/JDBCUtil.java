package com.Litb.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自己封装的mysql连接库
 * @author litongbin
 * 
 */
public class JDBCUtil extends DBconnection {
	
	/**
	 * 定义一个可供外部调用的数据库查询方法
	 * @param sql	预编译的sql语句
	 * @param parameters	预编译的sql语句占位符对应的参数值
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery(String sql, Object...parameters){
		//存储表
		List<Map<String, Object>> table = new ArrayList();
		//获取连接对象
		Connection conn = getConnction();
		//创建预编译的对象
		PreparedStatement pst = null;
		ResultSet rs =null;
		try {
			pst =  conn.prepareStatement(sql);
			//根据parameters判断是否需要设置参数
			if(parameters.length > 0){
				//循环遍历parameters
				for (int i = 0; i < parameters.length; i++) {
					//设置对应的占位符对应的参数 		setObject从1开始,数组从0开始
					pst.setObject(i+1, parameters[i]);
				}
			}
			//通过编译对象执行sql查询指令
			 rs = pst.executeQuery();
			//判断rs是否为null
			if (rs != null) {
				//获取结果集中的元数据对象
				ResultSetMetaData rsd = rs.getMetaData();
				//获取查询结果的列
				int columnCount = rsd.getColumnCount();
				//遍历结果集  rs游标一次读取一行,判断是否还有下一行
				while(rs.next()){
					Map<String, Object> row = new HashMap();
					//遍历当前行的每一个列
					for (int i = 0; i < columnCount; i++) {
						//获取列的编号或者列名 第一列是1,所以用i+1
						String columnName =rsd.getColumnName(i+1);
						//通过列名获取当前行中对应的列的值
						Object columnValue = rs.getObject(columnName);
						//吧获取的列名当做key,值当做value再put到map中
						row.put(columnName, columnValue);
					}
					//遍历完毕一行后将当前行存入table中
					table.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//在finally中关闭资源
			try {
				//按从下到上的顺序一步一步关闭
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//返回查询出来的table
		return table;
	}
}
