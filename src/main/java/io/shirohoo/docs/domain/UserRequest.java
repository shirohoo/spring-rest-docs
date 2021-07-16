package io.shirohoo.docs.domain;

import lombok.*;

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
