package com.game.store.services;

import java.util.List;
import java.util.Optional;

import com.game.store.models.User;

public interface UserService {
  List<User> getAllUsers();

  Optional<User> getUserById(Long id);

  void deleteUserById(Long id);

  User findByEmail(String email);

  void saveUser(User user);

  boolean isEmailUnique(String email);
}
