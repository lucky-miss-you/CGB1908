package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private PageProperties pageProperties;
	
	
	@Override
	public boolean isExists(String columnName,String columnValue) {
		int rows=sysUserDao.isExist(columnName,columnValue);
		return rows>0;
	}
	
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		 //2.查询用户以及用户对应的部门信息
		 SysUserDeptVo user=sysUserDao.findObjectById(id);
		 if(user==null)
			 throw new ServiceException("用户可能已经不存在");
		 //3.查询用户对应的角色信息
		 List<Integer> roleIds=
		 sysUserRoleDao.findRoleIdsByUserId(id);
		 Map<String,Object> map=new HashMap<>();
		 map.put("user", user);
		 map.put("roleIds",roleIds);
		//4.封装查询结果并返回
		return map;
	}
	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
	    //1.参数校验
		if(entity==null)
		   throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		//.....
		//2.保存用户自身信息
		//2.1创建一个盐值
		String salt=UUID.randomUUID().toString();
		//借助spring中的工具类对密码进行盐值加密
		//String pwd=DigestUtils.md5DigestAsHex((salt+entity.getPassword()).getBytes());
	    //借助shiro中的API完成加密操作
		SimpleHash sh=new SimpleHash(
				"MD5",//algorithmName 加密算法
				 entity.getPassword(), //source 未加密的密码
				 salt,//salt 加密盐 
				 1);//hashIterations 加密次数
		String pwd=sh.toHex();//将加密结果转换为16进制
		entity.setSalt(salt);
		entity.setPassword(pwd);
		int rows=sysUserDao.insertObject(entity);
		//3.保存用户,角色关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		//.....
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("用户可能已经不存在");
		//3.更新关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	
	
	@RequiredLog("valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("无效参数异常");
		if(valid!=1&&valid!=0)
			throw new IllegalArgumentException("状态值不正确");
		//2.更新状态
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		//3.返回结果
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@RequiredLog("select")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值不正确");
		//2.查询总记录数
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.查询当前页记录
		int pageSize=pageProperties.getPageSize();
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records=sysUserDao.findPageObjects(username,
		    		startIndex, pageSize);
		//4.封装查询结果并返回
		return new PageObject<>(records, rowCount, pageCurrent, pageSize);
	}

}
