package com.tedu.dao;

import java.util.HashMap;
import java.util.List;

import com.tedu.pojo.Emp;

/***
 *1、创建一个接口，接口的全路径名和mapper文件的namespace值要相同
 *2、mapper文件中每条要执行的SQL语句，在接口中要添加一个对应的方法，
 *并且接口中的方法名和SQL标签上的id值相同
 *3、Mapper接口中方法接收的参数类型，和mapper.xml中定义的sql的接
 *收的参数类型要相同
 *4、接口中方法的返回值类型和SQL标签上的resultType即返回值类型相同
 *(如果方法返回值是集合,resultType只需要指定集合中的泛型)
 */
 /*方法中的参数与sql语句要对应*/
public interface EmpMapper {
	
	/**1,查询所有员工信息*/
	public List<Emp> findAll();
	
	/**2,根据id查询员工信息*/
	public Emp findById(Integer id);
	
	/**3,新增员工信息,马云  教师 800*/
	public int insert2(Emp emp);
	
	/**4,根据id删除员工信息*/
	public int deleteById(Integer id);
	
	/**5,根据ID批量删除员工信息*/
	public int deleteByIds(Integer[] ids);
	
	/**6,根据id修改员工信息*/
	public int updateById(Emp emp);
	
	

}
