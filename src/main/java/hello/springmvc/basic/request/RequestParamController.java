package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    @ResponseBody
    public Map<String, Object> param(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("username", request.getParameter("username"));
        result.put("age", Integer.parseInt(request.getParameter("age")));

        log.info("username={}, age={}", result.get("username"), result.get("age"));

        return result;
    }

    /**
     * {@code @RequestParam} 사용<br>
     * - 파라미터 이름으로 바인딩<br>
     * {@code @ResponseBody} 추가<br>
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력<br>
     */
    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String paramV2(@RequestParam("username") String memberName,
                          @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "username=" + memberName + ", age=" + memberAge;
    }

    /**
     * {@code @RequestParam} 사용<br>
     * HTTP 파라미터 이름이 변수 이름과 같으면 {@code @RequestParam(name="xx")} 생략 가능<br>
     */
    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String paramV3(@RequestParam String username,
                          @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "username=" + username + ", age=" + age;
    }

    /**
     * {@code @RequestParam}사용<br>
     * String, int 등의 단순 타입이면 {@code @RequestParam} 도 생략 가능<br>
     */
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String paramV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "username=" + username + ", age=" + age;
    }

    /**
     * {@code @RequestParam.required} / {@code request-param-required} -> username이 없으므로 예외
     * <p>
     * 주의!<br>
     * {@code /request-param-required?username=} -> 빈문자로 통과<br>
     * <p>
     * 주의!<br>
     * {@code /request-param-required}<br>
     * {@code int age} -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는 defaultValue 사용)<br>
     */
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String paramRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "username=" + username + ", age=" + age;
    }

    /**
     * @RequestParam - defaultValue 사용<br>
     * <p>
     * 참고: defaultValue는 빈 문자의 경우에도 적용<br>
     * {@code /request-param-default?username=} <br>
     */
    @RequestMapping("/request-param-default")
    @ResponseBody
    public String paramDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") Integer age) {
        log.info("username={}, age={}", username, age);
        return "username=" + username + ", age=" + age;
    }

    /**
     * {@code @RequestParam Map, MultiValueMap}<br>
     * {@code Map(key=value)}<br>
     * {@code MultiValueMap(key=[value1, value2, ...]) } ex) {@code (key=userIds, value=[id1, id2])}<br>
     */
    @RequestMapping("/request-param-map")
    @ResponseBody
    public String paramDefault(
            @RequestParam MultiValueMap<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username").get(0), paramMap.get("age").get(0));
        return "username=" + paramMap.get("username").get(0) + ", age=" + paramMap.get("age").get(0);
    }

}
