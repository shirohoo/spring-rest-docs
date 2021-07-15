package io.shirohoo.docs.model;

import java.time.LocalDateTime;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private User(Long id, LocalDateTime createAt, LocalDateTime updateAt, String name, String email,
            String phoneNumber) {
        super(id, createAt, updateAt);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public static User createUser(Long id, LocalDateTime createAt, LocalDateTime updateAt,
            String name, String email, String phoneNumber) {
        return new User(id, createAt, updateAt, name, email, phoneNumber);
    }
}
