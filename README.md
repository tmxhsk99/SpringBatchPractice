# 0.0 개요

- 개인적으로 Spring Batch를 공부하기 위해 만든 프로젝트 입니다.

# 1.0 개발 환경
- java 17
- Spring Boot 3.2.2
- Spring Batch 5.1.0

# 2.0 프로젝트 셋팅
## 2.1 Active Profile 확인
- 현재는 local Profile 밖에 없으므로 jar 실행시 local Profile 설정을 적용하여 실행시킨다. 

``` 
java -Dspring.profiles.active=local -jar {project_jar_name}.jar
```

## 2.2 DB 셋팅
### 2.2.1 DB 종류 및 환경 설정
- 회사에서 주로 사용하는 DB가 마리아 DB 이기 때문에 Maria DB를 사용 한다.
- Maria DB는 로컬 DB를 사용 하건 Docker를 사용 하건 본인의 환경에 맞게 셋팅 하면 된다.

### 2.2.2 Maria DB 셋팅 (Window 기준)
### 대소문자 구분 추가 설정
- Maria DB는 기본적으로 대소문자를 구분하지 않는다.
  - 대소문자를 구분하고 싶다면 설정을 추가 해야 한다.
  - 설정 위치: {설치경로}\mariadb\data\my.ini
  - 설정 내용  
 
```ini
[mariadb] 
lower_case_table_names=2
```
- 설정 후 Maria DB 재시작 

### 2.2.3 SPRING BATCH 기본 테이블 생성
    - 테이블 생성은 Spring Batch에서 제공하는 스크립트를 사용한다.
    - 스크립트 위치: org.springframework.batch.core

