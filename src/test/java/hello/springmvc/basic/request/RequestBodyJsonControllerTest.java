package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(RequestBodyJsonController.class)
public class RequestBodyJsonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void requestBodyJsonV1Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        String jsonContent = new ObjectMapper().writeValueAsString(userA);

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(userA.toString());
                });
    }

    @Test
    void requestBodyJsonV2Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        String jsonContent = new ObjectMapper().writeValueAsString(userA);

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(userA.toString());
                });
    }

    @Test
    void requestBodyJsonV3Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        String jsonContent = new ObjectMapper().writeValueAsString(userA);

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(userA.toString());
                });
    }

    @Test
    void requestBodyJsonV4Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        String jsonContent = new ObjectMapper().writeValueAsString(userA);

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(userA.toString());
                });
    }

    @Test
    void requestBodyJsonV41Test() throws Exception {
        //given
        HelloData userA = HelloData.builder().username("userA").age(15).build();
        String jsonContent = new ObjectMapper().writeValueAsString(userA);

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v41")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(userA.toString());
                });
    }

    @Test
    void requestBodyJsonV5Test() throws Exception {
        //given
        String jsonContent = new ObjectMapper().writeValueAsString(HelloData.builder().username("userA").age(15).build());

        //when
        ResultActions perform = mvc.perform(post("/request-body-json-v5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        perform.andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(jsonContent);
                });
    }
}
