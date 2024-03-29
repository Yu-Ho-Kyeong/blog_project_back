# Blog Project

'VELOG'를 참조하여 만든 웹 어플리케이션입니다. 


## 주요기능

- 사용자 인증 및 로그인: 계정을 생성하고 로그인하여 자신의 블로그에 접근할 수 있습니다.
- 게시물 관리: Admin 계정에 로그인할 경우 블로그에 게시물을 작성, 수정, 삭제할 수 있습니다.
- 게시물 태그: 게시물에 태그를 지정하여 분류할 수 있으며 태그별 게시물을 불러올 수 있습니다.
- 댓글 기능: 회원가입시 게시물에 무한댓글, 대댓글을 작성할 수 있습니다.
- 이미지 업로드: 회원정보에서 프로필사진을 업로드할 수 있습니다.

## URL
- /api/getUsers &nbsp;&nbsp;&nbsp;모든 유저정보 조회
- /api/login &nbsp;&nbsp;&nbsp;유저 로그인
- /api/signup&nbsp;&nbsp;&nbsp;유저 회원가입
- /api/refreshAccessToken&nbsp;&nbsp;&nbsp; accesstoken 만료시 refreshAccessToken발급
- /api/getIntro/{userNo} &nbsp;&nbsp;&nbsp;회원 소개글 조회
- /api/user/deleteUser &nbsp;&nbsp;&nbsp;회원 탈퇴 
- /api/admin/updateIntro &nbsp;&nbsp;&nbsp;소개글 수정
- /api/admin/getAdminSetInfo/{userNo} &nbsp;&nbsp;&nbsp;관리자 정보조회(설정페이지)
- /api/admin/adminUpdateInfo &nbsp;&nbsp;&nbsp;관리자 정보 수정(설정페이지)
- /api/admin/adminUpdateBlogName &nbsp;&nbsp;&nbsp;관리자 블로그명수정(설정페이지)
- /api/admin/uploadImg &nbsp;&nbsp;&nbsp;관리자 프로필업로드(설정페이지)

- /api/board/getBoards &nbsp;&nbsp;&nbsp;전체 게시글 정보 조회
- /api/board/getBoardsWithTag &nbsp;&nbsp;&nbsp;태그별 게시글 정보 조회
- /api/board/getBoard/{boardNo}&nbsp;&nbsp;&nbsp;특정 게시글 정보 조회
- /api/board/admin/uploadImg&nbsp;&nbsp;&nbsp;게시글 이미지 업로드
- /api/board/admin/saveImg&nbsp;&nbsp;&nbsp;게시글 이미지 저장
- /api/board/admin/saveBoard&nbsp;&nbsp;&nbsp;게시글 저장
- /api/board/admin/saveBoard&nbsp;&nbsp;&nbsp;게시글 수정
- /api/board/admin/delete/{boardNo}&nbsp;&nbsp;&nbsp;게시글 삭제
- /api/board/getPreBoard/{boardNo}&nbsp;&nbsp;&nbsp;이전 게시글 정보 조회
- /api/board/getNextBoard/{boardNo}&nbsp;&nbsp;&nbsp;다음 게시글 정보 조회
- /api/board/maxBoardNo&nbsp;&nbsp;&nbsp;게시글번호 max값 찾기
- /api/board/minBoardNo&nbsp;&nbsp;&nbsp;게시글번호 min값 찾기

- /api/comment/getCount/{boardNo}&nbsp;&nbsp;&nbsp;게시글 댓글 count 조회
- /api/comment/getReCount/{boardNo}/{parentCommentNo}&nbsp;&nbsp;&nbsp;자식댓글 count 조회
- /api/comment/getComment/{boardNo}&nbsp;&nbsp;&nbsp;댓글 정보 조회
- /api/comment/user/addComment/{boardNo}&nbsp;&nbsp;&nbsp;댓글 등록
- /api/comment/user/modifyComment/{commentNo}&nbsp;&nbsp;&nbsp;댓글 수정
- /api/comment/user/deleteComment/{commentNo}&nbsp;&nbsp;&nbsp;댓글 삭제
