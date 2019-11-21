package com.cy.pj.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	
	/**
	 * 查询角色id,及name
	 * @return
	 */
	List<CheckBox> findObjects();
	
	/**
	 * 在SysRoleService接口中,添加用于更新角色对象的方法
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[] menuIds);
	
	SysRoleMenuVo findObjectById(Integer id) ;
	/**
	 * 添加用于更新角色对象的方法
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	 int saveObject(SysRole entity,Integer[] menuIds);
	
	/**
	 * 基于条件查询每页显示的记录
	 * @param name
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysRole> findPageObjects(
			String name,Integer pageCurrent);

	/**
	 * 基于角色id删除角色以及角色对应的关系数据
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
}
