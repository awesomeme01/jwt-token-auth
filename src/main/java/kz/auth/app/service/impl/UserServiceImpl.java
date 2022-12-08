package kz.auth.app.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.auth.app.domain.entity.User;
import kz.auth.app.domain.repository.UserRepository;
import kz.auth.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username = " + username));
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
