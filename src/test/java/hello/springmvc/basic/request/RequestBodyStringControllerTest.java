package hello.springmvc.basic.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestBodyStringController.class)
public class RequestBodyStringControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void requestBodyStringV1Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV2Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV3Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV31Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV31BadRequestTest() throws Exception {
        //given
        String jsonContent = "";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void requestBodyStringV4Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV4BadRequestTest() throws Exception {
        //given
        String jsonContent = "";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void requestBodyStringV41Test() throws Exception {
        //given
        String jsonContent = "{\"username\":\"userA\", \"age\":\"15\"}";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v41")
                .contentType(MediaType.APPLICATION_JSON)
                .header("myHeader", "h1", "h2")
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonContent));
    }

    @Test
    void requestBodyStringV41BadRequestTest() throws Exception {
        //given
        String jsonContent = "";

        //when
        ResultActions perform = mvc.perform(post("/request-body-string-v41")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
        );

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest());
    }
}
