# hunMusic-Ver2
음취헌 음악감상동아리 사이트 Ver.2 - Spring Boot, Refactoring, 기능 추가

## 서비스 소개
동아리 운영과 관련하여 음반 관리, 개인 시간표 입력, 운영 시간표 만들기, 선곡표 만들기 자동화 서비스를 제공합니다.

## hunMusic-Ver.1 히스토리
#### Git Repository   
https://github.com/psh5487/hunheroku

#### 서비스   
https://hunmusic.herokuapp.com

## Ver.2 에서 달라진 점
- Spring Boot 프레임워크 사용
- MSA 전환을 위한 프로젝트 모듈 분리 
- 기능 추가
- 코드 개선

## DB 스키마
<img width="800" alt="아키텍쳐" src="https://user-images.githubusercontent.com/26567880/97251011-74bb2380-184a-11eb-90cc-be64e5efeedc.png">

## 기능 설명

#### 회원가입/로그인   
- Spring Security 와 JWT 사용
- 예외 처리 
- Validation 

#### 음반 리스트 구축
- MySQL과 JPA를 사용하여 음반 정보 CRUD 기능 제공

#### 댓글/좋아요 표시 
- 개인이 음반에 대해 좋아요 표시와 댓글을 남길 수 있음

#### 프로필 사진 등록
- CompletableFuture 로 비동기 처리   
- AWS S3 사용

#### 알림 기능
- Event Bus, Apache Kafka 를 사용하여, 회원 가입시 Push 알림 
<img width="700" alt="아키텍쳐" src="https://user-images.githubusercontent.com/26567880/97253155-13498380-184f-11eb-8a31-a30dfe4f117b.png">

#### 음반 간편 입력 기능 
- Jsoup 크롤링을 사용하여, 바코드 번호만 입력하면 음반 정보가 자동으로 음반 리스트에 들어올 수 있도록 함 

#### 개인 시간표 입력 기능 
- Javascript를 사용하여 UI 에서 본인 시간표를 입력할 수 있도록 함 
- 학생들이 각자의 시간표를 사이트에서 입력하면, 선택된 칸들의 값들이 Ajax를 사용하여 서버로 보내짐 

#### 운영 시간표 짜기 기능 
- 입력된 학생들의 개인 시간표를 바탕으로, 여러 조건 사항이 반영된 한 학기 운영 시간표가 완성됨
- Ver.1 의 시간표 알고리즘 코드 1800줄 -> 300줄 개선

#### 선곡표 만들기 기능 
- 음반 리스트에서 랜덤으로 음반을 뽑아 한 주 선곡표가 만들어짐

#### 배포 
- Heroku 무료 서비스를 사용하여, 일부 기능을 포함한 버전1 사이트 배포

#### 성과
- 기존에는 음반 한 장 입력하는데 30분의 시간이 소요되었지만, 자동화를 통해 3초 안에 입력할 수 있게 됨 
- 매 학기 마다 운영 시간표를 만드는데 1주일을 할애 해야 했었지만, 이 서비스를 통해 1초 안에 짤 수 있도록 함 


 
