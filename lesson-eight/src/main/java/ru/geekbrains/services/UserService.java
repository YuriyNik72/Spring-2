package ru.geekbrains.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.entites.SystemUser;
import ru.geekbrains.entites.User;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
