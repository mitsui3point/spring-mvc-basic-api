package hello.springmvc.basic.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestParamController.class)
public class RequestParamControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void requestParamV1GetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-v1")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().json("{\"age\":15,\"username\":\"userA\"}"));
    }

    @Test
    void requestParamV1PostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-v1")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().json("{\"age\":15,\"username\":\"userA\"}"));
    }

    @Test
    void requestParamV2GetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-v2")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV2PostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-v2")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV3GetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-v3")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV3PostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-v3")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV4GetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-v4")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamV4PostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-v4")
                .param("username", "userA")
                .param("age", "15"));
        //then
        perform.andDo(print())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamRequiredGetTest() throws Exception {
        //when
        ResultActions performBadRequest = mvc.perform(get("/request-param-required")
                .param("age", "15"));
        ResultActions perform = mvc.perform(get("/request-param-required")
                .param("username", "userA"));

        //then
        performBadRequest.andDo(print())
                .andExpect(status().isBadRequest());
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=userA, age=null"));
    }

    @Test
    void requestParamRequiredPostTest() throws Exception {
        //when
        ResultActions performBadRequest = mvc.perform(post("/request-param-required")
                .param("age", "15"));
        ResultActions perform = mvc.perform(post("/request-param-required")
                .param("username", "userA"));

        //then
        performBadRequest.andDo(print())
                .andExpect(status().isBadRequest());
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=userA, age=null"));
    }

    @Test
    void requestParamDefaultGetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-default"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=guest, age=-1"));
    }

    @Test
    void requestParamDefaultPostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-default"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=guest, age=-1"));
    }

    @Test
    void requestParamMapGetTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/request-param-map")
                .param("username", "userA")
                .param("age", "15"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void requestParamMapPostTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(post("/request-param-map")
                .param("username", "userA")
                .param("age", "15"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("username=userA, age=15"));
    }

    @Test
    void modelAttributeV1Test() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/model-attribute-v1")
                .param("username", "userA")
                .param("age", "15")
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("HelloData(username=userA, age=15)"));
    }

    @Test
    void modelAttributeV1BindingExceptionTest() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/model-attribute-v1")
                .param("username", "userA")
                .param("age", "abc")
        );

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void modelAttributeV2Test() throws Exception {
        //when
        ResultActions perform = mvc.perform(get("/model-attribute-v2")
                .param("username", "userA")
                .param("age", "15")
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("HelloData(username=userA, age=15)"));
    }

}
