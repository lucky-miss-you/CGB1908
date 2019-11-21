package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 封装用户的基本信息 , 然后将数据持久化到数据库
 * @author UID
 */
@Data
public class SysUser implements Serializable{
	private static final long serialVersionUID = -2295877844786783330L;
	private Integer id;
	private String username;
	private String password;
	private String salt;//盐值
	private String email;
	private String mobile;
	private Integer valid=1;
    private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
	

}
