package io.github.shirohoo.springrestdocs.api;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping
    public ResponseEntity<User> get(@RequestParam String phoneNumber) {
        Map<String, User> users = getRepository();

        if (users.containsKey(phoneNumber)) {
            return ResponseEntity.ok(users.get(phoneNumber));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> post(@RequestBody User user) {
        Map<String, User> users = getRepository();

        if (users.containsKey(user)) {
            return ResponseEntity.badRequest().build();
        }
        users.put(user.getPhoneNumber(), user);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(user);
    }

    private Map<String, User> getRepository() {
        Map<String, User> users = new HashMap<>();
        users.put("010-1234-5678", new User("user1", 11, "010-1234-5678", LocalDate.of(2000, 1, 1)));
        users.put("010-1111-1111", new User("user2", 22, "010-1111-1111", LocalDate.of(2000, 1, 1)));
        users.put("010-1234-1111", new User("user3", 33, "010-1234-1111", LocalDate.of(2000, 1, 1)));
        return users;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private String name;

        private int age;

        private String phoneNumber;

        private LocalDate birthDay;

    }

}
