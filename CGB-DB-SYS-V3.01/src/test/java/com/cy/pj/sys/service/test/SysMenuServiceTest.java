package com.cy.pj.sys.service.test;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.service.SysMenuService;

@SpringBootTest
public class SysMenuServiceTest {
	@Autowired
	private SysMenuService sysMenuService;
	
	@Test
	public void SysMenuServiceTest() {
		List<Map<String, Object>> map = sysMenuService.findObjects();
		for (Map<String, Object> map2 : map) {
			System.out.println(map2);
		}
	}

}
