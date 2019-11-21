package com .cy.pj.sys.service;

import java.util.Map;


import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService {
	
	boolean isExists(String columnName,String columnValue);	
	/**
	 * 基于角色id更新角色及用户的关系数据
	 * @param entity  用户封装信息
	 * @param roleIds 角色id
	 * @return
	 */
	int updateObject(SysUser entity,Integer[] roleIds);
	
	/**
	 * 基于用户id查询基于用户表的相关数据,并将数据封装到map集合中
	 * @param userId
	 * @return
	 */
	Map<String, Object> findObjectById(Integer userId);
	
	int saveObject(SysUser entity,Integer [] roleIds);
	
	int validById(Integer id,Integer valid,String modifiedUser);
	
	PageObject<SysUserDeptVo> findPageObjects(String uesrname,Integer pageCurrent);
	
	

}
