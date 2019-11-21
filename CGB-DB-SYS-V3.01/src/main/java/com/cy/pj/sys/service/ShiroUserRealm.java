package com.cy.pj.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	/**
	 * 设置凭证匹配器(加密方式)
	 */
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		//构建凭证匹配对象
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
		//设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		return cMatcher;
	}//也可以重写setCredentialsMatcher方法

	/**
	 * 负责认证信息的获取和封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1.获取用户提交的信息
		UsernamePasswordToken uToken=
				(UsernamePasswordToken)token;
		String username=uToken.getUsername();
		//2.基于用户提交信息查询当前用户
		SysUser user=
				sysUserDao.findUserByUserName(username);
		//3.检测用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//4.检测用户是否被禁用
		if(user.getValid()==0)
			throw new LockedAccountException();
		//5.封装用户信息，交给SecurityManager进行认证
		ByteSource credentialsSalt=
				ByteSource.Util.bytes(user.getSalt());
		return new SimpleAuthenticationInfo(
				user, //principal身份
				user.getPassword(),//hashedCredentials
				credentialsSalt,
				getName());
	}
	/**
	 * 负责授权信息的获取和封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1,获取登录用户信息,例如用户id
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		Integer userId=user.getId();
		//2,基于用户id获取用户拥有的角色(sys-user-roles)
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		if (roleIds==null || roleIds.size()==0) {
			throw new AuthorizationException();
		}
		//3,基于角色id获取菜单id(sys_user_menus)
		Integer [] array= {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(array);
		if (menuIds==null || menuIds.size()==0) {
			throw new AuthorizationException();
		}
		//4,基于菜单id获取权限标识(sys_menus)
		List<String> permission = sysMenuDao.findPermissions(menuIds.toArray(array));
		//5,对权限标识信息进行封装并返回
		Set<String> set = new HashSet<>();
		for (String per : permission) {
			if (org.springframework.util.StringUtils.isEmpty(per)) {
				set.add(per);
			}
		}
		SimpleAuthorizationInfo info=
				new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;//返回给授权管理器
	}

}
