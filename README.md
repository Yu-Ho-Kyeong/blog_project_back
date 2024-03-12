# Blog Project

이 프로젝트는 개인 블로그를 구축하기 위한 웹 어플리케이션입니다. 사용자는 이 웹 어플리케이션을 통해 자신의 블로그 게시물을 작성하고 관리할 수 있습니다.

## 주요기능

- 사용자 인증 및 로그인: 사용자는 계정을 생성하고 로그인하여 자신의 블로그에 접근할 수 있습니다.
- 게시물 관리: 사용자는 블로그에 게시물을 작성, 수정, 삭제할 수 있습니다.
- 카테고리 및 태그: 게시물에 카테고리와 태그를 지정하여 분류할 수 있습니다.
- 댓글 기능: 사용자는 게시물에 댓글을 작성할 수 있습니다.
- 이미지 업로드: 게시물에 이미지를 업로드하여 포함할 수 있습니다.

## ㅇㅇㅇ
/api/getUsers  					        모든 유저정보 조회
/api/login 						        유저 로그인
/api/signup						        유저 회원가입
/api/refreshAccessToken	 		        accesstoken 만료시 refreshAccessToken발급
/api/getIntro/{userNo}				    회원 소개글 조회
/api/user/deleteUser				    회원 탈퇴 
/api/admin/updateIntro			        소개글 수정
/api/admin/getAdminSetInfo/{userNo}	    관리자 정보조회(설정페이지)
/api/admin/adminUpdateInfo		        관리자 정보 수정(설정페이지)
/api/admin/adminUpdateBlogName	        관리자 블로그명수정(설정페이지)
/api/admin/uploadImg				    관리자 프로필업로드(설정페이지)

/api/board/getBoards				    전체 게시글 정보 조회
/api/board/getBoardsWithTag		        태그별 게시글 정보 조회
/api/board/getBoard/{boardNo}		    특정 게시글 정보 조회
/api/board/admin/uploadImg		        게시글 이미지 업로드
/api/board/admin/saveImg			    게시글 이미지 저장
/api/board/admin/saveBoard			    게시글 저장
/api/board/admin/saveBoard			    게시글 수정
/api/board/admin/delete/{boardNo}	    게시글 삭제
/api/board/getPreBoard/{boardNo}		이전 게시글 정보 조회
/api/board/getNextBoard/{boardNo}	    다음 게시글 정보 조회
/api/board/maxBoardNo			        게시글번호 max값 찾기
/api/board/minBoardNo			        게시글번호 min값 찾기

/api/comment/getCount/{boardNo}					        게시글 댓글 count 조회
/api/comment/getReCount/{boardNo}/{parentCommentNo}	    자식댓글 count 조회
/api/comment/getComment/{boardNo}					    댓글 정보 조회
/api/comment/user/addComment/{boardNo}				    댓글 등록
/api/comment/user/modifyComment/{commentNo}			    댓글 수정
/api/comment/user/deleteComment/{commentNo}			    댓글 삭제
