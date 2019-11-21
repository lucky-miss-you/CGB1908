package com.cy.pj.sys.dao.test;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysMenuDao;

@SpringBootTest
public class SysMenuDaoTests {
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Test
	public void SysMenuDaoTest() {
		Long t1 = System.nanoTime();
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		System.out.println(System.nanoTime()-t1);
	}
}
