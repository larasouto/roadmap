package com.game.store.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.models.Genre;
import com.game.store.services.GenreServiceImpl;

@Controller
@RequestMapping(value = "/genres")
public class GenreController {
  private final GenreServiceImpl genreService;

  public GenreController(GenreServiceImpl genreService) {
    this.genreService = genreService;
  }

  @GetMapping("")
  public ModelAndView index() {
    List<Genre> genres = genreService.getAllGenres();
    ModelAndView mv = new ModelAndView("genres/index");
    mv.addObject("genres", genres);
    return mv;
  }

  @GetMapping("/new")
  public ModelAndView createNewGenreForm() {
    ModelAndView mv = new ModelAndView("genres/new");
    mv.addObject("genre", new Genre());
    return mv;
  }

  @PostMapping("")
  public ModelAndView createGenre(@Valid Genre genre, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView("genres/new");
      return mv;
    } else {
      genreService.saveGenre(genre);
      return new ModelAndView("redirect:/genres/");
    }
  }

  @GetMapping("/{id}")
  public ModelAndView showGenre(@PathVariable Long id) {
    Optional<Genre> optional = genreService.getGenreById(id);
    if (optional.isPresent()) {
      Genre genre = optional.get();
      ModelAndView mv = new ModelAndView("genres/show");
      mv.addObject("genre", genre);
      return mv;
    } else {
      return returnGenreError("SHOW ERROR: Genre #" + id + " not found in the database!");
    }
  }

  @GetMapping("/{id}/edit")
  public ModelAndView editGenreForm(@PathVariable Long id) {
    Optional<Genre> optional = genreService.getGenreById(id);
    if (optional.isPresent()) {
      Genre genre = optional.get();
      ModelAndView mv = new ModelAndView("genres/edit");
      mv.addObject("genre", genre);
      return mv;
    } else {
      return returnGenreError("EDIT ERROR: Genre #" + id + " not found in the database!");
    }
  }

  @PostMapping("/{id}")
  public ModelAndView updateGenre(@PathVariable Long id, @Valid Genre genre, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView("genres/edit");
      mv.addObject("genre", genre);
      return mv;
    } else {
      Optional<Genre> optional = genreService.getGenreById(id);
      if (optional.isPresent()) {
        Genre existingGenre = optional.get();
        existingGenre.setName(genre.getName()); // Atualiza o nome do gênero
        genreService.saveGenre(existingGenre);
        return new ModelAndView("redirect:/genres/");
      } else {
        return returnGenreError("UPDATE ERROR: Genre #" + id + " not found in the database!");
      }
    }
  }

  @GetMapping("/{id}/delete")
  public ModelAndView deleteGenre(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("redirect:/genres");
    try {
      genreService.deleteGenreById(id);
      mv.addObject("message", "Genre #" + id + " deleted successfully!");
      mv.addObject("error", false);
    } catch (EmptyResultDataAccessException e) {
      mv = returnGenreError("DELETE ERROR: Genre #" + id + " not found in the database!");
    }
    return mv;
  }

  private ModelAndView returnGenreError(String message) {
    ModelAndView mv = new ModelAndView("redirect:/genres");
    mv.addObject("message", message);
    mv.addObject("error", true);
    return mv;
  }
}
