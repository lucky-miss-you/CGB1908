package com.tedu.mybatis;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.tedu.dao.EmpMapper;
import com.tedu.pojo.Emp;
/**测试mapper接口开发*/
public class TestMybatis {
 private static SqlSession session = null;
 static{
  try {
   
   InputStream in = 
     Resources.getResourceAsStream("mybatis-config.xml");
   SqlSessionFactory fac = 
     new SqlSessionFactoryBuilder().build(in);
   session = fac.openSession();
  } catch (Exception e) {
   e.printStackTrace();

  }
 }
 /**1,查询员工信息*/
 @Test
 public void testFindAll(){
  //获取EmpMapper接口的子类实例
  EmpMapper mapper = session.getMapper(EmpMapper.class);
  //调用findAll方法查询所有员工信息  
  List<Emp> list = mapper.findAll();
  for (Emp emp : list) {
   System.out.println(emp);
  }
  
  /*
   *(1) 调用getMapper方法根据EmpMapper接口字节码对象,
   * 框架底层提供接口的实现类,并根据实现类创建实例
   * (2)根据接口的子类实例调用findAll方法,查询员工所有信息
   * 接口全路劲=namespace+sql标签的id*/
  }
  /** 2.根据id查询员工信息*/
  @Test
  public void testFindById(){
   //获取EmpMapper的子类实例
   EmpMapper mapper = session.getMapper(EmpMapper.class);
   //调用findById方法
   Emp emp = mapper.findById(2);
   System.out.println(emp);
  }
  /** 3.新增员工信息 马云 教师*/
  @Test
  public void testInsert(){
   EmpMapper mapper = session.getMapper(EmpMapper.class);
   Emp emp = new Emp();
   emp.setName("马云");
   emp.setJob("教师");
   emp.setSalary(888d);
   int rows = mapper.insert2(emp);
   session.commit();
   System.out.println(rows);
  }
  
  /** 4.根据id修改员工信息 */
  @Test
  public void testUpdateById(){
   EmpMapper mapper = session.getMapper(EmpMapper.class);
   Emp emp = new Emp();
   emp.setId(18);
   emp.setName("张三");
   emp.setJob("董事");
   emp.setSalary(25000D);
   int rows = mapper.updateById(emp);
   session.commit();
   System.out.println("影响行数:"+rows);
  }
   
  public void testDeleteById(){
   EmpMapper mapper = session.getMapper(EmpMapper.class);
   session.commit();
  }
 
}