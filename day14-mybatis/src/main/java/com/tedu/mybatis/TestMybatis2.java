package com.tedu.mybatis;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.tedu.pojo.Emp;

public class TestMybatis2 {
	private static SqlSession session=null;
	static{
		try {
			//1,读取mybatis核心配置文件(mybatis-config.xml)
			InputStream in = Resources.getResourceAsStream(
					"mybatis-config.xml");//in流中包含了两个文件的配置信息
			//2,通过配置信息获取一个SqlSessionFactory工厂
			SqlSessionFactory fac = 
					new SqlSessionFactoryBuilder().build(in);
			//3,通过工厂获取SqlSession对象
			session = fac.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**练习1:查询所有员工的信息*/
	@Test
	public void testFindAll(){
		//执行sql语句,返回处理后的结果
		List<Emp> list = session.selectList("EmpMapper.findAll");
		//遍历员工信息集合
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}

	/**练习2: 新增员工信息: 刘德华 歌手 888 */
	@Test
	public void testInsert(){
		//执行sql语句,返回处理后的结果
		int rows = session.update("EmpMapper.insert");
		//提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**<!-- 练习3：修改员工信息 -->*/
	@Test
	public void testUpdate(){
		//执行sql语句,返回处理后的结果
		int rows = session.update("EmpMapper.update");
		//提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**<!-- 练习4: 删除name为'刘德华'的记录 -->*/
	@Test
	public void testDelete(){
		//执行sql语句,返回处理后的结果
		int rows = session.update("EmpMapper.delete");
		//提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/*===========#{}占位符的使用==============*/
	/**练习5:查询emp表中指定id的员工信息*/

	@Test
	public void testFindById(){
		//4,执行sql语句,并返回执行结果
		Emp emp = session.selectOne("EmpMapper.findById",4);//定向指定+查询参数 
		System.out.println(emp);
	}

	/**练习6:新增员工信息 :张飞 java开发工程师 15000
	 * #{}占位符中的变量名和数据表中的列名没有关系
	 * 如果传递的参数是多个,可以将 多个参数封装到map中再传递
	 * 要求#{}占位符中的变量名和map集合中的key保持一致
	 * 
	 * 如果将多个参数封装到pojo对象中再传递也可以,要求:
	 * #{}占位符中的变量名和pojo对象中的属性名保持一致
	 * 或者说#{}占位符中的变量名要和pojo对象中的getter方法相对应
	 * */
	@Test
	public void testInsert2(){
		Emp emp = new Emp();
		emp.setName("张飞");
		emp.setJob("java开发工程师");
		emp.setSalary(15000d);

		//		Map map = new HashMap();
		//		map.put("name", "张飞");//key参照的是映射文件中的占位符中的值
		//		map.put("job", "java开发工程师");
		//		map.put("salary", 15000);
		//4,执行sql语句,并返回执行结果
		int rows = session.update("EmpMapper.insert2",emp);
		session.commit();
		System.out.println("影响行数:"+rows);	
	}

	/**练习7,通过id修改对应员工的信息*/
	@Test
	public void testUpdateById(){
		//4,执行 sql语句(先在镜像文件中书写sql语句)并返回执行结果
		int rows = session.update("EmpMapper.updateById",14);
		//提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**练习8 通过id删除员工信息*/
	@Test
	public void testDeleteById(){
		//4,书写并执行sql语句,返回执行结果
		int rows = session.update("EmpMapper.updateById",14);
		//5,提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**练习9 :修改员工信息:张飞  架构师 25000*/
	@Test
	public void testUpdate2(){
		Emp emp = new Emp();
		emp.setName("张飞");
		emp.setJob("架构师");
		emp.setSalary(25000d);
		int rows = session.update("EmpMapper.update2", emp);
		//提交事务
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**==========${}占位符的使用=============
	 * #{}占位符起始就是jdbc中的问号(?)占位符
	 * ${}占位符可以为sql片段或者不带引号的字符串进行占位
	 * 大部分情况下不使用${}占位符
	 * */

	/**练习10 动态指定查询的列
	 * */
	@Test
	public void testFindAll2(){
		Map map = new HashMap();
		map.put("cols", "id,name");
		List<Emp> list = session.selectList("EmpMapper.findAll2",map);
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}

	/**练习11 根据name 模糊查询emp表 使用${}占位符*/

	@Test
	public void testFindAll3(){
		Map map = new HashMap();
		map.put("name", "刘");
		List<Emp> list = session.selectList("EmpMapper.findAll3",map);
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}

	/**练习11-2根据name 模糊查询emp表 使用#{}占位符*/
	@Test
	public void testFindAll4(){
		String name="张";
		List<Emp> list = session.selectList("EmpMapper.findAll4","%"+name+"%");
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}

	/**===========动态SQL标签===========*/
	/*
	 * if标签:if元素用于对某一字段进行判断,比如根据判断传过来的参数是否为空，从而决定是否执行包含在其中的SQL片段
	 * where标签:where元素则用于对包含在其中的SQL语句进行检索,需要时会剔除多余的连接词（比如and或者or），并且在需要时可以添加where关键词
	 * set标签:根据需要生成set关键字并且可以根据需要去除多余的连接符
	 * `for-each标签
	 */

	/**练习12,根据薪资查询员工信息 3000~5000
	 * 不输:默认查询所有与员工的薪资
	 * 只传一个最低薪资:  ....where sal>=最低薪资
	 * 只传一个最高薪资:  ....where sal<=最高薪资
	 * 传递最高薪资和最低薪资:  ..where sal<=最高薪资and sal>=最低薪资
	 * */
	@Test
	public void testFindAll5(){
		Map map = new HashMap<>();
		map.put("minSal", 3000);
		map.put("maxSal", 5000);
		List<Emp> list = session.selectList("EmpMapper.findAll5",map);
		for (Emp emp : list) {
			System.out.println(emp);
		}
	}

	/**练习13:根据参数传递与否,修改表中的列*/
	@Test        
	public void testUpdate3(){
		Emp emp = new Emp();
		emp.setName("关云长");
		emp.setId(14);
		int rows = session.update("EmpMapper.update3",emp);
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**练习14:根据id批量删除员工信息*/
	@Test
	public void testDeleteByIds(){
		Integer[] ids = {1,3,5,7};
		int rows = session.update("EmpMapper.deleteByIds",ids);
		session.commit();
		System.out.println("影响行数:"+rows);
	}

	/**练习15,批量更新员工的薪资 Integer[] ids = {2,4,6,8}*/
	@Test
	public void testUpdateByIds(){
		Integer[] ids = {2,4,6,8};
		int rows = session.update("EmpMapper.updateByIds", ids);
		session.commit();
		System.out.println("影响行数:"+rows);
	}



}
