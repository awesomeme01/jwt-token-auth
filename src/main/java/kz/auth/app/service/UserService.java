package kz.auth.app.service;

import kz.auth.app.domain.entity.User;

public interface UserService {
    User create(User user);
    User update(User user);
    void delete(User user);
    void delete(Long id);
    User getUserByUsername(String username);
}
