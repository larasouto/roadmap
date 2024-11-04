package com.game.store.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.dto.UserDto;
import com.game.store.models.User;
import com.game.store.services.DeviceService;
import com.game.store.services.GenreService;
import com.game.store.services.UserService;

@Controller
public class HomeController {

    private final UserService userService;
    private final GenreService genreService;
    private final DeviceService deviceService;

    public HomeController(UserService userService, GenreService genreService, DeviceService deviceService) {
        this.userService = userService;
        this.genreService = genreService;
        this.deviceService = deviceService;
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    public static void redirectToUnauthorized() {
        throw new UnauthorizedAccessException("Unauthorized access");
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/signup")
    public ModelAndView register(UserDto request) {
        ModelAndView mv = new ModelAndView("signup");
        mv.addObject("genres", genreService.getAllGenres());
        mv.addObject("devices", deviceService.getAllDevices());
        return mv;
    }

    @GetMapping("/manager-home")
    public ModelAndView managerHome(UserDto request, HttpSession session) {
        // Long userId = (Long) session.getAttribute("id");
        // if (userId == null || !userService.isUserAuthorized(userId)) {

        // return new ModelAndView("redirect:/unauthorized");
        // }
        ModelAndView mv = new ModelAndView("manager-home");
        return mv;
    }

    /*
     * public boolean isUserAuthorized(Long userId) {
     * 
     * User user = userRepository.findById(userId).orElse(null);
     * 
     * return user != null && (user.getRole() == User.Role.ADMINISTRATOR ||
     * user.getRole() == User.Role.MANAGER);
     * }
     */
    
    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmail(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());

            if (null == user.getRole()) {
                return new ModelAndView("redirect:/games/user-home");
            } else switch (user.getRole()) {
                case ADMINISTRATOR:
                    return new ModelAndView("redirect:/main");
                case SELLER:
                    return new ModelAndView("redirect:/purchases/seller-home");
                case MANAGER:
                    return new ModelAndView("redirect:/manager-home");
                default:
                    return new ModelAndView("redirect:/games/user-home");
            }
        } else {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("error", "Email ou senha inválido(s).");
            return mv;
        }
    }

    @PostMapping("/signup")
    public ModelAndView createUser(@Valid UserDto request, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                ModelAndView mv = new ModelAndView("signup");
                return mv;
            } else {
                User user = request.toUser();
                userService.saveUser(user);
                return new ModelAndView("redirect:/games/user-home");
            }
        } catch (DataIntegrityViolationException e) {
            ModelAndView mv = new ModelAndView("signup");
            mv.addObject("error", "Email address is already in use.");
            return mv;
        }
    }

}
