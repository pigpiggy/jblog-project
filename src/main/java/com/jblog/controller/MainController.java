package com.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping({"/","main"})
	public String main() {
	  System.out.println("main....");
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>안녕하세요?</h1>";//한글깨짐
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public UserVo hello2() {
		UserVo vo = new UserVo();
		vo.setUserNo(11L);
		vo.setId("hgd");
		vo.setUserName("홍길동");
		
		return vo;//한글깨짐
	}
}
