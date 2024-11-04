package com.game.store.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.game.store.models.Genre;
import com.game.store.repositories.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;

  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public List<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  @Override
  public Optional<Genre> getGenreById(Long id) {
    return genreRepository.findById(id);
  }

  @Override
  public Optional<Genre> getGenreByName(String name) {
    return genreRepository.findByName(name);
  }

  @Override
  public void saveGenre(Genre genre) {
    genreRepository.save(genre);
  }

  @Override
  public void deleteGenreById(Long id) {
    genreRepository.deleteById(id);
  }
}
