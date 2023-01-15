package hello.springmvc.basic;

import lombok.*;

/**
 * 롬복 @Data {@link Data}
 * {@link Getter}, {@link Setter}, {@link ToString}, {@link EqualsAndHashCode}, {@link RequiredArgsConstructor} 를
 * 자동으로 적용해준다.
 *
 * class level 에 {@link Builder}를 {@link NoArgsConstructor}없이 생성시 에러원인
 * : com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `hello.springmvc.basic.HelloData` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
 * : no Creators, like default constructor, exist => 'jackson' library 가 empty constructor 가 없고, 파라미터가 있는 constructor 가 있을 때 어떻게 Json 모델을 생성해야되는지 몰라서 발생하는 문제
 */
@Data
@NoArgsConstructor
public class HelloData {
    private String username;
    private int age;

    @Builder
    private HelloData(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
