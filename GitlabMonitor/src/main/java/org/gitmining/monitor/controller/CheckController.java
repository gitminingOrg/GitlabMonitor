package org.gitmining.monitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.service.UserService;
import org.gitmining.monitor.util.ResultMap;
import org.gitmining.monitor.util.URLMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CheckController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value=URLMapping.CHECK_RRGISTER_ITEM)
	public ResultMap checkRegisterItem(HttpServletRequest request,HttpServletResponse response){
		ResultMap resultMap = new ResultMap();
		String item = request.getParameter("item");
		String value = request.getParameter("value");
		if(value == null || item == null || value.length() == 0 || !(item.equals("name") || item.equals("email"))){
			resultMap.setStatus(ResultMap.FAIL_STATUS);
			resultMap.setInfo("unsupported request!");
		}else{
			boolean valid = userService.checkUserNotExist(item, value);
			if(!valid){
				resultMap.setStatus(ResultMap.FAIL_STATUS);
				resultMap.setInfo(item + " already exists, please change");
			}else{
				resultMap.setStatus(ResultMap.SUCCESS_STATUS);
				resultMap.setInfo("ok");
			}
		}
		return resultMap;
	}
}
