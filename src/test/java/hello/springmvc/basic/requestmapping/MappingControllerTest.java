package hello.springmvc.basic.requestmapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MappingControllerTest {
    private Logger log;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        log = LoggerFactory.getLogger(this.getClass());
    }

    @Test
    void mappingTest() throws Exception {
        //given

        //when
        ResultActions performBasic = mvc.perform(get("/hello-basic"));
        ResultActions performGo = mvc.perform(get("/hello-go"));

        //then
        performBasic.andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().string("ok"));
        log.info("=================================");
        performGo.andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().string("ok"));
    }

    /**
     * 둘다 허용 - 스프링 부트 3.0 이전<br>
     * 다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑한다.<br>
     * 매핑: /hello-basic<br>
     * URL 요청: /hello-basic , /hello-basic/<br>
     * <p>
     * 스프링 부트 3.0 이후<br>
     * 스프링 부트 3.0 부터는 /hello-basic , /hello-basic/ 는 서로 다른 URL 요청을 사용해야 한다.<br>
     * 기존에는 마지막에 있는 / (slash)를 제거했지만, 스프링 부트 3.0 부터는 마지막의 / (slash)를 유지한다.<br>
     * 따라서 다음과 같이 다르게 매핑해서 사용해야 한다.<br>
     * 매핑: /hello-basic URL 요청: /hello-basic<br>
     * 매핑: /hello-basic/ URL 요청: /hello-basic/<br>
     */
    @Test
    void mappingSpringBoot3_0Test() throws Exception {
        //given

        //when
        ResultActions performBasic = mvc.perform(get("/hello-basic/"));

        //then
        performBasic.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getMethodOnlyTest() throws Exception {
        //given

        //when
        ResultActions getPerform = mvc.perform(get("/mapping-get"));
        ResultActions[] performNotAllowedMethods = {
                mvc.perform(post("/mapping-get")),
                mvc.perform(put("/mapping-get")),
                mvc.perform(patch("/mapping-get")),
                mvc.perform(delete("/mapping-get"))
//                mvc.perform(head("/hello-basic")),
//                mvc.perform(options("/hello-basic"))
        };
        //then
        getPerform.andDo(print())
                .andExpect(status().isOk());
        for (ResultActions perform : performNotAllowedMethods) {
            log.info("=================================");
            perform.andDo(print())
                    .andExpect(status().isMethodNotAllowed());
        }
    }

    @Test
    void mappingPathOneTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping/kim"));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().string("kim"));
    }

    @Test
    void mappingPathManyTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping/users/kim/orders/123"));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().string("kim,123"));
    }

    @Test
    void mappingParamTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping-param"));
        ResultActions performBadRequest = mvc.perform(get("/mapping-param?mode=dddd"));
        //then
        perform.andDo(print())
                .andExpect(status().isOk());
        log.info("=================================");
        performBadRequest.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void mappingHeaderTest() throws Exception {
        //given

        //when
        ResultActions perform = mvc.perform(get("/mapping-header")
                .header("mode", "debug"));
        ResultActions performNotFound = mvc.perform(get("/mapping-header"));
        //then
        perform.andDo(print())
                .andExpect(status().isOk());
        log.info("=================================");
        performNotFound.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void mappingConsumes() throws Exception {
        //given

        //when
        ResultActions[] performs = {
                mvc.perform(post("/mapping-consume")
                        .contentType(MediaType.APPLICATION_JSON)),
                mvc.perform(post("/mapping-consume")
                        .contentType(MediaType.TEXT_PLAIN))
        };
        ResultActions performUnsupportedMediaType = mvc.perform(post("/mapping-consume"));

        //then
        for (ResultActions perform : performs) {
            perform.andDo(print())
                    .andExpect(status().isOk());
            log.info("=================================");
        }
        performUnsupportedMediaType.andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void mappingProduce() throws Exception {
        //given

        //when
        ResultActions[] performs = {
                mvc.perform(post("/mapping-produce")
                        .accept(MediaType.APPLICATION_JSON)),
                mvc.perform(post("/mapping-produce")
                        .accept(MediaType.IMAGE_PNG))
        };
        ResultActions performNotAcceptable = mvc.perform(post("/mapping-produce")
                .accept(MediaType.TEXT_HTML));

        //then
        for (ResultActions perform : performs) {
            perform.andDo(print())
                    .andExpect(status().isOk());
            log.info("=================================");
        }
        performNotAcceptable.andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}
