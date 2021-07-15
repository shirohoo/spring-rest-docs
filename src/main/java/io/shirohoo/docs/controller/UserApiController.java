package io.shirohoo.docs.controller;

import io.shirohoo.docs.domain.UserRequest;
import io.shirohoo.docs.model.User;
import io.shirohoo.docs.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService service;

    @GetMapping("{id}")
    public ResponseEntity<User> read(@PathVariable("id") Optional<User> user) {
        try {
            return ResponseEntity.ok(user.orElseThrow(() -> new NullPointerException()));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("")
    public ResponseEntity<User> update(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("done");
    }
}
