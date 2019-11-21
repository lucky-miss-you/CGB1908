package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfig {
     
	
	
	
	/**
	 * 配置bean对象的生命周期
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
			 return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 通过此配置为目标业务对象创建代理对象
	 */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
			 return new DefaultAdvisorAutoProxyCreator();
	}
	/**
	 * 配置advisor对象,shiro框架底层会通过此对象的matchs
	 * 方法返回值决定是否创建代理对象,进行权限控制
	 */
	 @Bean
	 public AuthorizationAttributeSourceAdvisor 
	 newAuthorizationAttributeSourceAdvisor(
	 	    		    @Autowired SecurityManager securityManager) {
	 		        AuthorizationAttributeSourceAdvisor advisor=
	 				new AuthorizationAttributeSourceAdvisor();
	 advisor.setSecurityManager(securityManager);
	 	return advisor;
	 }

	/**
       * @Bean 注解描述的方法，表示方法的返回值
       * 对象要交给Spring管理，默认key为方法名
       * SecurityManager对象是Shiro框架的核心
       * 注意：此对象为org.apache.shiro.mgt
       * 包中的对象。
       * @return
       */
	  @Bean //<bean id="" class=""/>
	  public SecurityManager securityManager(
			  Realm realm) {
		  DefaultWebSecurityManager sManager=
				 new DefaultWebSecurityManager();
		  sManager.setRealm(realm);
		  return sManager;
	  }
	  @Bean
	  public ShiroFilterFactoryBean shiroFilterFactory(
			  SecurityManager securityManager) {
		  ShiroFilterFactoryBean fBean=
				  new ShiroFilterFactoryBean();
		  fBean.setSecurityManager(securityManager);
		  fBean.setLoginUrl("/doLoginUI");
		  LinkedHashMap<String,String> filterMap=new LinkedHashMap<>();
		  //设置允许匿名访问的资源
		  filterMap.put("/bower_components/**","anon");
		  filterMap.put("/build/**","anon");
		  filterMap.put("/dist/**","anon");
		  filterMap.put("/plugins/**","anon");
		  filterMap.put("/user/doLogin","anon");
		  filterMap.put("/doLogout","logout");
          //设置必须认证才可访问的资源(需要认证访问的资源，需要放在map最后)
		  filterMap.put("/**", "authc");
		  fBean.setFilterChainDefinitionMap(filterMap);
		  return fBean;
	  }
}




