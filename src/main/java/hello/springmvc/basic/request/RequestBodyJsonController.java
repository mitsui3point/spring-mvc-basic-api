package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info(helloData.toString());

        response.getWriter().write(helloData.toString());
    }

    /**
     * @1. @RequestBody
     * <br> HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * @2. @ResponseBody
     * <br> - 모든 메서드에 @ResponseBody 적용
     * <br> - 메시지 바디 정보 직접 반환(view 조회X)
     * <br> - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-json-v2")
    public @ResponseBody String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info(helloData.toString());

        return helloData.toString();
    }

    /**
     * @1. @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * <br> HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
     * <br> @ModelAttribute 에서 학습한 내용을 떠올려보자.
     * <br> 스프링은 @ModelAttribute , @RequestParam 과 같은 해당 애노테이션을 생략시 다음과 같은 규칙을 적용한다.
     * <br> String , int , Integer 같은 단순 타입 = @RequestParam
     * <br> 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
     * <br> 따라서 이 경우 HelloData에 @RequestBody 를 생략하면 @ModelAttribute 가 적용되어버린다.
     * <br> HelloData data @ModelAttribute HelloData data
     * <br> 따라서 생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.
     */
    @PostMapping("/request-body-json-v3")
    public @ResponseBody String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info(helloData.toString());

        return helloData.toString();
    }

    /**
     * @1. HttpEntity, RequestEntity, @RequestBody 를 사용하면
     * <br>HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
     */
    @PostMapping("/request-body-json-v4")
    public @ResponseBody String requestBodyJsonV4(HttpEntity<HelloData> entity) throws IOException {
        HelloData helloData = entity.getBody();
        log.info(helloData.toString());

        return helloData.toString();
    }

    /**
     * @1. HttpEntity, RequestEntity, @RequestBody 를 사용하면
     * <br>HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
     */
    @PostMapping("/request-body-json-v41")
    public @ResponseBody String requestBodyJsonV41(RequestEntity<HelloData> entity) throws IOException {
        HelloData helloData = entity.getBody();
        log.info(helloData.toString());

        return helloData.toString();
    }

    /**
     * @1. @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * <br> HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
     * @2. @ResponseBody 적용
     * <br> - 메시지 바디 정보 직접 반환(view 조회X)
     * <br> - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
     * <br> (Accept: application/json)
     *
     * @3. @ResponseBody
     * <br> 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
     * <br> 물론 이 경우에도 HttpEntity 를 사용해도 된다.
     * @4. @RequestBody 요청
     * <br> JSON 요청 HTTP 메시지 컨버터 객체
     * @5. @ResponseBody 응답
     * <br> 객체 HTTP 메시지 컨버터 JSON 응답
     */
    @PostMapping("/request-body-json-v5")
    public @ResponseBody HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
        log.info(helloData.toString());

        return helloData;
    }
}
