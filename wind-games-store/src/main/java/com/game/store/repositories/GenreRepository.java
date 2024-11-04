package com.game.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.store.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
   Optional<Genre> findByName(String name);
}
