package com.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.CommentsVo;
import com.jblog.vo.PagingVo;
import com.jblog.vo.PostVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public BlogVo getId(String id) {
		System.out.println(id);
		return sqlSession.selectOne("blog.getById",id);
	}
	
	public Boolean modify(BlogVo blogVo) {
		int count = sqlSession.update("blog.modify", blogVo);
		System.out.println("modifydao: " + blogVo);
		
		return 1==count;
	}
	
	public Long getUser(long userNo) {
		return sqlSession.selectOne("blog.getByUserNo", userNo);
	}
	
	public List<CategoryVo> getCateListMain(Long userNo) {
		return sqlSession.selectList("blog.CateListMain", userNo);
	}
	
	public List<PostVo> getPostListMain(int cateNo) {
		return sqlSession.selectList("blog.PostListMain", cateNo);
	}
	
	public List<PostVo> getdefaultpost(Long userNo) {
		return sqlSession.selectList("blog.getdefaultpost", userNo);
	}
	
	public List<PostVo> getPostView(int postNo) {
		return sqlSession.selectList("blog.getPostView", postNo);
	}
	
	public PostVo getdePostContent(Long userNo){
		return sqlSession.selectOne("blog.getdePostContent", userNo);
	}
	
	public Long getUserNo(String userid) {
		return sqlSession.selectOne("blog.getUserNo", userid);
	}
	
	public List<CommentsVo> getReply(Long userNo){
		return sqlSession.selectList("blog.getReply", userNo);
	}
	
	public List<CommentsVo> getCommentsList(int postNo){
		return sqlSession.selectList("blog.getCommentsList", postNo);
	}
	
	public List<CommentsVo> addReply(CommentsVo commentsVo){
		sqlSession.insert("blog.addReply", commentsVo);
		System.out.println("addReplydao" + commentsVo);
		return sqlSession.selectList("blog.firstReply");
	}
	
	public List<CommentsVo> deleteReply(CommentsVo commentsVo) {
		sqlSession.delete("blog.deleteReply", commentsVo);
		return sqlSession.selectList("blog.deleteReplyList", commentsVo);
	}
	
	
}
