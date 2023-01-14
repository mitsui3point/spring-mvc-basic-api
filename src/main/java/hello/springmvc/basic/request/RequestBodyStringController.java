package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는 {@link RequestParam} ,{@link ModelAttribute} 를 사용할 수 없다. <br>
     * (물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)<br>
     * 먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.<br>
     * HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다.<br>
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        response.getWriter().write(messageBody);
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회<br>
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력<br>
     *
     * @apiNote https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-methods
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        responseWriter.write(messageBody);
    }

    /**
     * 스프링 MVC는 다음 파라미터를 지원한다.<br>
     * <p>
     * {@link HttpEntity}: HTTP header, body 정보를 편리하게 조회<br>
     * : 메시지 바디 정보를 직접 조회<br>
     * : 요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X<br>
     * <p>
     * {@link HttpEntity}는 응답에도 사용 가능<br>
     * : 메시지 바디 정보 직접 반환<br>
     * : 헤더 정보 포함 가능<br>
     * : view 조회X<br>
     * : {@link HttpEntity} 를 상속받은 다음 객체들도 같은 기능을 제공한다.<br>
     * <p>
     * {@link RequestEntity}<br>
     * : {@link HttpEntity}, url 정보가 추가, 요청에서 사용<br>
     * <p>
     * {@link ResponseEntity}<br>
     * : HTTP 상태 코드 설정 가능, 응답에서 사용<br>
     * : {@code return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)}
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> entity) throws IOException {
        String messageBody = entity.getBody();

        log.info("messageBody={}", messageBody);
        return new HttpEntity<>(messageBody);
    }

    @PostMapping("/request-body-string-v31")
    public ResponseEntity<String> requestBodyStringV31(RequestEntity<String> entity) throws IOException {
        String messageBody = entity.getBody();

        log.info("messageBody={}", messageBody);

        if (!StringUtils.hasText(messageBody)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(messageBody, HttpStatus.CREATED);
    }

    /**
     * @1. @RequestBody
     * <br> @RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다.
     * <br> 참고로 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
     * <br> 이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam , @ModelAttribute 와는 전혀 관계가 없다.
     * @2. 요청 파라미터 vs HTTP 메시지 바디
     * <br>요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
     * <br> HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody
     * @3. @ResponseBody
     * <br> @ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
     * <br> 물론 이 경우에도 view를 사용하지 않는다.
     * @RequestBody
     * <br> 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * <br> HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * @ResponseBody
     * <br> 메시지 바디 정보 직접 반환(view 조회X)
     * <br> HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v4")
    public @ResponseBody String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        return messageBody;
    }

    /**
     * request body, request header
     */
    @PostMapping("/request-body-string-v41")
    public @ResponseBody String requestBodyStringV41(@RequestBody String messageBody, @RequestHeader MultiValueMap<String, Object> headers) throws IOException {
        headers.forEach((key, values) -> {
            if (values.getClass().equals(ArrayList.class)) {
                values.forEach(value -> log.info("key={}, value={}", key, value));
            }
        });
        log.info("messageBody={}", messageBody);
        return messageBody;
    }
}