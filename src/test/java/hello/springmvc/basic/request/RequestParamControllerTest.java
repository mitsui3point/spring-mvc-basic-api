package hello.springmvc.basic.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RequestParamController.class)
public class RequestParamControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void requestParamV1GetTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/request-param-v1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("username","userA")
                .param("age","15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV1PostTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(post("/request-param-v1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("username","userA")
                .param("age","15"));
        //then
        perform.andDo(print()).andExpect(status().isOk());

    }
}
