# Litb-lib 1.0.3
2018年5月11日   	V1.0.3更新内容
* 添加了简单的httputil,可直接调用get和post来进行数据传输
-------------------------------
2018年4月23日	V1.0.2更新内容  
* 添加了时间的工具类
-------------------------------
2018年4月22日		V1.0.1更新内容:  
* 1.添加了jdbc的增删改,同时修改上个版本中执行完sql后连接关闭后线程没有释放,以至于后面的查询提示连接已经关闭的bug
-------------------------------
项目成立时间:2018年4月21日      
-------------------------------
环境要求:需要jar1.6以上.
-------------------------------
* 1.com.Litb.jdbc.util目前只是mysql连接,需要mysql的驱动包支持.其他数据库没有测试.
	* JDBCUtil.executeQuery()   sql查询 操作   * @param( sql	预编译的sql语句,parameters	预编译的sql语句占位符对应的参数值)
