package io.shirohoo.docs.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    private UserRequest(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public static UserRequest createUserRequest(Long id, String name, String email, String phoneNumber) {
        return new UserRequest(id, name, email, phoneNumber);
    }
}
