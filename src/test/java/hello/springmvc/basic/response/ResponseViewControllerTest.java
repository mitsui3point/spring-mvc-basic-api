package hello.springmvc.basic.response;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.*;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResponseViewController.class)
public class ResponseViewControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void responseViewV1Test() throws Exception {
        //given
        //when
        ResultActions perform = mvc.perform(get("/response-view-v1"));
        //then
        perform.andDo(print())
                .andExpect(view().name("response/hello"))
                .andExpect(model().attribute("data", "hello"));
    }

    @Test
    void responseViewV2Test() throws Exception {
        //given
        //when
        ResultActions perform = mvc.perform(get("/response-view-v2"));
        //then
        perform.andDo(print())
                .andExpect(view().name("response/hello"))
                .andExpect(model().attribute("data", "hello"));
    }

    @Test
    void responseViewV21Test() throws Exception {
        //given
        //when
        ResultActions perform = mvc.perform(get("/response-view-v21"));
        //then
        perform.andDo(print())
                .andExpect(content().string("response/hello"));

    }

    @Test
    void responseViewV3Test() throws Exception {
        //given
        //when
        ResultActions perform = mvc.perform(get("/response/hello"));
        //then
        perform.andDo(print())
                .andExpect(view().name("response/hello"))
                .andExpect(model().attribute("data", "hello"));
    }

}
