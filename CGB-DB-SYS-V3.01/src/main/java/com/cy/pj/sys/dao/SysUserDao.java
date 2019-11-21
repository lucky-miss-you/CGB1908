package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	
	@Select("select count(*) from sys_users where ${columnName}=#{columnValue}")
	int isExist(String columnName,String columnValue);
	
	
	/**
	 * 根据entity封装的信息进行数据更新
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	
	/**
	 * 基于用户id查询用户相关信息的方法
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	 
	int insertObject(SysUser entity);
	
	/**
	 * 添加修改用户状态信息的方法
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);

	
	/**
	 * 按条件统计总记录数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username") String username);
	
	/**
	 * 实现当前页记录的分页 查询操作
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptVo> findPageObjects(
		      @Param("username")String  username,
		      @Param("startIndex")Integer startIndex,
		      @Param("pageSize")Integer pageSize);

}
