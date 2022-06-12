package ru.geekbrains.services;

import ru.geekbrains.entites.SystemUser;
import ru.geekbrains.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
