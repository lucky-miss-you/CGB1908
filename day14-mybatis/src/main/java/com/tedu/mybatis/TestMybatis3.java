package com.tedu.mybatis;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.tedu.pojo.Emp;

public class TestMybatis3 {
	/**1,查询所有员工的信息*/
	@Test
	public void testFindAll() throws Exception{
		//1,读取mybatis核心配置文件
		InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
		//2,通过配置信息获取一个SqlSessionFactory工厂
		SqlSessionFactory fac = new SqlSessionFactoryBuilder().build(in);
		//3,通过工厂获取一个SqlSession对象
		SqlSession session = fac.openSession();
		//4,执行sql语句并返回结果
		List<Emp> list = session.selectList("EmpMapper.findAll");
		//遍历list集合,并打印
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}
	
	/**2,新增员工信息: 刘德华 歌手 888
	 * @throws Exception */
	@Test
	public void testInsert() throws Exception{
		//1,读取mybatis核心配置文件信息
		InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
		//2,通过配置文件获取一个SqlSessionFactory
		SqlSessionFactory fac = new SqlSessionFactoryBuilder().build(in);
		//3,通过工厂创建一个session
		SqlSession session = fac.openSession();
		//4,执行sql语句并返回执行结果
		int rows = session.update("EmpMapper.insert");
		//5,提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}
	
	/**3,修改员工信息, 将刘德华的job改为'歌手&演员 ,salary改为88888
	 * @throws Exception */
	@Test
	public void testUpdate() throws Exception{
		//1,读取核心配置文件信息
		InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
		//2,根据核心配置文件创建SqlSessionFactory工厂
		SqlSessionFactory fac = new SqlSessionFactoryBuilder().build(in);
		//3,通过工厂创建一个session
		SqlSession session = fac.openSession();
		//4,执行sql语句并返回执行结果
		int rows = session.update("EmpMapper.update");
		//5,提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}
	
	/**4,删除name为'刘德华'的记录
	 * @throws Exception */
	@Test
	public void testDelete() throws Exception{
		//1,读取核心配置文件
		InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
		//2,根据核心配置文件创建SqlSessionFactory工厂
		SqlSessionFactory fac = new SqlSessionFactoryBuilder().build(in);
		//3,通过工厂创建session对象
		SqlSession session = fac.openSession();
		//4,通过session对象执行sql语句,并返回执行结果
		int rows = session.update("EmpMapper.delete");
		//5,提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
		
	}

}
