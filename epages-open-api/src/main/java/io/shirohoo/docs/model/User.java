package io.shirohoo.docs.model;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Access(AccessType.FIELD)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public static User createUser(String name, String email, String phoneNumber) {
        return new User(name, email, phoneNumber);
    }
}
