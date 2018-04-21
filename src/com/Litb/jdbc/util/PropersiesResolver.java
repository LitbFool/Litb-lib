package com.Litb.jdbc.util;

import java.util.ResourceBundle;

/**
 * 读取properties文件
 * @author litongbin
 *
 */
public class PropersiesResolver {
	
	private static ResourceBundle bundle;
	
	static{
		//需要有个名为jdbc的properties文件.
		bundle = ResourceBundle.getBundle("jdbc");
	}
	
	/**
	 * 输入属性文件的名返回名称对应的值
	 * @param propertiesName
	 * @return 	value:属性文件中名称对应的值
	 */
	public static String getValue(String propertiesName){
		return bundle.getString(propertiesName);
	}
}
