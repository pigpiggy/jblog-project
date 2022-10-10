package com.jblog.controller;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jblog.service.AdminService;
import com.jblog.service.BlogService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;
import com.security.Auth;

@Auth(role = Auth.Role.ADMIN)
@Controller
@RequestMapping("/")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private BlogService blogService;

	// 기본 설정 화면
	@RequestMapping(value = "/{id}/admin/basic", method = RequestMethod.GET)
	public String blogbasic(@PathVariable("id") String id, Model model) {
		model.addAttribute("blogVo", blogService.getId(id));
		System.out.println("id: " + id);
		return "blog/admin/blog-admin-basic";
	}

	// 프로필 수정 실행
	@PostMapping(value = "/{userId}/admin/modify")
	public String profileUpload(@PathVariable("userId") String userId, Model model, MultipartFile[] file,
			@RequestParam("blogTitle") String blogTitle, @ModelAttribute BlogVo blogVo) {
		String uploadFolder = "/Users/pyuteo/spring-workspace/jblog/src/main/webapp/assets/images";
		blogVo = adminService.getId(userId);
		for (MultipartFile multipartFile : file) {

			blogVo.setLogoFile(multipartFile.getOriginalFilename());
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
			} // end catch
		} // end for

		blogVo.setBlogTitle(blogTitle);
		adminService.modify(blogVo);
		System.out.println(blogVo);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin/blog-admin-basic";
	}

	// 카테고리 관리 페이지
	@RequestMapping(value="{id}/admin/category", method=RequestMethod.GET)
	public String blogcate(@PathVariable("id") String id, HttpSession session, Model model) {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		id = userVo.getId();
		model.addAttribute("blogVo", blogService.getId(id));
		System.out.println("blogcontrollerblogcate: " + id);
		return "blog/admin/blog-admin-cate";
	}
	
	// 카테고리 리스트 보여줌
	@ResponseBody //ajax 쓰려면 이거 쓰기 (값을 받아오기 위해서)
	@RequestMapping(value = "/catelist", method = RequestMethod.GET)
	public List<CategoryVo> getCateList(HttpSession session) {
		UserVo userVo = (UserVo)session.getAttribute("authUser"); //세션에 저장된 authUser를 UserVo에 가져온다...
		Long userNo = userVo.getUserNo();
		System.out.println("AdminController");
		return adminService.getList(userNo);
	}
	
	// 카테고리(방명록) 삭제할 번호 가져오기
	@RequestMapping(value = "{userid}/delete/{cateNo}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable(value = "cateNo") int cateNo, 
			@PathVariable(value = "userid") String userid) {
		model.addAttribute("cateNo", cateNo);
		return "redirect:/" + userid + "/delete";
	}

	// 카테고리(방명록) 삭제 처리
	@RequestMapping(value="{userid}/delete", method = RequestMethod.GET)
		   public String delete(@ModelAttribute CategoryVo categoryvo,Model model,
				   @PathVariable(value = "userid") String userid) {
		      boolean result =adminService.delete(categoryvo);
		      if(result) {
		         return "redirect:/" + userid + "/admin/category";
		      }else {
		         model.addAttribute("result", "fail");
		         return "redirect:/" + userid + "delete/" + categoryvo.getCateNo();
		      }
	}
	
	//카테고리 추가
	@ResponseBody
	@PostMapping(value="{userid}/admin/insertcate")
	public List<CategoryVo> insertcate(Model model, //리스트에 추가한 값(마지막넘버)을 불러온다.
			@PathVariable(value="userid") String userid, HttpSession session, String catename, String description) { //이렇게만 써도 값이 받아와
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setUserNo(userVo.getUserNo());
		categoryVo.setCateName(catename);
		categoryVo.setDescription(description);
	
		return adminService.insertcate(categoryVo);
	}
	
	//포스트 추가에서 카테고리 가져오는 용
	//카테고리 넘버와 네임 가져오는 용도
	@RequestMapping(value="{id}/admin/write", method=RequestMethod.GET)
	public String blogwrite(@PathVariable("id") String id, HttpSession session, Model model) {
		model.addAttribute("blogVo", blogService.getId(id));
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		Long userNo = userVo.getUserNo();
		model.addAttribute("categoryVo", adminService.getCateNoName(userNo)); //userNo가 같은 애들만 카테고리 넘버 네임 가져오게
		return "blog/admin/blog-admin-write";
	}

	//포스트 추가
	@PostMapping(value="{userid}/admin/insertpost")
	public String insertpost(Model model, //리스트에 추가한 값(마지막넘버)을 불러온다.
			@PathVariable(value="userid") String userid, HttpSession session, 
			@ModelAttribute PostVo writepost) { 
		adminService.insertpost(writepost);
		
		return "redirect:/" + userid + "/admin/write";
	}
	

}
