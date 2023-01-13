package hello.springmvc.basic.requestmapping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MappingClassController.class)
public class MappingClassControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void usersTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping/users")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("get users"));
    }

    @Test
    void addUserTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(post("/mapping/users")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("add user"));
    }

    @Test
    void findUserTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping/users/user1")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("find userId=user1"));
    }

    @Test
    void updateUserTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(patch("/mapping/users/user1")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("update userId=user1"));
    }

    @Test
    void deleteUserTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(delete("/mapping/users/user1")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("delete userId=user1"));
    }
}
