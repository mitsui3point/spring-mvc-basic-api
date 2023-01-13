package hello.springmvc.basic.request;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestHeaderController.class)
public class RequestHeaderControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MockMvc mvc;

    @Test
    void headerTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(
                get("/headers")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.ALL_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .locale(Locale.KOREAN)
                        .header("host", "localhost:8080")
                        .cookie(new Cookie("myCookie", "myCookie"))
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.request").value("org.springframework.mock.web.MockHttpServletRequest"))
                .andExpect(jsonPath("$.response").value("org.springframework.mock.web.MockHttpServletResponse"))
                .andExpect(jsonPath("$.method").value("GET"))
                .andExpect(jsonPath("$.cookie").value("myCookie"))
                .andExpect(jsonPath("$.host").value("localhost:8080"))
                .andExpect(jsonPath("$.header.Content-Type").value("text/plain;charset=UTF-8"))
                .andExpect(jsonPath("$.header.Accept").value("*/*"))
                .andExpect(jsonPath("$.header.Accept-Language").value("ko"))
                .andExpect(jsonPath("$.locale").value("ko"));

    }
}
