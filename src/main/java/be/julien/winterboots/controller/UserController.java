package be.julien.winterboots.controller;

import be.julien.winterboots.Logger;
import be.julien.winterboots.entities.User;
import be.julien.winterboots.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.function.UnaryOperator;

@Controller
public class UserController implements Logger {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/createUser")
    public String showCreateUserForm(User user) {
        return "add-user";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        return changeOnUser(user, result, model, "add-user", "index", userRepository::save);
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        System.out.println("UserController.updateUser");
        System.out.println("id = " + id + ", user = " + user + ", result = " + result + ", model = " + model);
        return changeOnUser(user, result, model, "update-user", "index", userRepository::save);
    }

    private String changeOnUser(@Valid User user, BindingResult result, Model model, String errorBinding, String successBinding, UnaryOperator<User> userOperation) {
        if (result.hasErrors()) {
            logError(result.getAllErrors());
            return errorBinding;
        }
        logInfo("User processed: " + user.getName() + " and binding: " + successBinding);
        userOperation.apply(user);
        model.addAttribute("users", userRepository.findAll());
        return successBinding;
    }

    @GetMapping("/editUser/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        System.out.println("UserController.showUpdateForm");
        System.out.println("id = " + id + ", model = " + model);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

}