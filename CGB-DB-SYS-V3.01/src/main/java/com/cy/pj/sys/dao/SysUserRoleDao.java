package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserRoleDao {
	
	
	/**
	 * 根据角色id删除user及角色的关系数据
	 * @param userId
	 * @return
	 */
	@Delete("delete from sys_user_roles where user_id=#{userId}")
	int deleteObjectsByUserId(Integer userId);
	
	/**
	 * 基于用户id查询角色id信息的方法
	 * @param id
	 * @return
	 */
	@Select("select role_id from sys_user_roles where user_id=#{id}")
	List<Integer> findRoleIdsByUserId(Integer id);
	
	int insertObjects(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer[] roleIds);

	
	/**
	 * 基于角色id删除角色和用户的关系数据
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);

}
