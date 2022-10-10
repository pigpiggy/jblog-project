package com.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;

@Repository
public class AdminDao {

	@Autowired
	private SqlSession sqlSession;

	public BlogVo getId(String id) {
		System.out.println(id);
		return sqlSession.selectOne("admin.getById", id);
	}

	public Boolean modify(BlogVo blogVo) {
		int count = sqlSession.update("admin.modify", blogVo);
		System.out.println("modifydao: " + blogVo);

		return 1 == count;
	}

	// 카테고리 리스트 출력
	public List<CategoryVo> getList(Long userNo) {
		return sqlSession.selectList("admin.getList", userNo);
	}

	// 카테고리 삭제
	public boolean delete(CategoryVo catevo) {
		int count = sqlSession.delete("admin.delete", catevo);
		return 1 == count;
	}

	public List<CategoryVo> insertcate(CategoryVo categoryVo) {
		sqlSession.insert("admin.insertcate", categoryVo); //받아오는 값이 없으므로 return이 필요없
		return sqlSession.selectList("admin.selectcate"); //max를 찾아오니까 인자가 필요 없음??? 변수를 하나도 안넣어줬음 테이블 자체만으로도...
	}
	
	public List<CategoryVo> getCateNoName(Long userNo){
		return sqlSession.selectList("admin.cateNoName", userNo);
	}
	
	public void insertpost(PostVo writepost) {
		sqlSession.insert("admin.insertpost", writepost);
	}
}
