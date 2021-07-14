package io.shirohoo.docs.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @NotNull
    private String account;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private User(String account, String email, String phoneNumber) {
        this.account = account;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static User createUser(String account, String email, String phoneNumber) {
        return new User(account, email, phoneNumber);
    }
}
