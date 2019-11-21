package com.cy.pj.sys.service.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@SpringBootTest
public class SysLogServiceTests {
	@Autowired
	private SysLogService sysLogService;
	
	@Test
	public void DeleteObjectsTest() {
		int rows = sysLogService.deleteObjects(37,38,50);
		System.out.println(rows);
	}
	
	@Test
	public void SysLogServiceTest() {
		PageObject<SysLog> findPageObjects = sysLogService.findPageObjects("admin", 4);
		System.out.println(findPageObjects.getPageSize());
		System.out.println(findPageObjects.getPageCount());
		System.out.println(findPageObjects);
	}
}
