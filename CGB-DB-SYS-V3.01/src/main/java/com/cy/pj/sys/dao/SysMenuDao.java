package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

@Mapper
//@CacheNamespaceRef(name="com.cy.pj.sys.dao.SysMenuDao")
public interface SysMenuDao {
	
	/**
	 * 基于菜单id查找权限标识
	 */
	List<String> findPermissions(@Param("menuls") Integer[] menuIds);
	
	int updateObject(SysMenu entity);
	
	/**将菜单信息持久化到数据库*/
	int insertObject(SysMenu entity);
	
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	/**
	 * 基于id统计子菜单的个数
	 * @param id
	 * @return
	 */
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
	
	
	/**
	 * 基于id删除菜单自身信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	
	/**
	 * 基于id查询所有的菜单信息
	 * @param id
	 * @return
	 */
	@Select("Select * from sys_menus where id=${id}")
	Map<String,Object> findById(Integer id);
	List<Map<String,Object>> findObjects();

}
