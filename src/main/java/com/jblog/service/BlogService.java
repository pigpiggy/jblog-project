package com.jblog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.BlogDao;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.CommentsVo;
import com.jblog.vo.PagingVo;
import com.jblog.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	public BlogVo getId(String id) {
		System.out.println("blogservice:" + id);
		return blogDao.getId(id);
	}
	
	public List<CategoryVo> getCateListMain(Long userNo) {
		return blogDao.getCateListMain(userNo);
	}
	
	public List<PostVo> getPostListMain(int cateNo) {
		return blogDao.getPostListMain(cateNo);
	}
	
	public List<PostVo> getdefaultpost(Long userNo){
		return blogDao.getdefaultpost(userNo);
	}
	
	public List<PostVo> getPostView(int postNo) {
		return blogDao.getPostView(postNo);
	}
	
	public PostVo getdePostContent(Long userNo){
		return blogDao.getdePostContent(userNo);
	}

	public Long getUserNo(String userid) {
		return blogDao.getUserNo(userid);
		
	}
	
	public List<CommentsVo> getReply(Long userNo){
		return blogDao.getReply(userNo);
	}
	
	public ArrayList<CommentsVo> getCommentsList(int postNo){
		ArrayList<CommentsVo> obj = (ArrayList<CommentsVo>) blogDao.getCommentsList(postNo);
		   for(int i=0; i<obj.size(); i++) { //값 넘어오는지 확인
		         System.out.println(obj.get(i) + "commentlist");
		      }
		return obj;
	}
	
	public ArrayList<CommentsVo> addReply(CommentsVo commentsVo){
		   ArrayList<CommentsVo> obj = (ArrayList<CommentsVo>) blogDao.addReply(commentsVo);
			System.out.println("addReplyservice" + commentsVo);

		   return obj;
		   
	}
	
	public List<CommentsVo> deleteReply(CommentsVo commentsVo) {
		return blogDao.deleteReply(commentsVo);
	}

	public Boolean modify(BlogVo blogVo) {
		System.out.println("modifyservice: " + blogVo);
		return blogDao.modify(blogVo);
	}

	
	public Long getUser(long userNo) {
		return blogDao.getUser(userNo);
	}
	
}
