--jblog 계정생성, 권한설정
create user jblog identified by jblog ;
grant resource, connect to jblog ;

--테이블 삭제
drop table comments;
drop table post;
drop table category;
drop table blog;
drop table users;

--시퀀스 삭제
drop sequence seq_users_no;
drop sequence seq_category_no;
drop sequence seq_post_no;
drop sequence seq_comments_no;


CREATE SEQUENCE seq_users_no
INCREMENT BY 1
START WITH 1 
NOCACHE ;


CREATE SEQUENCE seq_category_no
INCREMENT BY 1
START WITH 1 
NOCACHE ;


CREATE SEQUENCE seq_post_no 
INCREMENT BY 1
START WITH 1 
NOCACHE ;


CREATE SEQUENCE seq_comments_no
INCREMENT BY 1
START WITH 1 
NOCACHE ;


--테이블 / 시퀀스 만들기
CREATE TABLE users (
  userNo    NUMBER,
  id        VARCHAR2(50)  NOT NULL Unique,
  userName  VARCHAR2(100) NOT NULL,
  password  VARCHAR2(50)  NOT NULL,
  joinDate  DATE          NOT NULL,
  PRIMARY KEY(userNo)
);

CREATE TABLE blog (
  userNo    NUMBER,
  blogTitle VARCHAR2(200) NOT NULL,
  logoFile  VARCHAR2(200),
  PRIMARY KEY(userNo),
  CONSTRAINT c_blog_fk FOREIGN KEY (userNo)
  REFERENCES users(userNo)
);

CREATE TABLE category (
  cateNo        NUMBER,
  userNo        NUMBER, 
  cateName      VARCHAR2(200)   NOT NULL,
  description   VARCHAR2(500),
  regDate       DATE            NOT NULL,
  PRIMARY KEY(cateNo),
  CONSTRAINT c_category_fk FOREIGN KEY (userNo)
  REFERENCES blog(userNo)
);

CREATE TABLE post (
  postNo        NUMBER,
  cateNo        NUMBER,  
  postTitle     VARCHAR2(300)   NOT NULL,
  postContent   VARCHAR2(4000),
  regDate       DATE            NOT NULL,
  PRIMARY KEY(postNo),
  CONSTRAINT c_post_fk FOREIGN KEY (cateNo)
  REFERENCES category(cateNo)
);

CREATE TABLE comments (
  cmtNo         NUMBER,
  postNo        NUMBER,
  userNo		NUMBER,
  cmtContent    VARCHAR2(300)   NOT NULL,
  regDate       DATE            NOT NULL,
  PRIMARY KEY(cmtNo),
  CONSTRAINT c_comment_fk FOREIGN KEY (postNo)
  REFERENCES post(postNo),
  CONSTRAINT c_users_fk FOREIGN KEY (userNo)
  REFERENCES users(userNo)
);

alter table COMMENTS add coname varchar (25);


INSERT INTO USERS(userNo, id, USERNAME, PASSWORD, JOINDATE)
VALUES (SEQ_USERS_NO.nextval, 'hgd', '홍길동', '1234', sysdate);
INSERT INTO USERS values(seq_users_no.nextval, 'haena', '김해나', '1234', sysdate);
INSERT INTO USERS values(seq_users_no.nextval, 'ljj', '이정재', '1234', sysdate);

INSERT INTO BLOG values(1, '홍길동의 블로그 입니다.', 'spring-logo.jpg');
INSERT INTO BLOG values(2, '김해나의 블로그 입니다.', 'spring-logo.jpg');
INSERT INTO BLOG values(3, '이정재의 블로그 입니다.', 'spring-logo.jpg');

INSERT INTO CATEGORY values(seq_category_no.nextval, 1, '미분류', '기본으로 만들어지는 카테고리 입니다.', sysdate);
INSERT INTO CATEGORY values(seq_category_no.nextval, 2, '스프링MVC', '부트 설정과 사용법', sysdate);
INSERT INTO CATEGORY values(seq_category_no.nextval, 3, '코딩MVC', '코딩 설정과 사용법', sysdate);

INSERT INTO post values(seq_post_no.nextval, 2, '스프링 시작', '스프링이란', sysdate);
INSERT INTO post values(seq_post_no.nextval, 2, '스프링 끝', '스프링 과제 제출합니다.', sysdate);
INSERT INTO post values(seq_post_no.nextval, 3, '부트 시작', '부트란', sysdate);
INSERT INTO post values(seq_post_no.nextval, 3, '부트 끝', '부트 과제 제출합니다.', sysdate);

INSERT INTO COMMENTS values(seq_comments_no.nextval, 1, 1, '좋은 글 감사합니다.', sysdate, '이정재');
INSERT INTO COMMENTS values(seq_comments_no.nextval, 2, 2, '반갑습니다.', sysdate, '김해나');
INSERT INTO COMMENTS values(seq_comments_no.nextval, 2, 2, '댓글 달기 어렵네요.', sysdate, '홍길동');
INSERT INTO COMMENTS values(seq_comments_no.nextval, 3, 3, '댓글 너무 어려웡.', sysdate, '홍길동');
INSERT INTO COMMENTS values(seq_comments_no.nextval, 3, 3, '저녁 시간에 댓글을 달자.', sysdate, '김해나');

SELECT *
FROM USERS;

SELECT *
FROM BLOG;

SELECT *
FROM CATEGORY;

SELECT *
FROM POST;

SELECT *
FROM COMMENTS;

