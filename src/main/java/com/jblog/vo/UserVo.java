package com.jblog.vo;

import lombok.Data;

@Data
public class UserVo {
	
	public UserVo(String id, String password) {
		this.id=id;
		this.password=password;
	}
	public UserVo() {
		// TODO Auto-generated constructor stub
	}
	private Long userNo;
	private String id;
	private String userName;
	private String password;
	private String joinDate;

}
