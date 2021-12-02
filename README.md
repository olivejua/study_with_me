# Study With Me (스터디 모집)
 
## 주제
나에게 맞는 스터디를 조건으로 검색하여 찾는 웹서비스

## 구현 목표
- 유저
    - 복잡한 회원가입 대신 간단한 소셜로그인을 통해 로그인.
    - 신규회원일 경우, 사이트에서 이용할 닉네임만 설정하면 회원가입 완료.
    - 이름은 원하면 언제든지 변경 가능.

- 스터디 모집 게시판
    - 참여하고 싶은 관련 기술스택, 스터디 모임 장소 등으로 검색 가능.
    - 원하는 스터디는 댓글을 통해 참여 의사를 밝힘.

- 장소 추천 게시판
    - 해당 장소의 썸네일과 지역, 추천, 비추천 수로 한눈에 볼 수 있음.
    - 1명의 User당 1회의 추천, 비추천을 누를 수 있음.
  
  
## 기술스택
|Back-End|Front-End|Tools & ETC|
|:---:|:---:|:---:|
|JAVA 11|HTML|IntelliJ|
|Spring Boot 2.4.4|Thymeleaf|GitHub|
|JPA|AJAX|AWS|
|Junit|JavaScript|
|MariaDB|Bootstrap|
  


## 기능별 구동 화면

1. [유저](/docs/function/user.md)
    - 회원가입 / 로그인
2. 게시판
    1. [스터디 모집](/docs/function/study_recruitment.md)
    2. [장소 추천](/docs/function/place_recommendation.md) 
    3. [질문](/docs/function/question.md)
3. 웹 구축 (AWS 프리티어 기한이 지나 중단했습니다)
    - [웹 사이트 구경하기](http://ec2-15-164-33-81.ap-northeast-2.compute.amazonaws.com:8080/)