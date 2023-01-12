package hello.springmvc.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController}<br>
 * {@link Controller} 는 반환 값이 String 이면 뷰 이름으로 인식된다.<br>
 * 그래서 뷰를 찾고 뷰가 랜더링 된다.<br>
 * {@link RestController}는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.<br>
 * 따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있는데, 뒤에서 더 자세히 설명한다.<br>
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class LogTestController {
    /**
     * 로깅 라이브러리<br>
     * 스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리( spring-boot-starter-logging )가 함께 포함된다.<br>
     * 스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.<br>
     * SLF4J - http://www.slf4j.org <br>
     * Logback - http://logback.qos.ch <br>
     * 로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데,<br>
     * 그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리다.<br>
     * 쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.<br>
     * 실무에서는 스프링 부트가 기본으로 제공하는 Logback을 대부분 사용한다.<br>
     * <p>
     * 로그 선언<br>
     * private Logger log = LoggerFactory.getLogger(getClass());<br>
     * private static final Logger log = LoggerFactory.getLogger(Xxx.class)<br>
     *
     * @Slf4j : 롬복 사용 가능
     */
//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        System.out.println("name = " + name);
        //2023-01-12T19:53:23.992+09:00  INFO 19500 --- [nio-8080-exec-3] h.springmvc.basic.LogTestController      : info log = Spring
        //날짜/시간                       /로그종류/pid--- [실행한 thread 명] 패키지.패키지.Class                        : 로그메세지
        /**
         * 2023-01-12T20:00:17.172+09:00  INFO 7092 --- [nio-8080-exec-1] h.springmvc.basic.LogTestController      : info log = Spring
         * 2023-01-12T20:00:17.173+09:00  WARN 7092 --- [nio-8080-exec-1] h.springmvc.basic.LogTestController      : warn log = Spring
         * 2023-01-12T20:00:17.173+09:00 ERROR 7092 --- [nio-8080-exec-1] h.springmvc.basic.LogTestController      : error log = Spring
         */
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        //올바른 로그 사용법
        log.debug("name=" + name);
        /*
        로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다.
        log.debug() 를 실행하기 전 파라미터에 들어갈 문자열 ("name=" + name)을 이미 연산해 버린다.
        결과적으로 문자 더하기 연산이 발생한다.
         */
        log.debug("name={}", name);
        /*
        로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다.
        log.debug() 를 실행하기 전에는 파라미터 2개 ("name={}", name)를 넘긴것 뿐이기에, 연산없이 메소드 파라미터로 넘어간다.
        따라서 앞과 같은 의미없는 연산이 발생하지 않는다.
         */
        return "ok";
    }
}
