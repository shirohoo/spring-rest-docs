package io.shirohoo.docs.controller;

import io.shirohoo.docs.model.User;
import io.shirohoo.docs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService service;

    @GetMapping("{id}")
    public ResponseEntity<User> read(@PathVariable("id") Optional<User> user) {
        try {
            return ResponseEntity.ok(user.orElseThrow(() -> new NullPointerException()));
        }
        catch(NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
