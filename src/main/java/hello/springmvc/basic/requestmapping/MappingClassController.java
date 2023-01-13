package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;


/**
 * 회원 관리 API<br>
 * <p>
 * 회원 목록 조회: GET /users<br>
 * 회원 등록: POST /users<br>
 * 회원 조회: GET /users/{userId}<br>
 * 회원 수정: PATCH /users/{userId}<br>
 * 회원 삭제: DELETE /users/{userId}<br>
 */
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "add user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "find userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }

}
