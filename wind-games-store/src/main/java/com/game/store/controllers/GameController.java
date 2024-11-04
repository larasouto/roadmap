package com.game.store.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.dto.GameDto;
import com.game.store.models.Device;
import com.game.store.models.Game;
import com.game.store.models.Genre;
import com.game.store.services.DeviceService;
import com.game.store.services.GameService;
import com.game.store.services.GenreService;

@Controller
@RequestMapping(value = "/games")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        logger.info("Entering method: {}.{}", className, methodName);
    }

    private final GameService gameService;
    private final GenreService genreService;
    private final DeviceService deviceService;

    public GameController(GameService gameService, GenreService genreService, DeviceService deviceService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public ModelAndView index() {
        List<Game> games = gameService.getAllGames();
        ModelAndView mv = new ModelAndView("games/index");
        mv.addObject("games", games);
        return mv;
    }

    @GetMapping("/game/{gameId}")
    public String getGameDetails(@PathVariable Long gameId, Model model) {
        Optional<Game> game = gameService.getGameById(gameId);
        model.addAttribute("game", game);
        return "game-details";
    }

    @GetMapping("/user-home")
    public ModelAndView userHome(@RequestParam(name = "search", required = false) String searchTerm) {

        List<Game> games = gameService.getAllGames()
        .stream()
        .filter(game -> searchTerm != null && !searchTerm.isEmpty() 
                ? game.getName().toLowerCase().contains(searchTerm.toLowerCase()) 
                : true)
        .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("user-home");
        mv.addObject("games", games);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView createNewGameForm(GameDto request) {
        ModelAndView mv = new ModelAndView("games/new");
        mv.addObject("genres", genreService.getAllGenres());
        mv.addObject("devices", deviceService.getAllDevices());
        return mv;
    }

    @GetMapping("/user-games")
    public ModelAndView showUserGames(GameDto request) {
        ModelAndView mv = new ModelAndView("user-games");
        mv.addObject("genres", genreService.getAllGenres());
        mv.addObject("devices", deviceService.getAllDevices());
        return mv;
    }

    @PostMapping("")
    public ModelAndView createGame(@Valid GameDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("games/new")
                    .addObject("genres", genreService.getAllGenres())
                    .addObject("devices", deviceService.getAllDevices());
        }
        
        Optional<Genre> genre = genreService.getGenreById(request.getGenre().getId());
        Optional<Device> device = deviceService.getDeviceById(request.getPlatform().getId());
        
        return genre.flatMap(g -> 
                device.map(d -> {
                    Game game = request.toGame();
                    game.setGenre(g);
                    game.setPlatform(d);
                    gameService.saveGame(game);
                    return new ModelAndView("redirect:/games/" + game.getId());
                })
        ).orElseGet(() -> returnGameError("Genre or Device not found!"));
        
    }

    @GetMapping("/{id}")
    public ModelAndView showGame(@PathVariable Long id) {
        Optional<Game> optional = gameService.getGameById(id);
        if (optional.isPresent()) {
            Game game = optional.get();
            ModelAndView mv = new ModelAndView("games/show");
            mv.addObject("game", game);
            return mv;
        } else {
            return returnGameError("SHOW ERROR: Game #" + id + " not found in the database!");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editGameForm(@PathVariable Long id, GameDto game) {
        Optional<Game> optional = gameService.getGameById(id);
        return optional.map(game1 -> {
            game.fromGame(game1);
            ModelAndView mv = new ModelAndView("games/edit");
            mv.addObject("gameId", game1.getId());
            mv.addObject("genres", genreService.getAllGenres());
            mv.addObject("devices", deviceService.getAllDevices());
            return mv;
        }).orElseGet(() -> returnGameError("EDIT ERROR: Game #" + id + " not found in the database!"));
    }

    @PostMapping("/{id}")
    public ModelAndView updateGame(@PathVariable Long id, @Valid GameDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("games/edit")
                    .addObject("gameId", id)
                    .addObject("genres", genreService.getAllGenres())
                    .addObject("devices", deviceService.getAllDevices());
        }
        
        return gameService.getGameById(id)
                .map(existingGame -> {
                    Game game = request.toGame(existingGame);
                    gameService.saveGame(game);
                    return new ModelAndView("redirect:/games/" + game.getId());
                })
                .orElseGet(() -> returnGameError("UPDATE ERROR: Game #" + id + " not found in the database!"));        
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteGame(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/games");
        try {
            gameService.deleteGameById(id);
            mv.addObject("message", "Game #" + id + " deletado com sucesso!");
            mv.addObject("error", false);
        } catch (EmptyResultDataAccessException e) {
            mv = returnGameError("DELETE ERROR: Game #" + id + " not found in the database!");
        }
        return mv;
    }

    private ModelAndView returnGameError(String message) {
        ModelAndView mv = new ModelAndView("redirect:/games");
        mv.addObject("message", message);
        mv.addObject("error", true);
        return mv;
    }
}
