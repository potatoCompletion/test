## 회원가입, 로그인, 내역조회, 주문수정 API
- 좀 더 효율적인 REST API 설계 및 프로젝트 구조를 고안하기 위한 프로토타입

### TODO
- 리소스 중심 구조에서 user로 통합하지 말고 세분화 해야함 (피드백)
- CommonResponse 수정. API Code 도입 고안 (피드백)
- 개인 AWS 인스턴스 삭제로 인해 MySQL -> h2 데이터베이스 도입해야함

<가상설정>  
가게주와 배달원 간의 주문중계 플랫폼

<실행환경>  
SDK: java 17 (Amazon Coretto Version 17.0)  
gradle JVM: java 17(Amazon Coretto)  
Server port: 8080

<API 명세서>  
https://docs.google.com/document/d/1pu-vLbkC_akcr_q4DfP_aSu2Di8sbpi0kytCeeGFTBs/edit?usp=sharing

