package io.shirohoo.docs.domain;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    private UserDto(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public static UserDto createUserRequest(Long id, String name, String email, String phoneNumber) {
        return new UserDto(id, name, email, phoneNumber);
    }
}
