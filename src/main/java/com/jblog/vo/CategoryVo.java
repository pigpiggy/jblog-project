package com.jblog.vo;

import lombok.Data;

@Data
public class CategoryVo {
   private int cateNo;
   private Long userNo;
   private String cateName;
   private int countPost;
   private String description;
   private String regDate;
   
}
