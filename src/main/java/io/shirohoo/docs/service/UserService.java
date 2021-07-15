package io.shirohoo.docs.service;

import io.shirohoo.docs.domain.UserRequest;
import io.shirohoo.docs.model.User;
import io.shirohoo.docs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final ModelMapper mapper;
    private final UserRepository repository;

    @Transactional
    public User create(UserRequest request) {
        return repository.save(mapper.map(request, User.class));
    }

    @Transactional
    public User update(UserRequest request) {
        return repository.save(mapper.map(request, User.class));
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("user not found!");
            return false;
        }
        return true;
    }
}
