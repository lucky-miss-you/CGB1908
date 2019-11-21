package com.cy.pj.sys.dao.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogDaoTests {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	//对数据进行从测试
	@Test
	public void SysLogDaotest() {
		int rows = sysLogDao.getRowCount("");
		System. out.println(rows);
	}
	
	//删除测试
	@Test
	public void testDeleteObjects() {
		int rows = sysLogDao.deleteObjects(29,50);
		System.out.println(rows);
	}
	
	@Test
	public void testFindPageTest() {
		List<SysLog> list = sysLogDao.findPageObjects("admin", 0,15);
		System.out.println(list.size());
	}

}
