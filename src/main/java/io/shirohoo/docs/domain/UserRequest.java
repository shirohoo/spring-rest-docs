package io.shirohoo.docs.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRequest {
    private Long id;
    private String account;
    private String email;
    private String phoneNumber;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
