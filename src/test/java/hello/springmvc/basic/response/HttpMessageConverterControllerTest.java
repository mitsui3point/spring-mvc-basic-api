package hello.springmvc.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HttpMessageConverterController.class)
public class HttpMessageConverterControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void convertBytesTest() throws Exception {
        //given
        byte[] expectedContent = new ObjectMapper().writeValueAsBytes(HelloData.builder().build());
        //when
        ResultActions perform = mvc.perform(post("/http-message-converter-bytes")
                .content(expectedContent)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(expectedContent));
    }

    @Test
    void convertStringTest() throws Exception {
        //given
        String expectedContent = new ObjectMapper().writeValueAsString(HelloData.builder().build());
        //when
        ResultActions perform = mvc.perform(post("/http-message-converter-string")
                .content(expectedContent)
                .contentType(MediaType.TEXT_PLAIN_VALUE));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(expectedContent));
    }

    @Test
    void convertJsonTest() throws Exception {
        //given
        String expectedContent = new ObjectMapper().writeValueAsString(HelloData.builder().build());
        //when
        ResultActions perform = mvc.perform(post("/http-message-converter-json")
                .content(expectedContent)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedContent));
    }
}
