package io.shirohoo.docs.domain;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String createAt;
    private String updateAt;

    private UserResponse(Long id, String name, String email, String phoneNumber, String createAt, String updateAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Builder
    public static UserResponse createUserResponse(Long id, String name, String email, String phoneNumber, String createAt, String updateAt) {
        return new UserResponse(id, name, email, phoneNumber, createAt, updateAt);
    }
}
