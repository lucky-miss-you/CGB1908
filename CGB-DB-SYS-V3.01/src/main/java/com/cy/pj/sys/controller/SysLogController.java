package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
/**
 * 基于请求参数执行日志信息的获取
 * @Param username 用户名
 * @param pageCurrent 当前页码值
 * 
 *
 */


@RestController //@Controller + @ResponseBody
@RequestMapping("/log/")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;

	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysLog> pageObject=
				sysLogService.findPageObjects(username,pageCurrent);
		return new JsonResult(pageObject);
	}//spring会借助jackon api将结果转换为json格式字符串

	@RequestMapping("doDeleteObjects")
	public JsonResult doDeleteObjects(Integer... ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");

	}
}
