package com.game.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.store.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  User findByName(String name);
  Optional<User> findById(Long id);

}