package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @_HttpMessageConverter {@link HttpMessageConverter} : spring bean
 * <br>스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.
 * <br>HTTP 요청: @{@link RequestBody} , {@link HttpEntity}({@link RequestEntity}) ,
 * <br>HTTP 응답: @{@link ResponseBody} , {@link HttpEntity}({@link ResponseEntity}) ,
 * @_HandlerMethodArgumentResolver {@link HandlerMethodArgumentResolver} : spring bean X
 * <br>
 * <br>생각해보면, 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다.
 * <br>{@link HttpServletRequest} , {@link Model} 은 물론이고, {@link RequestParam} , {@link ModelAttribute} 같은 애노테이션
 * <br>그리고 {@link RequestBody} , {@link HttpEntity} 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다.
 * <br>이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 {@link HandlerMethodArgumentResolver} 덕분이다.
 * <br>공식 메뉴얼
 * <br> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-arguments
 * @_HandlerMethodReturnValueHandler {@link HandlerMethodReturnValueHandler}
 * <br>{@link HandlerMethodReturnValueHandler} 를 줄여서 ReturnValueHandler 라 부른다.
 * <br>ArgumentResolver 와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
 * <br>컨트롤러에서 String 으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.
 * <br>어떤 종류들이 있는지 살짝 코드로 확인만 해보자.
 * <br>스프링은 10여개가 넘는 {@link HandlerMethodReturnValueHandler} 를 지원한다.
 * <br>예) {@link ModelAndViewMethodReturnValueHandler} , {@link RequestResponseBodyMethodProcessor} , {@link HttpEntityMethodProcessor} , String ...
 * <br>응답 값 목록은 다음 공식 메뉴얼에서 확인할 수 있다.
 * <br>https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-return-types
 * @_HttpMessageConverter위치는? {@link MappingJackson2HttpMessageConverter}
 * <br>HTTP 메시지 컨버터를 사용하는 {@link RequestBody} 도 컨트롤러가 필요로 하는 파라미터의 값에 사용된다.
 * <br>{@link ResponseBody} 의 경우도 컨트롤러의 반환 값을 이용한다.
 * <p>요청의 경우 {@link RequestBody} 를 처리하는 {@link HandlerMethodArgumentResolver} 가 있고, {@link HttpEntity} 를 처리하는 {@link HandlerMethodArgumentResolver} 가 있다.
 * <br>이 ArgumentResolver 들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다. (어떤 종류가 있는지 코드로 살짝 확인해보자)
 * <br>{@link RequestBody} {@link ResponseBody} 가 있으면 RequestResponseBodyMethodProcessor (ArgumentResolver): {@link RequestResponseBodyMethodProcessor#readWithMessageConverters(HttpInputMessage, MethodParameter, Type)}
 * <br>{@link HttpEntity} 가 있으면 HttpEntityMethodProcessor (ArgumentResolver)를 사용한다.: {@link HttpEntityMethodProcessor#readWithMessageConverters(HttpInputMessage, MethodParameter, Type)}
 * </p>
 * <p>응답의 경우 @ResponseBody 와 HttpEntity 를 처리하는 ReturnValueHandler 가 있다. 그리고 여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.
 * <br>@RequestBody @ResponseBody 가 있으면 RequestResponseBodyMethodProcessor (ReturnValueHandler): {@link RequestResponseBodyMethodProcessor#readWithMessageConverters(HttpInputMessage, MethodParameter, Type)}
 * </p>
 *
 * @_WebMvcConfigurer <br> 스프링은 다음을 모두 인터페이스로 제공한다. 따라서 필요하면 언제든지 기능을 확장할 수 있다.
 * <br>{@link HandlerMethodArgumentResolver}
 * <br>{@link HandlerMethodReturnValueHandler}
 * <br>{@link HttpMessageConverter}
 * <br>스프링이 필요한 대부분의 기능을 제공하기 때문에 실제 기능을 확장할 일이 많지는 않다.
 * <br>기능 확장은 {@link WebMvcConfigurer} 를 상속 받아서 스프링 빈으로 등록하면 된다.
 * <br>실제 자주 사용하지는 않으니 실제 기능 확장이 필요할 때 {@link WebMvcConfigurer} 를 검색해보자.
 * <br>{@link WebMvcConfigurer#addArgumentResolvers(List)}
 * <br>{@link WebMvcConfigurer#addReturnValueHandlers(List)}
 */
@Slf4j
@Controller
public class HttpMessageConverterController {
    public HttpMessageConverterController(List<HttpMessageConverter> converters) {
        for (HttpMessageConverter converter : converters) {
            log.info("converter:{}", converter.getClass().getName());
        }
    }

    /**
     * @ByteArrayHttpMessageConverter : byte[] 데이터를 처리한다.
     * <br>클래스 타입: byte[] , 미디어타입: *\/*,
     * <br>요청 예) @RequestBody byte[] data
     * <br>응답 예) @ResponseBody return byte[] 쓰기 미디어타입 application/octet-stream
     * <p>
     */
    @ResponseBody
    @RequestMapping("/http-message-converter-bytes")
    public byte[] convertBytes(@RequestBody byte[] helloDataBytes) throws IOException {
        return helloDataBytes;
    }

    /**
     * @StringHttpMessageConverter : String 문자로 데이터를 처리한다.
     * <br>클래스 타입:String ,미디어타입:*\/*
     * <br>요청 예) @RequestBody String data
     * <br>응답 예) @ResponseBody return "ok" 쓰기 미디어타입 text/plain
     */
    @ResponseBody
    @RequestMapping("/http-message-converter-string")
    public String convertString(@RequestBody String helloDataString) throws IOException {
        return helloDataString;
    }

    /**
     * @MappingJackson2HttpMessageConverter : application/json
     * <br>클래스 타입: 객체 또는 HashMap , 미디어타입 application/json 관련
     * <br>요청 예) @RequestBody HelloData data
     * <br>응답 예) @ResponseBody return helloData 쓰기 미디어타입 application/json 관련
     */
    @ResponseBody
    @RequestMapping("/http-message-converter-json")
    public HelloData convertJson(@RequestBody HelloData helloData) throws IOException {
        return helloData;
    }
}
