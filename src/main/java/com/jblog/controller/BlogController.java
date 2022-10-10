package com.jblog.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.service.AdminService;
import com.jblog.service.BlogService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.CommentsVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

@Controller
@RequestMapping("/")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private AdminService adminService;
	
	// 유저를 생성하면서 블로그를 만들면서... 메인화면에 목록주기
	@RequestMapping(value="{userid}", method=RequestMethod.GET)
	public String newblog(@PathVariable("userid") String userid, Model model, HttpSession session) {
		model.addAttribute("blogVo", blogService.getId(userid));
		Long userNo = blogService.getUserNo(userid);
		model.addAttribute("categoryVo", blogService.getCateListMain(userNo));
		model.addAttribute("postVo", blogService.getdefaultpost(userNo));
		model.addAttribute("postContent", blogService.getdePostContent(userNo));
		System.out.println("commentsssss" + userNo);
		model.addAttribute("commentsVo", blogService.getReply(userNo));
		model.addAttribute("userid", userid);
		System.out.println("blogcontroller:"+userid);
		System.out.println("getdefault" + blogService.getdefaultpost(userNo));
		return "blog/blog-main";
	}
	
	
	// 포스트 전문
	@ResponseBody
	@RequestMapping(value="/{userid}/postmain", method=RequestMethod.GET)
	public List<PostVo> getPostListMain(@PathVariable("userid") String userid, Model model,
			@RequestParam("cateNum") String cateNum) {
		model.addAttribute("blogVo", blogService.getId(userid));
		Long userNo = blogService.getUserNo(userid);
		int cateNo = Integer.parseInt(cateNum);
		return blogService.getPostListMain(cateNo);
	}
	
	@ResponseBody
	@RequestMapping(value="/{userid}/postview", method=RequestMethod.GET)
	public List<PostVo> getPostView(@PathVariable("userid") String userid, Model model,
			@RequestParam("postNum") String postNum) {
		model.addAttribute("blogVo", blogService.getId(userid));
		Long userNo = blogService.getUserNo(userid);
		int postNo = Integer.parseInt(postNum);
		System.out.println("postview" + blogService.getPostView(postNo));
		return blogService.getPostView(postNo);
	}
	
	@ResponseBody
	@RequestMapping(value="/{userid}/getCommentsList", method=RequestMethod.GET)
	public List<CommentsVo> getCommentsList(@PathVariable("userid") String userid, Model model,
			@RequestParam("postNum") String postNum) {
		model.addAttribute("blogVo", blogService.getId(userid));
		Long userNo = blogService.getUserNo(userid);
		int postNo = Integer.parseInt(postNum);
		System.out.println("getcommentlist:" + postNo);
		return blogService.getCommentsList(postNo);
	}
	
	@ResponseBody
	@RequestMapping(value="/{userNo}/addReply", method=RequestMethod.GET)
	public List<CommentsVo> addReply(@RequestParam("userNum") String userNum, Model model,
			@RequestParam("postNum") String postNum,
			@RequestParam("name") String name,
			@RequestParam("replyContent") String replyContent) {
		System.out.println("addReplycontroller");
		int postNo = Integer.parseInt(postNum);
		Long userNo = Long.parseLong(userNum);
		CommentsVo commentsVo = new CommentsVo();
		commentsVo.setUserNo(userNo);
		commentsVo.setCoName(name);
		commentsVo.setCmtContent(replyContent);
		commentsVo.setPostNo(postNo);
		return blogService.addReply(commentsVo);
	}
	
	//댓글 삭제할 번호 가져오기
	@ResponseBody
	@RequestMapping(value="/deleteReply", method=RequestMethod.GET)
	public List<CommentsVo> deleteReply(Model model, 
			@RequestParam("cmtNum") int cmtNo,
			@RequestParam("postNum") int postNo) {
		System.out.println("deleteReplycontroller");
		CommentsVo commentsVo = new CommentsVo();
		commentsVo.setCmtNo(cmtNo);
		commentsVo.setPostNo(postNo);
		return blogService.deleteReply(commentsVo);
	}
	
}






