package com.game.store.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.dto.UserDto;
import com.game.store.models.Device;
import com.game.store.models.User;
import com.game.store.services.DeviceService;
import com.game.store.services.GenreService;
import com.game.store.services.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;
  private final GenreService genreService;
  private final DeviceService deviceService;

  public UserController(UserService userService, GenreService genreService, DeviceService deviceService) {
    this.userService = userService;
    this.genreService = genreService;
    this.deviceService = deviceService;
  }

  @GetMapping("")
  public ModelAndView index() {
    List<User> users = userService.getAllUsers();
    ModelAndView mv = new ModelAndView("users/index");
    mv.addObject("users", users);
    return mv;
  }

  @GetMapping("/new")
  public ModelAndView createNewUserForm(UserDto request) {
    ModelAndView mv = new ModelAndView("users/new");
    mv.addObject("genres", genreService.getAllGenres());
    mv.addObject("devices", deviceService.getAllDevices());
    return mv;
  }

  @PostMapping("")
  public ModelAndView createUser(@Valid UserDto request, @RequestParam String role, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView("users/new");
      mv.addObject("genres", genreService.getAllGenres());
      mv.addObject("devices", deviceService.getAllDevices());
      return mv;
    } else {
      Set<Device> selectedPlatforms = request.getPlatforms();
      Set<Device> platforms = new HashSet<>();

      for (Device selectedPlatform : selectedPlatforms) {
        Optional<Device> optionalDevice = deviceService.getDeviceById(selectedPlatform.getId());
        if (optionalDevice.isPresent()) {
          platforms.add(optionalDevice.get());
        } else {
          return returnUserError("Plataforma não encontrada!");
        }
      }
      System.out.println(request);
      System.out.println(role);
      User user = request.toUser();
      user.setPlatforms(platforms);
      
      user.setRole(User.Role.valueOf(role));
      

      userService.saveUser(user);
      return new ModelAndView("redirect:/users/" + user.getId());
    }
  }

  @GetMapping("/{id}")
  public ModelAndView showUser(@PathVariable Long id) {
    Optional<User> optional = userService.getUserById(id);
    if (optional.isPresent()) {
      User user = optional.get();
      ModelAndView mv = new ModelAndView("users/show");
      mv.addObject("user", user);
      return mv;
    } else {
      return returnUserError("SHOW ERROR: User #" + id + " not found in the database!");
    }
  }

  @GetMapping("/{id}/edit")
  public ModelAndView editUserForm(@PathVariable Long id, UserDto request) {
    Optional<User> optional = userService.getUserById(id);
    if (optional.isPresent()) {
      User user = optional.get();
      request.fromUser(user);
      ModelAndView mv = new ModelAndView("users/edit");
      mv.addObject("user", user);
      mv.addObject("genres", genreService.getAllGenres());
      mv.addObject("devices", deviceService.getAllDevices());
      return mv;
    } else {
      return returnUserError("EDIT ERROR: User #" + id + " not found in the database!");
    }
  }

  @PostMapping("/{id}")
  public ModelAndView updateUser(@PathVariable Long id, @Valid UserDto request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView("users/edit");
      mv.addObject("id", id);
      mv.addObject("genres", genreService.getAllGenres());
      mv.addObject("devices", deviceService.getAllDevices());
      return mv;
    } else {
      Optional<User> optional = userService.getUserById(id);
      if (optional.isPresent()) {
        User user = request.toUser(optional.get());
        userService.saveUser(user);
        return new ModelAndView("redirect:/users/" + user.getId());
      } else {
        return returnUserError("UPDATE ERROR: User #" + id + " not found in the database!");
      }
    }
  }

  @GetMapping("/{id}/delete")
  public ModelAndView deleteUser(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("redirect:/users");
    try {
      userService.deleteUserById(id);
      mv.addObject("mensagem", "User #" + id + " deleted successfully!");
      mv.addObject("erro", false);
    } catch (EmptyResultDataAccessException e) {
      mv = returnUserError("DELETE ERROR: User #" + id + " not found in the database!");
    }
    return mv;
  }

  private ModelAndView returnUserError(String message) {
    ModelAndView mv = new ModelAndView("redirect:/users");
    mv.addObject("message", message);
    mv.addObject("error", true);
    return mv;
  }
}
