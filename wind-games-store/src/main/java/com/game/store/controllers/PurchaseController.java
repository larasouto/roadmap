package com.game.store.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.dto.PurchaseDto;
import com.game.store.models.Game;
import com.game.store.models.Purchase;
import com.game.store.models.User;
import com.game.store.services.GameService;
import com.game.store.services.PurchaseService;
import com.game.store.services.UserService;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final GameService gameService;
    private final UserService userService;

    public PurchaseController(PurchaseService purchaseService, GameService gameService, UserService userService) {
        this.purchaseService = purchaseService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @ModelAttribute("purchasedGames")
    public List<Game> initPurchasedGames() {
        return new ArrayList<>();
    }

    @GetMapping("")
    public ModelAndView getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        ModelAndView mv = new ModelAndView("purchases/index");
        mv.addObject("purchases", purchases);
        return mv;
    }

    @GetMapping("/seller-home")
    public ModelAndView sellerHome() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        ModelAndView mv = new ModelAndView("seller-home");
        mv.addObject("purchases", purchases);
        return mv;
    }

    /*
     * @GetMapping("/user-purchases")
     * public ModelAndView userPurchases(@RequestParam(name = "search", required =
     * false) String searchTerm) {
     * List<Purchase> purchases;
     * 
     * purchases = purchaseService.getAllPurchases();
     * 
     * ModelAndView mv = new ModelAndView("user-purchases");
     * mv.addObject("purchases", purchases);
     * return mv;
     * }
     */
    @GetMapping("/new")
    public ModelAndView createPurchaseForm(@RequestParam("gameId") Long gameId, PurchaseDto request) {
        Optional<Game> optionalGame = gameService.getGameById(gameId);

        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            ModelAndView mv = new ModelAndView("purchases/new");
            mv.addObject("game", game);
            return mv;
        } else {
            return returnPurchaseError("Game #" + gameId + " not found!");
        }
    }

    @GetMapping("/purchase-confirmation")
    public ModelAndView purchaseConfirmation(PurchaseDto request) {
        ModelAndView mv = new ModelAndView("purchase-confirmation");
        mv.addObject("games", gameService.getAllGames());
        mv.addObject("users", userService.getAllUsers());
        return mv;
    }

    @PostMapping("")
    public ModelAndView createPurchase(@Valid PurchaseDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("purchases/new");
            mv.addObject("games", gameService.getAllGames());
            mv.addObject("users", userService.getAllUsers());
            return mv;
        } else {
            Optional<Game> optionalGame = gameService.getGameById(request.getGameId());
            Optional<User> optionalUser = userService.getUserById(request.getUserId());

            if (optionalGame.isPresent() && optionalUser.isPresent()) {
                Game game = optionalGame.get();
                User user = optionalUser.get();

                Purchase purchase = request.toPurchase(game, user);
                purchase.setPurchasePrice(game.getPrice()); // Seta o preço da compra como o preço do jogo
                purchase.setPurchaseDate(java.time.LocalDate.now()); // Seta a data da compra como a data atual
                purchase.setPurchaseStatus(false); // Seta a compra como não confirmada

                // Adiciona o jogo comprado na lista de jogos comprados pelo usuário
                user.getPurchasedGames().add(game);

                purchaseService.savePurchase(purchase);

                return new ModelAndView("redirect:purchases/purchase-confirmation");
            } else {
                return returnPurchaseError("Jogo ou usuário não encontrado!");
            }
        }
    }

    @GetMapping("/{userId}/purchases")
    public ModelAndView getUserPurchases(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
     //   if (user == null) {
            // Lida com o caso de usuário não logado
     //       return new ModelAndView("redirect:/login");
     //   }

        List<Purchase> purchases = purchaseService.getPurchasesByUserId(userId);

        ModelAndView mv = new ModelAndView("user-purchases");
        mv.addObject("user", user);
        mv.addObject("purchases", purchases);
        return mv;
    }

    @GetMapping("/user-purchases")
    public ModelAndView userPurchases(@RequestParam(name = "search", required = false) String searchTerm,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
     //   if (userId == null) {
            // Lida com o caso de usuário não logado
     //       return new ModelAndView("redirect:/login");
    //    }

        List<Purchase> userPurchases = purchaseService.getPurchasesByUserId(userId);

        ModelAndView mv = new ModelAndView("user-purchases");
        mv.addObject("purchases", userPurchases);
        return mv;
    }

    @PostMapping("/{id}/confirm")
    public ModelAndView confirmPurchase(@PathVariable("id") Long id) {
        Optional<Purchase> optionalPurchase = purchaseService.getPurchaseById(id);
        if (optionalPurchase.isPresent()) {
            Purchase purchase = optionalPurchase.get();
            purchase.confirmPurchase();
            purchaseService.savePurchase(purchase);

            return new ModelAndView("redirect:/purchases/seller-home");
        } else {
            return returnPurchaseError("Purchase #" + id + " not found!");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView showPurchase(@PathVariable Long id) {
        Optional<Purchase> optional = purchaseService.getPurchaseById(id);
        if (optional.isPresent()) {
            Purchase purchase = optional.get();
            ModelAndView mv = new ModelAndView("purchases/show");
            mv.addObject("purchase", purchase);
            return mv;
        } else {
            return returnPurchaseError("Purchase #" + id + " not found!");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editPurchaseForm(@PathVariable Long id, PurchaseDto request) {
        Optional<Purchase> optional = purchaseService.getPurchaseById(id);
        if (optional.isPresent()) {
            Purchase purchase = optional.get();
            request.fromPurchase(purchase);
            ModelAndView mv = new ModelAndView("purchases/edit");
            mv.addObject("purchaseId", purchase.getId());
            mv.addObject("games", gameService.getAllGames());
            mv.addObject("users", userService.getAllUsers());
            return mv;
        } else {
            return returnPurchaseError("Purchase #" + id + " not found!");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView updatePurchase(@PathVariable Long id, @Valid PurchaseDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("purchases/edit");
            mv.addObject("purchaseId", id);
            mv.addObject("games", gameService.getAllGames());
            mv.addObject("users", userService.getAllUsers());
            return mv;
        } else {
            Optional<Purchase> optional = purchaseService.getPurchaseById(id);
            if (optional.isPresent()) {
                Purchase purchase = request.toPurchase(optional.get());
                purchaseService.savePurchase(purchase);
                return new ModelAndView("redirect:/purchases/" + purchase.getId());
            } else {
                return returnPurchaseError("Purchase #" + id + " not found!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deletePurchase(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/purchases");
        try {
            purchaseService.deletePurchaseById(id);
            mv.addObject("message", "Purchase #" + id + " deleted successfully!");
            mv.addObject("error", false);
        } catch (EmptyResultDataAccessException e) {
            mv = returnPurchaseError("Purchase #" + id + " not found!");
        }
        return mv;
    }

    private ModelAndView returnPurchaseError(String message) {
        ModelAndView mv = new ModelAndView("redirect:/purchases");
        mv.addObject("message", message);
        mv.addObject("error", true);
        return mv;
    }
}
