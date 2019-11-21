package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {//PageDao
	
	
	/**
	 * 添加查询角色id , name的相关方法
	 * @return
	 */
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
	
	/**
	 * 在接口中增加数据更新方法
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	
	/**
	 * 基于id执行角色数据的查询操作
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);

	/**
	 * 负责将角色自身信息写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);

	/**
	 * 基于id删除角色自身信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);

	/**
	 *  查询角色名称查询该角色的总记录数
	 * @param name 角色名称
	 * @return
	 */
	int getRowCount(@Param("name")String name);

	/**
	 * 分页查询当前页记录
	 * @param name 角色名称
	 * @param startIndex 起始位置
	 * @param pageSize  页面大小
	 * @return
	 */
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
}
