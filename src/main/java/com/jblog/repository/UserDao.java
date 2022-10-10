package com.jblog.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.jblog.exception.UserDaoException;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.UserVo;



@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
//	@Autowired
//	private DataSource dataSource;
	
	public UserDao() {
		System.out.println("userDao 생성");
	}
	public Boolean update(UserVo vo) {
		int count  = sqlSession.update("user.update",vo);
		return 1 == count;
	}

	public UserVo get(long userNo) {
		return sqlSession.selectOne("user.getByUserNo", userNo);
	}
	
	public UserVo get(String id) {
		return sqlSession.selectOne("user.getById", id);
	}

	public UserVo get(String id, String password) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		
		System.out.println("map: " + map);
		UserVo userVo = sqlSession.selectOne("user.getByIdAndPassword", map);
		System.out.println("userVomap: " + userVo);

		return userVo;
	}

	public boolean insert(UserVo vo) {
		System.out.println(vo);
		int count = sqlSession.insert("user.insert",vo);
		sqlSession.insert("user.newblog", vo);
		Long userNo = sqlSession.selectOne("user.getUserNo", vo);
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setUserNo(userNo);
		sqlSession.insert("user.defaultCate", categoryVo);
		System.out.println(vo);
		return 1==count;
	}

//	public boolean newblog(UserVo vo) {
//		System.out.println(vo);
//		int count = sqlSession.insert("user.newblog",vo);
//		System.out.println(vo);
//		return 1==count;
//	}
	
}
