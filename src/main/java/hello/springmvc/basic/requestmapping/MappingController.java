package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController//오류메시지 JSON return
@Slf4j
public class MappingController {
    @RequestMapping(value = {"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용<br>
     * GET, HEAD, POST, PUT, PATCH, DELETE<br>
     * <p>
     * 편리한 축약 애노테이션 (코드보기)<br>
     * {@link GetMapping}<br>
     * {@link PostMapping}<br>
     * {@link PutMapping}<br>
     * {@link DeleteMapping}<br>
     * {@link PatchMapping}<br>
     */
    @RequestMapping(value = "/mapping-get", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * {@link PathVariable} 사용<br>
     * 변수명이 같으면 생략 가능<br>
     * {@code @PathVariable("userId") String userId} -> {@code @PathVariable userId}<br>
     * /mapping/userA
     */
    @GetMapping(value = "/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return data;
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return userId + "," + orderId;
    }

    /**
     * 파라미터로 추가 매핑<br>
     * params="mode" => queryParam key == mode 전체<br>
     * params="!mode" => queryParam key != mode 전체<br>
     * params="mode=debug" => => queryParam key == mode, value == debug<br>
     * params="mode!=debug" (! = ) => queryParam key == mode, value != debug<br>
     * params = {"mode=debug","data=good"} => queryParams mode=debug&data=good<br>
     */
    @GetMapping(value = "/mapping-param", params = "!mode")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑<br>
     * headers="mode",<br>
     * headers="!mode"<br>
     * headers="mode=debug"<br>
     * headers="mode!=debug" (! = )<br>
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type<br>
     * consumes="application/json"<br>
     * consumes="!application/json"<br>
     * consumes="application/*"<br>
     * consumes="*\/*"<br>
     * MediaType.APPLICATION_JSON_VALUE<br>
     */
    @PostMapping(value = "/mapping-consume", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type<br>
     * produces = "text/html"<br>
     * produces = "!text/html"<br>
     * produces = "text/*"<br>
     * produces = "*\/*"<br>
     *
     * postman 테스트시 Accept header 설정에 따라 406 Error message Body 가 달라진다<br>
     * =========================================<br>
     * Accept: application/json 시<br>
     * =========================================<br>
     * {
     *     "timestamp": "2023-01-12T17:28:04.905+00:00",
     *     "status": 406,
     *     "error": "Not Acceptable",
     *     "path": "/mapping-produce"
     * }<br>
     * =========================================<br>
     * Accept: text/html 시<br>
     * =========================================<br>
     * <html>
     * <body>
     * 	<h1>Whitelabel Error Page</h1>
     * 	<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>
     * 	<div id='created'>Fri Jan 13 02:31:22 KST 2023</div>
     * 	<div>There was an unexpected error (type=Not Acceptable, status=406).</div>
     * </body>
     * </html>
     */
    @PostMapping(value = "/mapping-produce", produces = "!text/*")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}