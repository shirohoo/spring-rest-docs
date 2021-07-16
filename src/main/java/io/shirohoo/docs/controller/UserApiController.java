package io.shirohoo.docs.controller;

import io.shirohoo.docs.domain.UserRequest;
import io.shirohoo.docs.domain.UserResponse;
import io.shirohoo.docs.model.User;
import io.shirohoo.docs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService service;
    private final ModelMapper mapper;

    @PostMapping("")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        return ResponseEntity.ok(mapper.map(service.create(request), UserResponse.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> read(@PathVariable("id") Optional<User> user) {
        try {
            return ResponseEntity.ok(mapper.map(user.orElseThrow(() -> new NullPointerException()), UserResponse.class));
        }
        catch(NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest request) {
        return ResponseEntity.ok(mapper.map(service.update(request), UserResponse.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        if(!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(null);
    }
}
