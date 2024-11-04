package com.game.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.game.store.models.User;
import com.game.store.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public boolean isEmailUnique(String email) {
    User user = userRepository.findByEmail(email);
    return user == null;
  }
}
