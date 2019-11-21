package com.tedu.mybatis;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.tedu.pojo.Emp;

public class TestMybatis {
	public static void main(String[] args) throws Exception {
		//1,读取mybatis核心配置文件(mybatis-config.xml)
		InputStream in = Resources.getResourceAsStream(
				"mybatis-config.xml");//in流中包含了两个文件的配置信息
		//2,通过配置信息获取一个SqlSessionFactory工厂
		SqlSessionFactory fac = 
				new SqlSessionFactoryBuilder().build(in);
		//3,通过工厂获取SqlSession对象
		SqlSession session = fac.openSession();//connection
		
		//4,执行sql语句 Emp-mapper,返回处理后的结果(List<Emp>)
		//练习1,查询所有员工的信息
		List<Emp> list = session.selectList("EmpMapper.findAll");//写EmpMapper.xml中的namespace
		//5,输出结果
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}
}
