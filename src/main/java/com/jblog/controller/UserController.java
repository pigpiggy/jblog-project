package com.jblog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jblog.service.UserService;
import com.jblog.vo.UserVo;
import com.security.Auth;
import com.security.AuthUser;


@Controller
@RequestMapping("/user")
public class UserController {
	
	public UserController() {
		System.out.println("userController 생성");
	}
	@Autowired
	private UserService userService;
	
	//회원가입 폼
	@RequestMapping(value="/joinForm",method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/joinForm";
	}
	//회원가입 동작
	@RequestMapping(value="/join",method = RequestMethod.POST)
	public String join(@ModelAttribute  @Valid UserVo userVo, BindingResult result,Model model) {
		
		//Valid 체크가 틀릴 시, join form으로 넘김
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			model.addAllAttributes(result.getModel()); // Map으로 보내줌
			return "user/join";
		}
		userService.join(userVo);
		//userService.newblog(userVo);
		System.out.println("newblog:" +userVo);
		return "redirect:/user/joinsuccess";//dispatcher가 컨텍스트 패스를 붙이고 다시 리다이렉트를 보낸다.
	}
	//회원가입 성공
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		
		return "user/joinSuccess";
	}
	
	@RequestMapping(value="/loginForm",method = RequestMethod.GET)
	public String login() {
		
		return "user/loginForm";
	}
	
	@Auth
	@RequestMapping( value="/update", method=RequestMethod.GET )
	public String update(@AuthUser UserVo authUser,Model model ){
		UserVo userVo = userService.getUser( authUser.getUserNo() );
		model.addAttribute( "userVo", userVo );
		return "user/update";
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public String update(@ModelAttribute UserVo updateUserVo,HttpSession session,Model model) {
		System.out.println(updateUserVo);
		boolean result = userService.update(updateUserVo);
		if(result) {
			session.setAttribute("authUser", updateUserVo);
		}
		return "user/updatesuccess";
	}
//	컨트롤러에서 처리
//	@ExceptionHandler(Exception.class)
//	public String handleUserDaoException() {
//		return "error/exception";
//	}

//}
//로그인 동	
@RequestMapping(value="/auth",method = RequestMethod.POST)
public String login(@RequestParam(value="id",required = true,defaultValue = "")String id,
					@RequestParam(value="password",required = true,defaultValue = "")String password,
					HttpSession session,
					Model model) {

	UserVo authUser = userService.getUser(new UserVo(id,password));
	
	System.out.println("usercontroller:" + id + password);
	if(authUser == null) {
		model.addAttribute("result", "fail");
		return "/user/loginForm";
	}
	
	//session 처리(지금은 HttpSession을 사용(기술침투)하지만 나중엔 제거 예정-interceptor)
	session.setAttribute("authUser", authUser);
	
	System.out.println("usercontroller2:" + id + password);

	return "redirect:/user/loginsuccess";
}
//로그인 성공
@RequestMapping("/loginsuccess")
public String loginSuccess() {
	
	return "redirect:/";
}

@RequestMapping(value="/logout",method = RequestMethod.GET)
public String logout(HttpSession session) {
	
	session.removeAttribute("authUser");
	return "redirect:/";
}
}