package be.julien.winterboots.controller;

import be.julien.winterboots.Logger;
import be.julien.winterboots.entities.Product;
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
public class OrderController implements Logger {

//    private final UserRepository userRepository;
//
//    @Autowired
//    public OrderController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/createOrder")
//    public String showCreateUserForm() {
//        return "add-order";
//    }
//
//    @PostMapping("/addUser")
//    public String addUser(@Valid Product product, @Valid User user, BindingResult result, Model model) {
//        System.out.println("RECEIVED " + product.);
//        return changeOnUser(user, result, model, "add-user", "index", userRepository::save);
//    }
//
//    private String changeOnUser(@Valid User user, BindingResult result, Model model, String errorBinding, String successBinding, UnaryOperator<User> userOperation) {
//        if (result.hasErrors()) {
//            logError(result.getAllErrors());
//            return errorBinding;
//        }
//        logInfo("User processed: " + user.getName() + " and binding: " + successBinding);
//        userOperation.apply(user);
//        model.addAttribute("users", userRepository.findAll());
//        return successBinding;
//    }

}