package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

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
