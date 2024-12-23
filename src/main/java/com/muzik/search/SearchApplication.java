// JPA 감사 활성화

package com.muzik.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// JPA 감사(Auditing)을 활성화
// 엔티티의 레코드의 생성 및 수정 시간을 자동 기록하는데 사용
@EnableJpaAuditing
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
// 배포파일 war 지정하면 ServletInitializer 파일이 존재
/*
SpringBootServletInitializer는 무엇이고 왜 상속받고 있는가?

SpringBoot 웹 애플리케이션을 배포할 때는 주로 embedded tomcat이 내장된 jar파일을 이용한다.
하지만 특별한 경우에는 전통적인 배포 방식인 war 파일로 배포를 진행해야 하는 경우가 있을 수 있다.
이럴 경우 SpringBootServletInitializer를 상속받으면 된다.

즉, war 파일로 빌드하고 배포하지 않을 거라면 SpringBootServletInitializer를 상속할 필요가 없다는 의미다.


그럼 왜 SpringBoot 웹 애플리케이션을 war로 배포할 때 SpringBootServletInitializer를 상속해야 하는 걸까?

Spring 웹 애플리케이션을 외부 Tomcat에서 동작하도록 하기 위해서는 web.xml (Deployment Descriptor, DD)에 애플리케이션 컨텍스트를 등록해야만 한다.
이는, Apache Tomcat(Servlet Container)이 구동될 때 /WEB-INF 디렉토리에 존재하는 web.xml을 읽어 웹 애플리케이션을 구성하기 때문이다.

하지만 Servlet 3.0 스펙으로 업데이트되면서 web.xml이 없어도 동작이 가능해졌다.
이는, web.xml 설정을 WebApplicationInitializer인터페이스를 구현하여 대신할 수 있게 됐고,
프로그래밍적으로 ServletContext에 Spring IoC 컨테이너(AnnotationConfigWebApplicationContext)를 생성하여 추가할 수 있도록 변경됐기 때문이다.

이와 비슷한 맥락에서, web.xml이 없는 SpringBoot 웹 애플리케이션을 외부 Tomcat에서 동작하도록 하기 위해서는
WebApplicationInitializer 인터페이스를 구현한 SpringBootServletInitializer를 상속을 받는 것이 필요했던 것이다.

*/

// 4. 주요 작업 폴더(패키지)를 생성
// Config, Controller, Entity, DTO, Repository, Service, Util, Contant
// Config : Bean 등록(변수, 메소드), 보안, 로그인
// Controller : 요청에 따라 맵핑처리(HTML, Service간에 연결)
// DTO : 변수그룹, HTML과 Service에 자료를 전송
// Entity : 변수그룹(테이블정보), Service와 Repository에 자료를 전송 (데이터베이스 사용안하면 필요없음)
// Repository : 데이터베이스 처리를 위한 SQL문 구성
// Service : 내부적으로 처리할 내용(데이터베이스 처리)
// Util : 독립적인 메소드를 구성(날짜처리, 페이지처리, AI, AWS, 채팅 프로그램, 이메일)
// Constant : 고정화된 값을 처리(열거형)
//          ex) 상품에 대해서 : 신상품, 추천상품, 세일상품