package com.jblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.AdminDao;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	public BlogVo getId(String id) {
		System.out.println("adminservice:" + id);
		return adminDao.getId(id);
	}
	
	//아무 값이 반환되지 않아서 사용
	public Boolean modify(BlogVo blogVo) {
		System.out.println("modifyservice: " + blogVo);
		return adminDao.modify(blogVo);
	}
	
	//카테고리 리스트 Dao로 이동
	   public ArrayList<CategoryVo> getList(Long userNo){
	      ArrayList<CategoryVo> obj = (ArrayList<CategoryVo>) adminDao.getList(userNo);
	      for(int i=0; i<obj.size(); i++) { //값 넘어오는지 확인
	         System.out.println(obj.get(i) + "list");
	      }
	      return obj;
	   }
	
	//카테고리 삭제 Dao로 이동
	   public Boolean delete(CategoryVo categoryvo) {
	      return adminDao.delete(categoryvo);
	   }
	
	//카테고리 추가 Dao로 이동
	   public ArrayList<CategoryVo> insertcate(CategoryVo categoryVo) {
		   ArrayList<CategoryVo> obj = (ArrayList<CategoryVo>) adminDao.insertcate(categoryVo);
		   return obj;
	   }
	
	//포스트 추가 Dao로 이동
	  public List<CategoryVo> getCateNoName(Long userNo){
		  return adminDao.getCateNoName(userNo);
	  }

	  public void insertpost(PostVo writepost) {
		  adminDao.insertpost(writepost);
	  }
}
