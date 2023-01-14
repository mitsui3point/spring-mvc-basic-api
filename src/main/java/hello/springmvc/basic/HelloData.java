package hello.springmvc.basic;

import lombok.*;

/**
 * 롬복 @Data {@link Data}
 * {@link Getter}, {@link Setter}, {@link ToString}, {@link EqualsAndHashCode}, {@link RequiredArgsConstructor} 를
 * 자동으로 적용해준다.
 */
@Data
@Builder
public class HelloData {
    private String username;
    private int age;
}
