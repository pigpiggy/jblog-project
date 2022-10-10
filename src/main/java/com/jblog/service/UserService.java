package com.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.UserDao;
import com.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public Boolean existId(String id) {
		UserVo userVo = userDao.get(id);
		return userVo != null;
	}
	
	public UserService() {
		System.out.println("userService 생성");
	}

	public Boolean join(UserVo userVo) {
		return userDao.insert(userVo);
	}

//	public Boolean newblog(UserVo userVo) {
//		return userDao.newblog(userVo);
//	}
	public UserVo getUser(UserVo userVo) {
		System.out.println("servicemap1:" + userVo);
		return userDao.get(userVo.getId(), userVo.getPassword());
	}
	
	public UserVo getUser(long no) {
		return userDao.get(no);
	}

	public Boolean update(UserVo updateUserVo) {
		return userDao.update(updateUserVo);
	}

}
