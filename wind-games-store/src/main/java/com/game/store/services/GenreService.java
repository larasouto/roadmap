package com.game.store.services;

import java.util.List;
import java.util.Optional;

import com.game.store.models.Genre;

public interface GenreService {
  List<Genre> getAllGenres();

  Optional<Genre> getGenreById(Long id);

  void saveGenre(Genre game);

  void deleteGenreById(Long id);

  Optional<Genre> getGenreByName(String genreName);
}
