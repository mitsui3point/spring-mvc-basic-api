package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public Map<String, Object> headers(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod method,
            Locale locale,
            @RequestHeader MultiValueMap<String, String> header,
            @RequestHeader(value = "host", required = false) String host,
            @CookieValue(value = "myCookie", required = false) String cookie
    ) {
        log.info("request = {}", request);
        log.info("response = {}", response);
        log.info("method = {}", method);
        log.info("locale = {}", locale);
        log.info("header = {}", header);
        log.info("host = {}", host);
        log.info("cookie = {}", cookie);
        Map<String, Object> result = new HashMap<>();
        result.put("request", request.getClass().getName());
        result.put("response", response.getClass().getName());
        result.put("method", method.name());
        result.put("locale", locale.toString());
        result.put("header", header);
        result.put("host", header.get("host"));
        result.put("cookie", cookie);
        return result;
    }
}
