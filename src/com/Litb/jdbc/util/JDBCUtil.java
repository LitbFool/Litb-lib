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

import org.junit.runners.ParentRunner;

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
	 * @return 返回查询出来的数据源  List<Map<String, Object>>  一个map为一行
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
				colseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//返回查询出来的table
		return table;
	}
	
	/**
	 * 定义一个可供外部调用的数据库插入v,查询和删除方法
	 * @param sql  sql语句 insert或者update
	 * @param paramList	参数可有可无.最多只会处理paramList[0] 即最多只有一个.多的不做处理.
	 * @return	返回int类型,影响了多少行.  如果没有paramList时返回1则为成功. 返回0为失败.   如果有paramList则返回影响了多少行.
	 */
	public static int executeBatch(String sql,List<Object[]>... paramList){
		//定义返回的影响行数
		int successSize = 0;
		Connection conn = getConnction();
		//创建预编译的对象
		PreparedStatement pst = null;
		try {
			//设置手动提交事务
			conn.setAutoCommit(false);
			pst =conn.prepareStatement(sql);
			//判断paramList是否有值,如果有则取第一个
			if(paramList.length > 0){
				//遍历paramList[0] 取出其中的object数组.
				for (int i = 0; i < paramList[0].size(); i++) {
					Object[] value = paramList[0].get(i);
					//object[]数组中存入的为String[].将paramList[0].get(i)赋值给value后继续遍历value中的值.这里value是个数组
					for (int j = 0; j < value.length; j++) {
						//设置占位符对应的参数
						pst.setObject(j+1, value[j]);
					}
					pst.addBatch();
				}
				//提交后返回一个array 数组.根据数组的length来判断影响了多少行
				int[] ids = pst.executeBatch();
				//如果受影响行数和参数 paramList[0].size()对应,则说明全部插入成功
				if(ids.length == paramList[0].size()){
					if (ids.length == 1) {
						//更新操作的时候一个addBatch只会返回一个值.
						successSize = ids[0];
					}else{
						successSize = ids.length;
					}
					conn.commit();
				}else{
					conn.rollback();
				}
			}else{	
				//没有paramList所以没有占位符.直接提交.
				pst.execute();
				conn.commit();
				successSize = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				//关闭资源
				pst.close();
				colseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//返回影响数量.批量执行时返回影响数量.单条时返回1:成功
		return successSize;
	}

}
