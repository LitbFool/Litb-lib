package com.Litb.jdbc.util.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import com.Litb.jdbc.util.JDBCUtil;
/**
 * jdbcutilde 测试类
 * @author Administrator
 *
 */
public class JDBCUtilDemo {
	
//	@Test
	public void QueryTest() {
		String selectSql = "select now() from dual ";
		List<Map<String, Object>> table = JDBCUtil.executeQuery(selectSql);
		System.out.println(table.toString());
	}
//	@Test
	public void updateTest(){
		//insert语句
		String insertSql = "INSERT INTO tab1 (tab1_a , tab1_b) VALUES (?,?)";         
		//update语句
		String updateSql = "update tab1 set tab1_b = ? where tab1_b < ?";
		//delete语句
		String deleteSql = "delete from tab1";
		List<Object[]> paramList = new ArrayList();
		List<Object[]> updateParamList = new ArrayList();
		int c = 0;
		for(int i = 1 ; i < 10 ; i++){
			String[] param = new String[]{i+"",i+""};
			paramList.add(param);
		}
		updateParamList.add(new String[]{"1","5"});
		
		int i = JDBCUtil.executeBatch(insertSql,paramList);
		System.out.println(i);
	}
	@Test
	public void twoSqlTest(){
		//insert语句
		String insertSql = "INSERT INTO tab1 (tab1_a , tab1_b) VALUES (?,?)";         
		//update语句
		String updateSql = "update tab1 set tab1_b = ? where tab1_b < ?";
		//delete语句
		String deleteSql = "delete from tab1";
		List<Object[]> paramList = new ArrayList();
		List<Object[]> updateParamList = new ArrayList();
		int c = 0;
		for(int i = 1 ; i < 10 ; i++){
			String[] param = new String[]{i+"",i+""};
			paramList.add(param);
		}
		updateParamList.add(new String[]{"1","5"});
		int a = JDBCUtil.executeBatch(deleteSql);
		int b =0;
		b =  JDBCUtil.executeBatch(insertSql, paramList);
		int i = new JDBCUtil().executeBatch(updateSql,updateParamList);
		System.out.println("删除"+(a == 1?"成功":"失败")+"|插入"+b+"条|更新"+i+"条记录");
	}
}
