1. DB = 강의 보고 과제했더니 mySQL로만 해야하는 줄 알고 SQL로 작성했었습니다. 아래는 테이블 명세입니다
   create table record
(
    recordId   bigint auto_increment comment '게시글식별번호'
        primary key,
    writer     varchar(100)  not null comment '작성자',
    password   varchar(100)  not null comment '비밀번호',
    title      varchar(100)  not null comment '게시글제목',
    writedDate varchar(100)  not null comment '작성일',
    contents   varchar(1000) not null comment '게시글본문'
);

2. 강의 보고 과제했더니 HTML까지 해야하는 줄 알고 HTML로 작성했습니다.
   
3. API, UML, ERD 명세: 과제 제출시엔 제출란이 없어서 수기를 문서로 옮기지 않았었는데 과제Lv2가 급한지라 일단 다음부턴 문서화해보도록 하겠습니다.
   하지만 API명세는 테스트를 위해 작성해야겠지요
   생성: POST, /record/create, body
   전부조회: GET, /record/read/all, param
   세부조회: (전부조회시 JS에 전해진 목록에서 해당id만의 데이터를 재로드)
   수정: PUT, /record/update, param(타겟id)+body(수정데이터)
   삭제: DELETE, /record/delete, param

   ![E8ejAYKp](https://github.com/it-is-wanthefull/hh99_spring_lv1/assets/153047019/431745fe-e9c2-427f-9c77-a2dccce8bf91)
