package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public Map<String, Object> param(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("username", request.getParameter("username"));
        result.put("age", Integer.parseInt(request.getParameter("age")));

        log.info("username={}, age={}", result.get("username"), result.get("age"));

        if (request.getMethod().equals(HttpMethod.GET)) {
            return result;
        }

        response.getWriter().write("username="+result.get("username")+", age="+result.get("age"));
        return null;
    }
}
