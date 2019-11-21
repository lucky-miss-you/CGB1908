package com.tedu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tedu.pojo.Emp;

/**
 * Jdbc回顾,缺点
 * (1)代码过去繁琐(注册驱动,获取连接,获取传输器,执行sql,释放资源)
 * (2)jdbc自身没有连接池,效率低下(可以使用第三方连接池)
 * (3)通过jdbc执行的sql语句,是写死在程序中的.
 * (4)使用jdbc对结果集的处理过于麻烦;
 * 
 *
 */
public class TestJdbc {
	/**
	 * 查询yonghedb库中的emp中的所有员工信息,
	 * 将查询到的信息封装到Emp对象中
	 * 员工对象添加到一个list集合中.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		try {
			//1,注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			//2,获取连接
			conn = DriverManager.getConnection(
					"jdbc:mysql:///jt_db?characterEncoding=utf-8", 
					"root", 
					"root");
			//3,获取传输器
			state = conn.createStatement();
			//执行sql语句
			String sql = "select * from emp";
			//处理结果
			List<Emp> list=new ArrayList<>();
			while(rs.next()){
				Emp emp = new Emp();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setJob(rs.getString("job"));
				emp.setSalary(rs.getDouble("salary"));
				//将Emp对象添加到list集合中
				list.add(emp);
			}
			//遍历list集合
			for (Emp emp : list) {
				System.out.println(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(rs==null){
				try {
					state=null;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					conn=null;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

}
