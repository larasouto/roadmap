package com.game.store.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.game.store.models.User;
import com.game.store.models.UserGamesPurchased;
import com.game.store.services.GameService;
import com.game.store.services.UserService;

@Controller
@RequestMapping("/users")
public class PurchasedGamesController {

  private final UserService userService;
  private final GameService gameService;

  public PurchasedGamesController(UserService userService, GameService gameService) {
    this.userService = userService;
    this.gameService = gameService;
  }

  @GetMapping("/purchased-games")
  public String showCurrentUserPurchasedGames(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    System.out.println("userId: " + userId);
    // if (userId == null) {
    // return "login";
    // }
    Optional<User> optionalUser = userService.getUserById(userId);
    List<UserGamesPurchased> purchasedGames = gameService.findUserGamesPurchasedByUserId(userId);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      // Set<Game> purchasedGames = user.getPurchasedGames();

      model.addAttribute("user", user);
      // model.addAttribute("purchasedGames", purchasedGames);
      model.addAttribute("purchasedGames", purchasedGames);

      return "purchased-games";
    } else {
      return "user-not-found";
    }
  }
}
