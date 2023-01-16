package hello.springmvc.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
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

@WebMvcTest(ResponseBodyController.class)
public class ResponseBodyControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void responseBodyV1Test() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/response-body-string-v1"));
        //then
        perform.andDo(print()).andExpect(content().string("ok"));
    }

    @Test
    void responseBodyV2Test() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/response-body-string-v2"));
        //then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("ok"));
    }

    @Test
    void responseBodyV3Test() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/response-body-string-v3"));
        //then
        perform.andDo(print())
                .andExpect(content().string("ok"));
    }

    @Test
    void responseBodyJsonV1Test() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/response-body-json-v1"));
        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.username").value("userA"))
                .andExpect(jsonPath("$.age").value("15"));
    }

    @Test
    void responseBodyJsonV2Test() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/response-body-json-v2"));
        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.username").value("userA"))
                .andExpect(jsonPath("$.age").value("15"));
    }

    @Test
    void responseBodyJsonV21Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        HelloData emptyUser = HelloData.builder().build();

        //when
        ResultActions perform = mvc.perform(post("/response-body-json-v21")
                .content(new ObjectMapper().writeValueAsString(userA))
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions performBadRequest = mvc.perform(post("/response-body-json-v21")
                .content(new ObjectMapper().writeValueAsString(emptyUser))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.username").value("userA"))
                .andExpect(jsonPath("$.age").value("15"));
        performBadRequest.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void responseBodyJsonV22Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        HelloData emptyUser = HelloData.builder().build();

        //when
        ResultActions perform = mvc.perform(post("/response-body-json-v22")
                .content(new ObjectMapper().writeValueAsString(userA))
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions performBadRequest = mvc.perform(post("/response-body-json-v22")
                .content(new ObjectMapper().writeValueAsString(emptyUser))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.username").value("userA"))
                .andExpect(jsonPath("$.age").value("15"));
        performBadRequest.andDo(print()).andExpect(status().isBadRequest());
    }
}
